package com.khahnm04.ecommerce.service.order.impl;

import com.khahnm04.ecommerce.common.enums.OrderStatus;
import com.khahnm04.ecommerce.common.enums.PaymentStatus;
import com.khahnm04.ecommerce.dto.request.inventory.StockMovementRequest;
import com.khahnm04.ecommerce.dto.request.order.OrderRequest;
import com.khahnm04.ecommerce.dto.response.cart.CartResponse;
import com.khahnm04.ecommerce.dto.response.cart.CartItemResponse;
import com.khahnm04.ecommerce.dto.response.order.OrderResponse;
import com.khahnm04.ecommerce.entity.coupon.Coupon;
import com.khahnm04.ecommerce.entity.coupon.CouponUsage;
import com.khahnm04.ecommerce.entity.inventory.Branch;
import com.khahnm04.ecommerce.entity.order.Order;
import com.khahnm04.ecommerce.entity.order.OrderDetail;
import com.khahnm04.ecommerce.entity.order.Payment;
import com.khahnm04.ecommerce.entity.product.ProductVariant;
import com.khahnm04.ecommerce.entity.user.User;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.OrderMapper;
import com.khahnm04.ecommerce.repository.*;
import com.khahnm04.ecommerce.service.cart.CartService;
import com.khahnm04.ecommerce.service.coupon.CouponService;
import com.khahnm04.ecommerce.service.inventory.StockMovementService;
import com.khahnm04.ecommerce.service.order.OrderService;
import com.khahnm04.ecommerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final BranchRepository branchRepository;
    private final ProductVariantRepository variantRepository;
    private final CartService cartService;
    private final CouponService couponService;
    private final StockMovementService stockMovementService;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final CouponUsageRepository couponUsageRepository;
    private final CouponRepository couponRepository;

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        User user = userService.getCurrentUser();

        // 1. Lấy giỏ hàng
        CartResponse cart = cartService.getMyCart();
        if (cart.getItems().isEmpty()) {
            throw new AppException(ErrorCode.CART_EMPTY);
        }

        // 2. Validate Branch
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND));

        // 3. Khởi tạo Order (Builder pattern)
        Order order = Order.builder()
                .orderCode(generateOrderCode())
                .user(user)
                .branch(branch)
                .orderStatus(OrderStatus.PENDING)
                .shippingName(request.getShippingName())
                .shippingPhone(request.getShippingPhone())
                .shippingProvince(request.getShippingProvince())
                .shippingDistrict(request.getShippingDistrict())
                .shippingWard(request.getShippingWard())
                .shippingAddressDetail(request.getShippingAddressDetail())
                .note(request.getNote())
                .build();

        // 4. Xử lý từng item: Tạo OrderDetail + Trừ kho
        List<OrderDetail> orderDetails = new ArrayList<>();
        long tempTotalAmount = 0;

        for (CartItemResponse item : cart.getItems()) {
            ProductVariant variant = variantRepository.findById(item.getProductVariantId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

            // A. Tạo phiếu xuất kho (OUTBOUND)
            stockMovementService.outbound(StockMovementRequest.builder()
                    .productVariantId(variant.getId())
                    .branchId(branch.getId())
                    .quantity(Long.valueOf(item.getQuantity()))
                    .note("Xuất kho đơn hàng: " + order.getOrderCode())
                    .build());

            // B. Tạo OrderDetail
            OrderDetail detail = OrderDetail.builder()
                    .order(order)
                    .productVariant(variant)
                    .quantity(Long.valueOf(item.getQuantity()))
                    .unitPrice(item.getPrice().longValue()) // Giá tại thời điểm mua
                    .build();

            orderDetails.add(detail);
            tempTotalAmount += (detail.getUnitPrice() * detail.getQuantity());
        }
        order.setOrderDetails(orderDetails);

        // 5. XỬ LÝ COUPON (Logic Mới)
        long discountAmount = 0;
        Coupon coupon = null; // Biến tạm để lưu coupon entity

        if (request.getCouponCode() != null && !request.getCouponCode().isBlank()) {
            // A. Tìm Coupon trong DB
            coupon = couponRepository.findByCode(request.getCouponCode())
                    .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

            // B. Kiểm tra tính hợp lệ cơ bản (Ngày, Active, Min Order)
            checkCouponValid(coupon, tempTotalAmount);

            // C. QUAN TRỌNG: Kiểm tra xem User này đã dùng mã này chưa?
            // (Giả sử quy định: Mỗi khách chỉ được dùng 1 lần)
            long usageCount = couponUsageRepository.countByUserIdAndCouponId(user.getId(), coupon.getId());
            if (usageCount > 0) {
                throw new AppException(ErrorCode.COUPON_ALREADY_USED_BY_USER);
            }

            // D. Tính tiền giảm
            BigDecimal discount = couponService.calculateCouponValue(
                    request.getCouponCode(),
                    BigDecimal.valueOf(tempTotalAmount)
            );
            discountAmount = discount.longValue();
        }

        // 6. Chốt tổng tiền cuối cùng
        long finalAmount = tempTotalAmount - discountAmount;
        if (finalAmount < 0) finalAmount = 0;
        order.setTotalAmount(finalAmount);

        // 7. Tạo Payment info
        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.UNPAID)
                .amount(finalAmount)
                .build();
        order.setPayments(List.of(payment));

        // 8. Lưu Order xuống DB (Cascade sẽ lưu cả Details và Payment)
        Order savedOrder = orderRepository.save(order);

        // 9. LƯU LỊCH SỬ DÙNG COUPON (Logic Mới)
        if (coupon != null) {
            // Tăng số lượt đã dùng của mã chung
            coupon.setUsageCount(coupon.getUsageCount() + 1);
            couponRepository.save(coupon);

            // Lưu bảng CouponUsage để tracking user
            CouponUsage usage = CouponUsage.builder()
                    .coupon(coupon)
                    .user(user)
                    .order(savedOrder)
                    .discountAmount(BigDecimal.valueOf(discountAmount))
                    .build();
            couponUsageRepository.save(usage);
        }

        // 10. Xóa giỏ hàng & Return
        cartService.clearCart();
        return orderMapper.toOrderResponse(savedOrder);
    }

    // --- Helper Method: Validate Coupon ---
    private void checkCouponValid(Coupon coupon, long orderValue) {
        // 1. Check active
        if (!coupon.isActive()) {
            throw new AppException(ErrorCode.COUPON_NOT_ACTIVE);
        }
        // 2. Check số lượng chung (Global limit)
        if (coupon.getMaxUsage() != null && coupon.getUsageCount() >= coupon.getMaxUsage()) {
            throw new AppException(ErrorCode.COUPON_EXPIRED);
        }
        // 3. Check ngày hết hạn
        LocalDateTime now = LocalDateTime.now();
        if (coupon.getEndDate() != null && now.isAfter(coupon.getEndDate())) {
            throw new AppException(ErrorCode.COUPON_EXPIRED);
        }
        if (coupon.getStartDate() != null && now.isBefore(coupon.getStartDate())) {
            throw new AppException(ErrorCode.COUPON_NOT_YET_VALID);
        }
        // 4. Check giá trị đơn hàng tối thiểu
        if (coupon.getMinOrderValue() != null && BigDecimal.valueOf(orderValue).compareTo(coupon.getMinOrderValue()) < 0) {
            throw new AppException(ErrorCode.COUPON_MIN_ORDER_NOT_MET);
        }
    }

    @Override
    public Page<OrderResponse> getMyOrders(Pageable pageable) {
        User user = userService.getCurrentUser();
        return orderRepository.findByUserId(user.getId(), pageable)
                .map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse getOrderDetail(String orderCode) {
        User user = userService.getCurrentUser();
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional
    public void cancelOrder(String orderCode, String reason) {
        User user = userService.getCurrentUser();
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        // Chỉ cho hủy khi đơn còn PENDING
        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new AppException(ErrorCode.ORDER_CANNOT_CANCEL); // Thêm ErrorCode này
        }

        // Hủy đơn
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setNote(order.getNote() + " [Lý do hủy: " + reason + "]");

        // HOÀN KHO (Tạo phiếu nhập lại)
        for (OrderDetail detail : order.getOrderDetails()) {
            stockMovementService.inbound(StockMovementRequest.builder()
                    .productVariantId(detail.getProductVariant().getId())
                    .branchId(order.getBranch().getId())
                    .quantity(detail.getQuantity())
                    .note("Hoàn kho do hủy đơn: " + order.getOrderCode())
                    .build());
        }

        orderRepository.save(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    private String generateOrderCode() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

}
