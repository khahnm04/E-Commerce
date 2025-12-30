create database db_ecommerce;
use db_ecommerce;

-- Bảng users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE,            -- Email (Quan trọng)
    phone_number VARCHAR(15) UNIQUE,      -- SĐT (Quan trọng)
    password VARCHAR(500) NULL,           -- NULL nếu login bằng Google/FB
    full_name VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    gender ENUM('MALE', 'FEMALE', 'UNKNOWN') DEFAULT 'UNKNOWN',
    provider VARCHAR(50) DEFAULT 'LOCAL', -- 'LOCAL', 'GOOGLE', 'FACEBOOK'
    provider_id VARCHAR(255),             -- ID trả về từ Google/FB (Sub ID)
    is_email_verified BOOLEAN DEFAULT FALSE,
    is_phone_verified BOOLEAN DEFAULT FALSE,
    status ENUM('ACTIVE', 'INACTIVE', 'BANNED', 'LOCKED') DEFAULT 'ACTIVE',
    last_login_at DATETIME,               -- Lần cuối đăng nhập
    -- login_ip VARCHAR(50),                 -- IP lần cuối đăng nhập (để trace hack)
    -- branch_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
    -- CONSTRAINT fk_user__branchs FOREIGN KEY (branch_id) REFERENCES branchs(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng role
CREATE TABLE roles (
	name VARCHAR(255) PRIMARY KEY,
    description TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng user_roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_name),
    CONSTRAINT fk_user_roles__users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles__roles FOREIGN KEY (role_name) REFERENCES roles(name)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng permission
CREATE TABLE permissions (
	name VARCHAR(255) PRIMARY KEY,
    description TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng role_permissions
CREATE TABLE role_permissions (
	role_name VARCHAR(255) NOT NULL,
    permission_name VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (role_name, permission_name),
    CONSTRAINT fk_role_permissions__roles FOREIGN KEY (role_name) REFERENCES roles(name) ON DELETE CASCADE,
    CONSTRAINT fk_role_permissions__permissions FOREIGN KEY (permission_name) REFERENCES permissions(name)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng address
CREATE TABLE addresses (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    receiver_name VARCHAR(255) NOT NULL,  -- Tên người nhận (VD: Anh shipper gọi cho ai?)
    receiver_phone VARCHAR(15) NOT NULL,  -- SĐT người nhận
    province VARCHAR(255) NOT NULL,
    district VARCHAR(255) NOT NULL,
    ward VARCHAR(255) NOT NULL,
    detail_address VARCHAR(255) NOT NULL,   -- Số nhà, tên đường
    address_name VARCHAR(255),
    address_type ENUM('HOME', 'WORK', 'OTHER') DEFAULT 'HOME',
    is_default BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_addresses__users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- bảng brands
CREATE TABLE brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    logo TEXT,
    country VARCHAR(255),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- bảng categories
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    image TEXT,
    parent_id BIGINT,
    position INT NOT NULL DEFAULT 0,
    path VARCHAR(255),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_categories__parent FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE SET NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng news
CREATE TABLE news (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL,       -- Tiêu đề (VD: Đánh giá iPhone 15)
    slug VARCHAR(255) NOT NULL UNIQUE, -- URL thân thiện (VD: danh-gia-iphone-15)
    image TEXT,                        -- Ảnh đại diện (Thumbnail) hiện ở danh sách
    summary TEXT,                      -- Sapo/Mô tả ngắn (Quan trọng cho SEO & Tốc độ load danh sách)
    content LONGTEXT NOT NULL,         -- Nội dung full (HTML), dùng LONGTEXT để không bị cắt chữ
    view_count BIGINT DEFAULT 0,       -- Đếm lượt xem
    is_featured BOOLEAN DEFAULT FALSE, -- Bài nổi bật (Ghim lên đầu)
    status ENUM('DRAFT', 'PUBLISHED', 'HIDDEN') DEFAULT 'DRAFT',
    published_at DATETIME DEFAULT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- bảng products
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    price BIGINT NOT NULL,                -- Giá bán hiện tại (Thường là giá Min của variant)
    old_price BIGINT DEFAULT NULL,        -- Cho phép NULL (Nếu không giảm giá)
    thumbnail TEXT NOT NULL,
    description LONGTEXT NOT NULL,
    brand_id BIGINT,
    status ENUM('ACTIVE', 'INACTIVE', 'OUT_OF_STOCK', 'PENDING', 'DISCONTINUED') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_products__brands FOREIGN KEY (brand_id) REFERENCES brands(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng product_categories
CREATE TABLE product_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    CONSTRAINT fk_product_categories__products FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_product_categories__categories FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    UNIQUE (product_id, category_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng product_images
CREATE TABLE product_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    image_url TEXT NOT NULL,
    display_order INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_product_images__products FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng attributes
CREATE TABLE attributes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL, 
    code VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng product_attribute_values
CREATE TABLE product_attribute_values (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    value VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    CONSTRAINT fk_product_attribute_values__products FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_product_attribute_values__attributes FOREIGN KEY (attribute_id) REFERENCES attributes(id) ON DELETE RESTRICT,
    UNIQUE (product_id, attribute_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng variants
CREATE TABLE variants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng variant_values
CREATE TABLE variant_values (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    variant_id BIGINT NOT NULL,
    value VARCHAR(255) NOT NULL,
    code VARCHAR(255), 
    display_order INT DEFAULT 0, -- Sắp xếp (Quan trọng cho dung lượng: 128 < 256 < 512 < 1TB)
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_variant_values__variants FOREIGN KEY (variant_id) REFERENCES variants(id) ON DELETE CASCADE,
    UNIQUE (variant_id, value)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng product_variants
CREATE TABLE product_variants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    sku VARCHAR(255) NOT NULL UNIQUE, -- VD: IP15-PRO-BLK-256
    price BIGINT NOT NULL,
    old_price BIGINT DEFAULT NULL,
    image TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_product_variants__products FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng product_variant_values
CREATE TABLE product_variant_values (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_variant_id BIGINT NOT NULL,
    variant_value_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    CONSTRAINT fk_product_variant_values__product_variants FOREIGN KEY (product_variant_id) REFERENCES product_variants(id) ON DELETE CASCADE,
    CONSTRAINT fk_product_variant_values__variant_values FOREIGN KEY (variant_value_id) REFERENCES variant_values(id) ON DELETE CASCADE,
    UNIQUE (product_variant_id, variant_value_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng product_faqs
CREATE TABLE product_faqs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    question VARCHAR(500) NOT NULL, 
    answer TEXT NOT NULL,
    display_order INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_product_faqs__products FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng wishlists
CREATE TABLE wishlists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wishlists__users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_wishlists__products FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE (user_id, product_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng product_questions
CREATE TABLE product_questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    parent_id BIGINT NULL, 
    content TEXT NOT NULL,
    is_admin_reply BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_questions__products FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_product_questions__users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_product_questions__parents FOREIGN KEY (parent_id) REFERENCES product_questions(id) ON DELETE SET NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng branches
CREATE TABLE branches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE, -- Tên chi nhánh (VD: CellphoneS Thái Hà)
    address VARCHAR(255) NOT NULL, -- Địa chỉ cụ thể (VD: 11 Thái Hà, Đống Đa)
    city VARCHAR(255) NOT NULL, -- Thành phố/Tỉnh (Quan trọng để khách lọc nhanh: Hà Nội, HCM...)
    phone_number VARCHAR(20),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng inventories
CREATE TABLE inventories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_variant_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    quantity BIGINT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_inventories__variants FOREIGN KEY (product_variant_id) REFERENCES product_variants(id),
    CONSTRAINT fk_inventories__branches FOREIGN KEY (branch_id) REFERENCES branches(id),
    UNIQUE (product_variant_id, branch_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE stock_movements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inventory_id BIGINT NOT NULL,
    type ENUM('IN','OUT','ADJUSTMENT') NOT NULL,
    quantity BIGINT NOT NULL CHECK (quantity > 0),
    reference_id BIGINT DEFAULT NULL,
    note TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    CONSTRAINT fk_movements__inventories FOREIGN KEY (inventory_id) REFERENCES inventories(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng carts
CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    coupon_code VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_carts__users FOREIGN KEY (user_id) REFERENCES users(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng cart_items
CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_variant_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cart_items__carts FOREIGN KEY (cart_id) REFERENCES carts(id),
    CONSTRAINT fk_cart_items__variants FOREIGN KEY (product_variant_id) REFERENCES product_variants(id),
    UNIQUE (cart_id, product_variant_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng coupons
CREATE TABLE coupons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,          -- Mã code (VD: SALE50)
    discount_type VARCHAR(20) NOT NULL,        -- PERCENT (Phần trăm) hoặc FIXED (Tiền mặt)
    discount_value DECIMAL(15, 2) NOT NULL,    -- Giá trị giảm (VD: 10% hoặc 50.000đ)
    min_order_value DECIMAL(15, 2),            -- Đơn tối thiểu để dùng mã
    max_usage INT,                             -- Tổng số lượt dùng tối đa
    usage_count INT DEFAULT 0,                 -- Số lượt đã dùng
    start_date DATETIME,
    end_date DATETIME,
    active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng orders
CREATE TABLE orders (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_code VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    total_amount BIGINT NOT NULL,
    order_status ENUM(
		'PENDING',          -- Chờ xử lý
		'CONFIRMED',        -- Đã xác nhận
		'PROCESSING',       -- Đang đóng gói/lấy hàng
		'SHIPPED',          -- Đã giao cho đơn vị vận chuyển
		'DELIVERED',        -- Giao thành công (Khách đã nhận)
		'CANCELLED',        -- Hủy (Trước khi giao)
		'DELIVERY_FAILED',  -- Giao thất bại (Khách không nghe máy/Bom hàng)
		'RETURNED'          -- Trả hàng/Hoàn tiền
	) DEFAULT 'PENDING',
	shipping_name VARCHAR(255) NOT NULL,
    shipping_phone VARCHAR(15) NOT NULL,
    shipping_province VARCHAR(255) NOT NULL,
    shipping_district VARCHAR(255) NOT NULL,
    shipping_ward VARCHAR(255) NOT NULL,
    shipping_address_detail VARCHAR(255) NOT NULL,
    note TEXT,
    required_date DATE, -- Ngày dự kiến giao
	shipped_date DATE,  -- Thời gian hoàn thành order_details
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_orders__users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_orders__branches FOREIGN KEY (branch_id) REFERENCES branches(id)
);

-- Bảng order_details
CREATE TABLE order_details (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	order_id BIGINT NOT NULL,
	product_variant_id BIGINT NOT NULL,
    unit_price BIGINT NOT NULL,
    quantity BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_order_details__orders FOREIGN KEY (order_id) REFERENCES orders(id),
	CONSTRAINT fk_order_details__product_variants FOREIGN KEY (product_variant_id) REFERENCES product_variants(id),
    UNIQUE (order_id, product_variant_id)
);

-- Bảng feedbacks
CREATE TABLE feedbacks (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_detail_id BIGINT NOT NULL UNIQUE,
    rating TINYINT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    content TEXT NOT NULL,
    images JSON,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_feedbacks__order_details FOREIGN KEY (order_detail_id) REFERENCES order_details(id)
);

-- Bảng payments
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,                  -- Liên kết đơn hàng
    payment_method ENUM('CASH','CARD','TRANSFER','WALLET') NOT NULL,
	payment_status ENUM('UNPAID','PAID','REFUNDED') DEFAULT 'UNPAID',
    amount BIGINT NOT NULL,                    -- Số tiền thanh toán
    transaction_code VARCHAR(255),             -- Mã giao dịch từ ngân hàng/cổng thanh toán
    paid_at DATETIME,                          -- Thời gian thanh toán
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_payments__orders FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE coupon_usages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    discount_amount DECIMAL(15, 2) NOT NULL, -- Lưu lại giá trị đã giảm tại thời điểm đó
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usages__coupons FOREIGN KEY (coupon_id) REFERENCES coupons(id),
    CONSTRAINT fk_usages__users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_usages__orders FOREIGN KEY (order_id) REFERENCES orders(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE news_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    news_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    CONSTRAINT fk_news_products__news FOREIGN KEY (news_id) REFERENCES news(id) ON DELETE CASCADE,
    CONSTRAINT fk_news_products__products FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE (news_id, product_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng banners
CREATE TABLE banners (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    image TEXT NOT NULL,
    link_url VARCHAR(500),
    position VARCHAR(50) NOT NULL DEFAULT 'HOME_SLIDER',
    display_order INT DEFAULT 0,         -- Số càng nhỏ càng ưu tiên hiện trước
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;



