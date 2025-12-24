package com.khahnm04.ecommerce.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(9000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNCATEGORIZED_EXCEPTION(9001, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_EXCEPTION(9002, "Validation error", HttpStatus.BAD_REQUEST),
    DUPLICATE_ENTRY(9003, "Duplicate entry", HttpStatus.BAD_REQUEST),
    INVALID_ENUM_VALUE(9004, "Invalid enum value", HttpStatus.BAD_REQUEST),
    DATA_INTEGRITY_VIOLATION(9005, "Data integrity violation", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1000, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1001, "You do not have permission", HttpStatus.FORBIDDEN),
    LOGIN_IDENTIFIER_REQUIRED(2002, "Missing login identifier", HttpStatus.BAD_REQUEST),
    TOKEN_CREATION_FAILED(1003, "Token creation failed", HttpStatus.UNAUTHORIZED),
    PERMISSION_EXISTED(1004, "Permission already exists", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1005, "Permission not found", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1006, "Role already exists", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1007, "Role not found", HttpStatus.BAD_REQUEST),
    PERMISSION_REQUIRED(1008, "Permission cannot be blank", HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(1100, "user not found", HttpStatus.NOT_FOUND),
    USER_EXISTED(1101, "user already existed", HttpStatus.CONFLICT),
    PHONE_NUMBER_REQUIRED(1102, "phoneNumber cannot be blank", HttpStatus.BAD_REQUEST),
    EMAIL_NUMBER_REQUIRED(1103, "email number cannot be blank", HttpStatus.BAD_REQUEST),
    USERNAME_REQUIRED(1104, "username cannot be blank", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1105, "email already exists", HttpStatus.CONFLICT),
    PHONE_NUMBER_EXISTED(1106, "phone number already exists", HttpStatus.CONFLICT),
    PASSWORD_CONFIRMATION_MISMATCH(1107, "Password and confirm password do not match", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(1108, "Password do not match", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(1109, "Password cannot be blank", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_SAME_AS_OLD(1110, "New password cannot be the same as the old password", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND(1111, "Address not found", HttpStatus.NOT_FOUND),

    CATEGORY_NOT_FOUND(1200, "request not found", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(1201, "request already existed", HttpStatus.CONFLICT),
    CATEGORY_NAME_REQUIRED(1202, "request name cannot be blank", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_EXISTED(1203, "request name existed", HttpStatus.CONFLICT),
    CATEGORY_PARENT_NOT_EXISTED(1204, "request parent not existed", HttpStatus.CONFLICT),
    CATEGORY_HAS_CHILDREN(1205, "Cannot delete request because it has child categories.", HttpStatus.BAD_REQUEST),
    CANNOT_MOVE_PARENT_TO_CHILD(1206, "Cannot move parent to child", HttpStatus.BAD_REQUEST),
    DUPLICATE_CATEGORY_PRODUCT(1207, "Duplicate request product", HttpStatus.BAD_REQUEST),

    BRAND_NOT_FOUND(1300, "brand not found", HttpStatus.NOT_FOUND),
    BRAND_EXISTED(1301, "brand already existed", HttpStatus.CONFLICT),

    BANNER_NOT_FOUND(1400, "request not found", HttpStatus.NOT_FOUND),
    BANNER_EXISTED(1401, "request already existed", HttpStatus.CONFLICT),

    NEWS_NOT_FOUND(1500, "news not found", HttpStatus.NOT_FOUND),
    NEWS_EXISTED(1501, "news already existed", HttpStatus.CONFLICT),

    PRODUCT_NOT_FOUND(1600, "product not found", HttpStatus.NOT_FOUND),
    PRODUCT_EXISTED(1601, "product already existed", HttpStatus.CONFLICT),
    ATTRIBUTE_EXISTED(1602, "attribute already existed", HttpStatus.CONFLICT),
    ATTRIBUTE_NOT_FOUND(1603, "attribute not found", HttpStatus.NOT_FOUND),
    ATTRIBUTE_ALREADY_SOFT_DELETED(1604, "attribute already soft deleted", HttpStatus.CONFLICT),

    VARIANT_EXISTED(1701, "variant already existed", HttpStatus.CONFLICT),
    VARIANT_NOT_FOUND(1700, "variant not found", HttpStatus.NOT_FOUND),
    VARIANT_ALREADY_SOFT_DELETED(1702, "variant already soft deleted", HttpStatus.CONFLICT),

    PRODUCT_VARIANT_NOT_FOUND(1800, "product variant not found", HttpStatus.NOT_FOUND),
    PRODUCT_VARIANT_EXISTED(1801, "product variant sku already existed", HttpStatus.CONFLICT),
    PRODUCT_VARIANT_ALREADY_SOFT_DELETED(1802, "product variant already soft deleted", HttpStatus.CONFLICT),

    VARIANT_VALUE_EXISTED(1901, "variant value already existed", HttpStatus.CONFLICT),
    VARIANT_VALUE_NOT_FOUND(1900, "variant value not found", HttpStatus.NOT_FOUND),
    VARIANT_VALUE_ALREADY_SOFT_DELETED(1902, "variant value already soft deleted", HttpStatus.CONFLICT),

    PRODUCT_ATTRIBUTE_VALUE_EXISTED(2001, "product attribute value already existed", HttpStatus.CONFLICT),
    PRODUCT_ATTRIBUTE_VALUE_NOT_FOUND(2000, "product attribute value not found", HttpStatus.NOT_FOUND),

    PRODUCT_FAQ_NOT_FOUND(2100, "product faq not found", HttpStatus.NOT_FOUND),
    PRODUCT_FAQ_ALREADY_SOFT_DELETED(2101, "product faq already soft deleted", HttpStatus.CONFLICT),

    WISHLIST_ALREADY_EXISTED(2200, "Product already in wishlist", HttpStatus.CONFLICT),
    WISHLIST_NOT_FOUND(2201, "Product not found in wishlist", HttpStatus.NOT_FOUND),

    PRODUCT_QUESTION_NOT_FOUND(2300, "product question not found", HttpStatus.NOT_FOUND),
    INVALID_REPLY_TARGET(2301, "Invalid reply target", HttpStatus.BAD_REQUEST),

    BRANCH_NOT_FOUND(2500, "Branch not found", HttpStatus.NOT_FOUND),
    BRANCH_NAME_EXISTED(2501, "Branch name already exists", HttpStatus.BAD_REQUEST),

    INVENTORY_NOT_FOUND(2600, "Inventory not found", HttpStatus.NOT_FOUND),
    INSUFFICIENT_STOCK(2601, "Insufficient stock", HttpStatus.BAD_REQUEST),

    // Cart Errors
    CART_ITEM_NOT_FOUND(3001, "Cart item not found", HttpStatus.NOT_FOUND),
    OUT_OF_STOCK(3002, "Product is out of stock", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY(3003, "Quantity must be greater than 0", HttpStatus.BAD_REQUEST),

    // Coupon Errors
    COUPON_NOT_FOUND(4001, "Coupon not found", HttpStatus.NOT_FOUND),
    COUPON_ALREADY_EXISTS(4002, "Coupon code already exists", HttpStatus.BAD_REQUEST),
    COUPON_EXPIRED(4003, "Coupon is expired or usage limit reached", HttpStatus.BAD_REQUEST),
    COUPON_NOT_ACTIVE(4004, "Coupon is disabled", HttpStatus.BAD_REQUEST),
    COUPON_NOT_YET_VALID(4005, "Coupon is not yet valid", HttpStatus.BAD_REQUEST),
    COUPON_MIN_ORDER_NOT_MET(4006, "Order value does not meet the minimum requirement", HttpStatus.BAD_REQUEST),

    // --- CART ERRORS (Tiếp theo 3xxx) ---
    CART_EMPTY(3004, "Giỏ hàng trống", HttpStatus.BAD_REQUEST),

    // --- ORDER ERRORS (Nhóm 6xxx) ---
    ORDER_NOT_FOUND(6001, "Không tìm thấy đơn hàng", HttpStatus.NOT_FOUND),
    ORDER_CANNOT_CANCEL(6002, "Đơn hàng đã được xử lý hoặc đang vận chuyển, không thể hủy", HttpStatus.BAD_REQUEST),

    FEEDBACK_NOT_ALLOWED(6003, "Chỉ được đánh giá khi đơn hàng đã giao thành công", HttpStatus.BAD_REQUEST),
    FEEDBACK_ALREADY_EXISTS(6004, "Bạn đã đánh giá sản phẩm này rồi", HttpStatus.BAD_REQUEST),

    COUPON_ALREADY_USED_BY_USER(4007, "Bạn đã sử dụng mã giảm giá này rồi", HttpStatus.BAD_REQUEST),
    ;

    private final Integer code;
    private final String message;
    private final HttpStatusCode statusCode;

}
