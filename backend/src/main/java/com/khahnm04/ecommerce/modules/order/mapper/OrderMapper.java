package com.khahnm04.ecommerce.modules.order.mapper;

import com.khahnm04.ecommerce.modules.order.dto.response.OrderDetailResponse;
import com.khahnm04.ecommerce.modules.order.dto.response.OrderResponse;
import com.khahnm04.ecommerce.modules.order.entity.Order;
import com.khahnm04.ecommerce.modules.order.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // --- 1. Map Order ---
    @Mapping(source = "user.id", target = "userId")
    // Tạo địa chỉ đầy đủ từ các trường con
    @Mapping(target = "fullAddress", expression = "java(buildFullAddress(order))")
    // Map danh sách chi tiết
    @Mapping(source = "orderDetails", target = "orderDetails")

    // Lưu ý: Trong DB của bạn chỉ có total_amount (là số tiền cuối cùng khách phải trả)
    // Nên ta map thẳng nó vào finalAmount và totalAmount của response
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "totalAmount", target = "finalAmount")
    OrderResponse toOrderResponse(Order order);

    List<OrderResponse> toOrderResponseList(List<Order> orders);

    // --- 2. Map OrderDetail ---
    @Mapping(source = "productVariant.id", target = "productVariantId")
    @Mapping(source = "productVariant.product.name", target = "productName")
    @Mapping(source = "productVariant.sku", target = "productSku")
    @Mapping(source = "productVariant.image", target = "productImage")

    // Tính thành tiền từng món = unitPrice * quantity
    @Mapping(target = "subTotal", expression = "java(calculateSubTotal(detail))")

    // Check xem đã đánh giá chưa
    @Mapping(target = "hasFeedback", expression = "java(detail.getFeedback() != null)")
    OrderDetailResponse toOrderDetailResponse(OrderDetail detail);

    // --- Helper Methods ---

    default String buildFullAddress(Order order) {
        return String.format("%s, %s, %s, %s",
                order.getShippingAddressDetail(),
                order.getShippingWard(),
                order.getShippingDistrict(),
                order.getShippingProvince());
    }

    default Long calculateSubTotal(OrderDetail detail) {
        if (detail.getUnitPrice() == null || detail.getQuantity() == null) return 0L;
        return detail.getUnitPrice() * detail.getQuantity();
    }

}
