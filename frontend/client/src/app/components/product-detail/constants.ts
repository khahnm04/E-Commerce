import React from 'react';
import {
  Star, Play, Smartphone, ShieldCheck, Box, Tag
} from 'lucide-react';
import type {
  Product,
  ProductVersion,
  ProductColor,
  Commitment,
  Spec,
  DescriptionBlock,
  Review,
  FeatureRating,
  RatingDistribution,
  ReviewFilter,
  QnAQuestion,
  RelatedNews,
  SimilarProduct
} from './types';

export const BRAND_COLOR = '#d70018';
export const BRAND_COLOR_LIGHT = '#fffefe';
export const BRAND_COLOR_HOVER = '#ef0019';

export const PRODUCT: Product = {
  name: "Đồng hồ thông minh Huawei Watch Fit 4",
  rating: 4.8,
  reviews: 10,
  price: "2.740.000đ",
  oldPrice: "3.090.000đ",
  image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/_/n/_ng_h_th_ng_minh_huawei_watch_fit_4_-_2.png",
  thumbnails: [
    { type: "video", icon: React.createElement(Play, { size: 20 }), label: "Video" },
    { type: "feature", icon: React.createElement(Star, { size: 20 }), label: "Tính năng nổi bật" },
    { type: "img", src: "https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/_/n/_ng_h_th_ng_minh_huawei_watch_fit_4_-_2.png" },
    { type: "img", src: "https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/_/n/_ng_h_th_ng_minh_huawei_watch_fit_4_-_2.png" },
    { type: "img", src: "https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/_/n/_ng_h_th_ng_minh_huawei_watch_fit_4_-_2.png" },
  ]
};

export const VERSIONS: ProductVersion[] = [
  { name: "Fit 4 Pro Dây Nylon" },
  { name: "Fit 4 Pro" },
  { name: "Fit 4" },
  { name: "Fit 4 NFC Dây Nylon" }
];

export const COLORS: ProductColor[] = [
  { name: "Đen", price: "2.740.000đ" },
  { name: "Tím", price: "2.740.000đ" },
  { name: "Trắng", price: "2.740.000đ" }
];

export const COMMITMENTS: Commitment[] = [
  { 
    icon: React.createElement(Smartphone, { size: 20, className: "text-[#d70018]" }), 
    desc: "Mới, đầy đủ phụ kiện từ nhà sản xuất" 
  },
  { 
    icon: React.createElement(ShieldCheck, { size: 20, className: "text-[#d70018]" }), 
    desc: React.createElement(React.Fragment, null, 
      "Bảo hành 12 tháng chính hãng 1 đổi 1 trong 15 ngày nếu có lỗi phần cứng từ NSX. ",
      React.createElement('a', { href: "#", className: "text-blue-600 hover:underline" }, "Xem chi tiết")
    )
  },
  { 
    icon: React.createElement(Box, { size: 20, className: "text-[#d70018]" }), 
    desc: "Đồng hồ, Đế sạc (bao gồm cáp sạc), Hướng dẫn sử dụng & Thông tin an toàn & Thẻ bảo hành" 
  },
  { 
    icon: React.createElement(Tag, { size: 20, className: "text-[#d70018]" }), 
    desc: React.createElement(React.Fragment, null,
      "Giá sản phẩm ",
      React.createElement('span', { className: "font-bold" }, "đã bao gồm thuế VAT"),
      ", có hỗ trợ ",
      React.createElement('a', { href: "#", className: "text-blue-600 hover:underline" }, "hoàn thuế VAT - Tax Refund"),
      " cho khách du lịch."
    )
  }
];

export const SPECS: Spec[] = [
  { k: "Công nghệ màn hình", v: "AMOLED" },
  { k: "Kích thước màn hình", v: "1.82 inch" },
  { k: "Đường kính mặt", v: "43 mm" },
  { k: "Kích thước cổ tay phù hợp", v: "Tím: 120-190 mm\nXám, Trắng và Đen: 130-210 mm" },
  { k: "Nghe,gọi", v: "Nghe gọi qua Bluetooth" },
  { k: "Tiện ích sức khoẻ", v: "Chế độ luyện tập, Theo dõi giấc ngủ, Đo nhịp tim, Đo lượng oxy trong máu, Đếm bước chân..." },
  { k: "Tương thích", v: "Android 8.0 trở lên\niOS 13.0 trở lên" },
  { k: "Thời lượng pin", v: "Thời lượng sử dụng tối đa 10 ngày\nThời lượng sử dụng thường xuyên 7 ngày" },
  { k: "Hãng sản xuất", v: "Huawei" },
];

export const DESCRIPTION_CONTENT: DescriptionBlock[] = [
  { 
    type: 'text', 
    content: 'Huawei Watch Fit 4 sở hữu thiết kế thời thượng với mặt đồng hồ vuông vức, bo cong nhẹ nhàng ở các góc tạo cảm giác mềm mại nhưng không kém phần hiện đại. Khung viền kim loại chắc chắn kết hợp cùng dây đeo silicone êm ái, mang lại trải nghiệm đeo thoải mái suốt cả ngày dài.' 
  },
  { 
    type: 'image', 
    src: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/_/n/_ng_h_th_ng_minh_huawei_watch_fit_4_-_2.png', 
    alt: 'Thiết kế thời thượng' 
  },
  { 
    type: 'header', 
    content: 'Màn hình AMOLED sắc nét, hiển thị sống động' 
  },
  { 
    type: 'text', 
    content: 'Sở hữu màn hình AMOLED kích thước 1.82 inch với độ phân giải cao, Huawei Watch Fit 4 mang đến khả năng hiển thị sắc nét ngay cả dưới trời nắng gắt. Viền màn hình siêu mỏng giúp không gian hiển thị rộng rãi hơn, cho phép bạn dễ dàng thao tác và quan sát thông tin.' 
  },
  { 
    type: 'header', 
    content: 'Theo dõi sức khỏe chuyên sâu 24/7' 
  },
  { 
    type: 'text', 
    content: 'Đồng hồ được trang bị hệ thống cảm biến TruSeen™ 5.5 mới nhất, giúp theo dõi nhịp tim, nồng độ oxy trong máu (SpO2), mức độ căng thẳng và giấc ngủ với độ chính xác cao. Đặc biệt, tính năng quản lý chu kỳ kinh nguyệt thông minh là trợ thủ đắc lực cho phái nữ.' 
  }
];

export const RELATED_NEWS: RelatedNews[] = [
  { 
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:150:150/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_-_2025-12-04t105534.549.png', 
    title: 'Đánh giá chi tiết Huawei Watch Fit 4: Nâng cấp toàn diện, pin trâu 10 ngày' 
  },
  { 
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:150:150/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_-_2025-12-04t105534.549.png', 
    title: 'So sánh Huawei Watch Fit 4 và Fit 3: Có đáng để lên đời?' 
  },
  { 
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:150:150/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_-_2025-12-04t105534.549.png', 
    title: 'Top 5 đồng hồ thông minh dưới 3 triệu đáng mua nhất 2024' 
  }
];

export const SIMILAR_PRODUCTS: SimilarProduct[] = [
  { n: "Samsung Galaxy Watch 6", p: "3.990.000đ" },
  { n: "Xiaomi Watch S3", p: "2.590.000đ" },
  { n: "Garmin Venu Sq 2", p: "5.490.000đ" },
];

export const REVIEWS: Review[] = [
  {
    id: 1,
    name: "Bế Quang Ninh",
    avatarColor: "bg-purple-600",
    rating: 5,
    ratingText: "Tuyệt vời",
    time: "3 tháng trước",
    verified: false,
    content: "sản phẩm có đo được huyết áp không",
    tags: []
  },
  {
    id: 2,
    name: "Hương Can",
    avatarColor: "bg-green-600",
    rating: 5,
    ratingText: "Tuyệt vời",
    time: "4 tháng trước",
    verified: false,
    content: "Rất hài lòng",
    tags: []
  },
  {
    id: 3,
    name: "Phuc 21",
    avatarColor: "bg-red-800",
    rating: 5,
    ratingText: "Tuyệt vời",
    time: "4 tháng trước",
    verified: true,
    content: "Không lấy quà nên được trừ 400k. Rất hài lòng về chất lượng sản phẩm và chất lượng phục vụ",
    tags: ["Thời lượng pin Cực khoẻ", "Đo chỉ số sức khoẻ Chính xác tuyệt đối", "Tiện ích thông minh Đa dạng", "Cảm giác đeo Rất thoải mái"]
  },
  {
    id: 4,
    name: "Le Dung",
    avatarColor: "bg-teal-700",
    rating: 5,
    ratingText: "Tuyệt vời",
    time: "4 tháng trước",
    verified: false,
    content: "Có thu cũ đổi mới kg shop",
    tags: ["Thời lượng pin Cực khoẻ", "Đo chỉ số sức khoẻ Chính xác tuyệt đối", "Tiện ích thông minh Đa dạng", "Cảm giác đeo Rất thoải mái"]
  },
  {
    id: 5,
    name: "Anh Quang",
    avatarColor: "bg-blue-800",
    rating: 5,
    ratingText: "Tuyệt vời",
    time: "5 tháng trước",
    verified: true,
    content: "Sản phẩm tốt. Thời Trang. Nhiều tiện ích thông minh. Tuy nhiên, đồng ở của tôi không báo khi có cuộc gọi zalo (cuộc gọi qua sim và tin nhắn zalo thì vẫn báo). Huawei Watch Fit 4 có dùng khi bơi được không? Bản Pro thì nói có hỗ trợ lặn sâu 40m. Bản thường không nói rõ việc sử dụng dưới nước.",
    tags: ["Thời lượng pin Cực khoẻ", "Đo chỉ số sức khoẻ Chính xác tuyệt đối", "Tiện ích thông minh Đa dạng", "Cảm giác đeo Rất thoải mái"]
  }
];

export const FEATURE_RATINGS: FeatureRating[] = [
  { label: "Thời lượng pin", score: 5, count: 3 },
  { label: "Đo chỉ số sức khoẻ", score: 5, count: 3 },
  { label: "Tiện ích thông minh", score: 5, count: 3 },
  { label: "Cảm giác đeo", score: 5, count: 3 },
];

export const RATING_DISTRIBUTION: RatingDistribution[] = [
  { star: 5, percentage: 80, count: 8 },
  { star: 4, percentage: 20, count: 2 },
  { star: 3, percentage: 0, count: 0 },
  { star: 2, percentage: 0, count: 0 },
  { star: 1, percentage: 0, count: 0 },
];

export const REVIEW_FILTERS: ReviewFilter[] = [
  { label: "Tất cả", active: true },
  { label: "Có hình ảnh", active: false },
  { label: "Đã mua hàng", active: false },
  { label: "5 sao", active: false },
  { label: "4 sao", active: false },
  { label: "3 sao", active: false },
];

export const QNA_QUESTIONS: QnAQuestion[] = [
  {
    user: "Nguyễn Hùng",
    userInitial: "N",
    question: "Có thu củ đối mới ko shop,tôi cần đối giảsốy watch 5",
    time: "3 tuần trước",
    admin: "Quản Trị Viên",
    adminInitial: "Q",
    answer: "Cellphones xin chào anh Nguyễn Hùng. Đa sản phẩm Đồng hồ thông minh Huawei Watch Fit 4 với tình trạng máy chính hãng, đẹp không lỗi, đủ phụ kiện giá thu dự kiến 1.200.000đ. Đúng hình thức, chức năng máy không lỗi sẽ được nhập lại với mức giá dự kiến như trên. Minh mang máy đến cửa hàng bất kỳ để kỹ thuật viên thẩm định sẽ, cũng là một số tiền thay đổi liên tục ạ. Đa rất tiếc sản phẩm Samsung Galaxy Watch 5 tạm hết hàng ạ. Em gửi mình tham khảo SAMSUNG GALAXY WATCH 7 BT 40MM XANH (L300) giá hiện tại 3.990.000đ khu vực miền nam. Mình thu có lên đời SAMSUNG GALAXY WATCH 7 BT 40MM XANH (L300) giá bù dự kiến 2.430.000 (đã áp dụng ưu đãi trợ giá). Không biết hiện tại mình đang ở khu vực nào của HCM/HN hay tỉnh thành nào để em kiểm tra cửa hàng gần mình nhất có sẵn sản phẩm ạ. Thân mến"
  },
  {
    user: "HOANG ANH",
    userInitial: "H",
    question: "Hôm nay 28/11 giá fĩ 4 màu trắng giá bư?",
    time: "3 tuần trước",
    admin: "Quản Trị Viên",
    adminInitial: "Q",
    answer: "Cellphones xin chào anh Hoang Anh. Đồng hồ thông minh Huawei Watch Fit 4 dây cao su trắng giá hiện tại 2.520.000 (HCM), ưu đãi BLACK FRIDAY từ ngày 28/11 - 01/12: Nhập mã BMWTCHS00K giảm thêm 6% tối đa 500.000đ (Không áp dụng kèm giá Flashsale) Ngày cuối chương trình, chỉ áp dụng thanh toán online 100% hoặc thanh toán tại cửa hàng. Giảm BMWTCHS00K còn 2.318.000. Mình ở khu vực tỉnh thành nào, em kiểm tra và đặt hàng ạ. Thân mến"
  },
  {
    user: "HOANG ANH",
    userInitial: "H",
    question: "Gò vấp tphcm nhé.",
    time: "3 tuần trước",
    admin: null,
    adminInitial: null,
    answer: null
  }
];

