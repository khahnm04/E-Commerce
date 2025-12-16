import { Address, Order, UserStat, InfoItem, OrderTab } from "./types";

export const defaultUserStats: UserStat[] = [
  {
    icon: "/assets/images/user-cart.svg",
    value: "1",
    description: "Tổng số đơn hàng đã mua"
  },
  {
    icon: "/assets/images/money-icon.svg",
    value: "2.690.000đ",
    description: "Tổng tiền tích lũy Từ 01/01/2024"
  }
];

export const defaultPersonalInfo: InfoItem[] = [
  { label: "Họ và tên:", value: "Nguyễn Minh Khánh" },
  { label: "Giới tính:", value: "-" },
  { label: "Ngày sinh:", value: "18/11/2004" }
];

export const defaultContactInfo: InfoItem[] = [
  { label: "Số điện thoại:", value: "0387011685" },
  { label: "Email:", value: "khanhnguyenminh1811@gmail.com" },
  { label: "Địa chỉ mặc định:", value: "Số nhà 11, 22 Kim Quan, Phường Việt Hưng, Quận Long Biên, Hà Nội" }
];

export const defaultAddresses: Address[] = [
  {
    name: "Nguyễn Minh Khánh",
    phone: "0387011685",
    address: "Số nhà 11, 22 Kim Quan, Phường Việt Hưng, Quận Long Biên, Hà Nội",
    isDefault: true,
    type: "Nhà"
  },
  {
    name: "Nguyễn Minh Khánh",
    phone: "0387011685",
    address: "Số nhà 12, 23 Lê Lợi, Phường Bến Nghé, Quận 1, TP. Hồ Chí Minh",
    isDefault: false,
    type: "Công ty"
  },
  {
    name: "Nguyễn Minh Khánh",
    phone: "0387011685",
    address: "Số nhà 13, 24 Trần Hưng Đạo, Phường Cửa Nam, Quận Hoàn Kiếm, Hà Nội",
    isDefault: false,
    type: "Nhà"
  },
  {
    name: "Nguyễn Minh Khánh",
    phone: "0387011685",
    address: "Số nhà 14, 25 Nguyễn Du, Phường Bến Thành, Quận 1, TP. Hồ Chí Minh",
    isDefault: false,
    type: "Công ty"
  }
];

export const defaultOrders: Order[] = [
  {
    orderId: "#00186S2502000743",
    orderDate: "12/02/2025",
    status: "Đã nhận hàng",
    statusColor: "text-[#10B981]",
    product: {
      name: "MÀN HÌNH GAMING VIEWSONIC VX2479-HD-PRO 180HZ 24 (FHD/IPS/180HZ/1MS)",
      image: "/assets/images/pc.png",
      price: "3.990.000đ",
      vat: "Đã xuất VAT"
    },
    total: "2.690.000đ"
  }
];

export const orderTabs: OrderTab[] = [
  { id: "all", label: "Tất cả" },
  { id: "pending", label: "Chờ xác nhận" },
  { id: "confirmed", label: "Đã xác nhận" },
  { id: "shipping", label: "Đang vận chuyển" },
  { id: "delivered", label: "Đã giao hàng" },
  { id: "cancelled", label: "Đã hủy" }
];

