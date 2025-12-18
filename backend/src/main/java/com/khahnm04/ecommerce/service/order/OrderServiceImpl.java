package com.khahnm04.ecommerce.service.order;

import com.khahnm04.ecommerce.common.enums.OrderStatus;
import com.khahnm04.ecommerce.common.enums.PaymentStatus;
import com.khahnm04.ecommerce.dto.request.inventory.StockMovementRequest;
import com.khahnm04.ecommerce.dto.request.order.OrderRequest;
import com.khahnm04.ecommerce.dto.response.cart.CartResponse;
import com.khahnm04.ecommerce.dto.response.cart.CartItemResponse;
import com.khahnm04.ecommerce.dto.response.order.OrderResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final ProductVariantRepository variantRepository;
    private final CartService cartService;
    private final CouponService couponService;
    private final StockMovementService stockMovementService; // Dùng để trừ kho
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        User user = getCurrentUser();

        // 1. Lấy giỏ hàng
        CartResponse cart = cartService.getMyCart();
        if (cart.getItems().isEmpty()) {
            throw new AppException(ErrorCode.CART_EMPTY); // Cần thêm ErrorCode này
        }

        // 2. Validate Branch
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND)); // Cần thêm ErrorCode này

        // 3. Khởi tạo Order
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

        // 4. Xử lý từng item trong giỏ -> OrderDetail + Trừ kho
        List<OrderDetail> orderDetails = new ArrayList<>();
        long tempTotalAmount = 0;

        for (CartItemResponse item : cart.getItems()) {
            ProductVariant variant = variantRepository.findById(item.getProductVariantId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

            // A. Tạo phiếu xuất kho (Tự động trừ tồn kho, nếu thiếu sẽ báo lỗi ở đây)
            stockMovementService.outbound(StockMovementRequest.builder()
                    .productVariantId(variant.getId())
                    .branchId(branch.getId())
                    .quantity(Long.valueOf(item.getQuantity()))
                    .note("Xuất kho cho đơn hàng: " + order.getOrderCode())
                    .build());

            // B. Tạo OrderDetail
            OrderDetail detail = OrderDetail.builder()
                    .order(order)
                    .productVariant(variant)
                    .quantity(Long.valueOf(item.getQuantity()))
                    // Lưu giá tại thời điểm mua (Convert BigDecimal -> Long)
                    .unitPrice(item.getPrice().longValue())
                    .build();

            orderDetails.add(detail);
            tempTotalAmount += (detail.getUnitPrice() * detail.getQuantity());
        }

        order.setOrderDetails(orderDetails);

        // 5. Tính toán tiền giảm giá (Coupon)
        long discountAmount = 0;
        if (request.getCouponCode() != null && !request.getCouponCode().isEmpty()) {
            // Service coupon trả về BigDecimal, ta ép sang Long
            BigDecimal discount = couponService.calculateCouponValue(
                    request.getCouponCode(),
                    BigDecimal.valueOf(tempTotalAmount)
            );
            discountAmount = discount.longValue();
        }

        // 6. Chốt tổng tiền (Trong DB bạn chỉ có 1 cột total_amount, tôi lưu số tiền CUỐI CÙNG khách phải trả)
        long finalAmount = tempTotalAmount - discountAmount;
        if (finalAmount < 0) finalAmount = 0;
        order.setTotalAmount(finalAmount);

        // 7. Tạo thông tin thanh toán (Payment)
        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.UNPAID)
                .amount(finalAmount)
                .build();
        order.setPayments(List.of(payment));

        // 8. Lưu Order (Cascade sẽ lưu luôn Details và Payment)
        Order savedOrder = orderRepository.save(order);

        // 9. Xóa giỏ hàng
        cartService.clearCart();

        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public Page<OrderResponse> getMyOrders(Pageable pageable) {
        User user = getCurrentUser();
        return orderRepository.findByUserId(user.getId(), pageable)
                .map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse getOrderDetail(String orderCode) {
        User user = getCurrentUser();
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
        User user = getCurrentUser();
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
        // Hàm này dành cho Admin (sẽ làm controller sau)
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    // --- Helpers ---
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private String generateOrderCode() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

}
