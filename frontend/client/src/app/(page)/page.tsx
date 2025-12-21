/* eslint-disable @next/next/no-img-element */
"use client"
import React, { JSX, useState } from 'react';
import Link from 'next/link';
import {
  Smartphone, Laptop, Headphones, Watch, House,
  Cable, Monitor, Tv, RefreshCcw, Archive,
  ChevronRight, User, GraduationCap, ArrowRightLeft, Gift, Zap,
  Package, Heart, ChevronDown // Added ChevronDown
} from 'lucide-react';
import {
  ProductGrid,
  Pagination,
  type ProductListItem
} from '@/app/components/products';

interface Subcategory {
  label: string;
  href: string;
}

interface MegaMenuColumn {
  heading: string;
  items: Subcategory[];
}

interface MenuItem {
  id: string;
  icon: JSX.Element;
  label: string;
  megaMenuContent?: MegaMenuColumn[];
}

const MENU_ITEMS: MenuItem[] = [
  {
    id: 'dien-thoai-tablet',
    icon: <Smartphone size={18} />,
    label: "Điện thoại, Tablet",
    megaMenuContent: [
      {
        heading: "Thương Hiệu",
        items: [
          { label: "iPhone", href: "/category/iphone" },
          { label: "Samsung", href: "/category/samsung" },
          { label: "Xiaomi", href: "/category/xiaomi" },
          { label: "Oppo", href: "/category/oppo" },
          { label: "Realme", href: "/category/realme" },
          { label: "Vivo", href: "/category/vivo" },
          { label: "Nokia", href: "/category/nokia" },
        ]
      },
      {
        heading: "Dòng Máy",
        items: [
          { label: "Điện thoại thông minh", href: "/category/dt-thong-minh" },
          { label: "Điện thoại phổ thông", href: "/category/dt-pho-thong" },
          { label: "Điện thoại gaming", href: "/category/dt-gaming" },
          { label: "Điện thoại camera đẹp", href: "/category/dt-camera-dep" },
          { label: "Tablet", href: "/category/tablet" },
        ]
      },
      {
        heading: "Mức Giá",
        items: [
          { label: "Dưới 5 triệu", href: "/category/duoi-5-trieu" },
          { label: "Từ 5-10 triệu", href: "/category/5-10-trieu" },
          { label: "Từ 10-20 triệu", href: "/category/10-20-trieu" },
          { label: "Trên 20 triệu", href: "/category/tren-20-trieu" },
          { label: "Máy cũ giá rẻ", href: "/category/may-cu-gia-re" },
        ]
      },
    ],
  },
  {
    id: 'laptop',
    icon: <Laptop size={18} />,
    label: "Laptop",
    megaMenuContent: [
      {
        heading: "Thương Hiệu",
        items: [
          { label: "Macbook", href: "/category/macbook" },
          { label: "Dell", href: "/category/dell" },
          { label: "HP", href: "/category/hp" },
          { label: "Lenovo", href: "/category/lenovo" },
          { label: "Acer", href: "/category/acer" },
          { label: "Asus", href: "/category/asus" },
        ]
      },
      {
        heading: "Nhu Cầu",
        items: [
          { label: "Laptop Gaming", href: "/category/laptop-gaming" },
          { label: "Laptop Văn Phòng", href: "/category/laptop-van-phong" },
          { label: "Laptop Đồ Họa", href: "/category/laptop-do-hoa" },
          { label: "Laptop Mỏng Nhẹ", href: "/category/laptop-mong-nhe" },
        ]
      },
      {
        heading: "Mức Giá",
        items: [
          { label: "Dưới 10 triệu", href: "/category/laptop-duoi-10-trieu" },
          { label: "Từ 10-20 triệu", href: "/category/laptop-10-20-trieu" },
          { label: "Trên 20 triệu", href: "/category/laptop-tren-20-trieu" },
        ]
      },
    ],
  },
  { id: 'am-thanh-mic', icon: <Headphones size={18} />, label: "Âm thanh, Mic", megaMenuContent: [] },
  { id: 'dong-ho-camera', icon: <Watch size={18} />, label: "Đồng hồ, Camera", megaMenuContent: [] },
  { id: 'gia-dung-smarthome', icon: <House size={18} />, label: "Gia dụng, Smarthome", megaMenuContent: [] },
  { id: 'phu-kien', icon: <Cable size={18} />, label: "Phụ kiện", megaMenuContent: [] },
  { id: 'pc-man-hinh', icon: <Monitor size={18} />, label: "PC, Màn hình", megaMenuContent: [] },
  { id: 'tivi-tu-lanh', icon: <Tv size={18} />, label: "Tivi, Tủ lạnh", megaMenuContent: [] },
  { id: 'thu-cu-doi-moi', icon: <RefreshCcw size={18} />, label: "Thu cũ đổi mới", megaMenuContent: [] },
  { id: 'hang-cu-gia-re', icon: <Archive size={18} />, label: "Hàng cũ giá rẻ", megaMenuContent: [] },
  { id: 'khuyen-mai-hot', icon: <Gift size={18} />, label: "Khuyến mãi hot", megaMenuContent: [] },
];

const PROMO_DATA = [
  { icon: <GraduationCap size={18} />, title: "HSSV giảm thêm", desc: "Đến 500k cho Laptop" },
  { icon: <ArrowRightLeft size={18} />, title: "Thu cũ đổi mới", desc: "Trợ giá đến 2.5 triệu" },
  { icon: <Zap size={18} />, title: "Thành viên mới", desc: "Voucher 200k" },
  { icon: <Gift size={18} />, title: "Ưu đãi sinh nhật", desc: "Quà tặng bất ngờ" },
];

// Mock Data
const REAL_IMAGE_URL = "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone_air-3_2.jpg";

const PRODUCTS: ProductListItem[] = Array(20).fill(null).map((_, i) => ({
  id: i + 1,
  name: "iPhone Air 256GB | Chính hãng",
  price: "30.590.000đ",
  oldPrice: "31.990.000đ",
  discount: "4%",
  rating: 5,
  reviewCount: 12,
  isInstallment: true,
  promoText: "Trả góp 0% - 0đ phụ thu - 0đ trả trước - kỳ hạn đến 6 tháng",
  image: REAL_IMAGE_URL,
  slug: `product-${i + 1}`
}));

const BRANDS = ["All", "Apple", "Samsung", "Xiaomi", "OPPO", "Vivo", "Nokia", "Sony"];

const TABS = [
  { id: 'phone', label: 'ĐIỆN THOẠI' },
  { id: 'tablet', label: 'MÁY TÍNH BẢNG' },
  { id: 'laptop', label: 'LAPTOP' },
];

const ITEMS_PER_PAGE = 8; // 4 columns x 2 rows = 8 items per page

export default function HomePage() {
  const [activeTab, setActiveTab] = useState('phone');
  const [currentPage, setCurrentPage] = useState(1);
  const [hoveredCategory, setHoveredCategory] = useState<MenuItem | null>(null);

  const handleFavorite = (productId: number | string) => {
    console.log('Toggle favorite:', productId);
    // TODO: Implement favorite logic
  };

  const handleMouseEnter = (category: MenuItem) => {
    setHoveredCategory(category);
  };

  const handleMouseLeave = () => {
    setHoveredCategory(null);
  };

  // Calculate pagination
  const totalPages = Math.ceil(PRODUCTS.length / ITEMS_PER_PAGE);
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
  const endIndex = startIndex + ITEMS_PER_PAGE;
  const currentProducts = PRODUCTS.slice(startIndex, endIndex);

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
    // Scroll to top of product section when page changes
    const productSection = document.getElementById('product-section');
    if (productSection) {
      productSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  };

  return (
    <div className="bg-[#f3f4f6] min-h-screen pb-[30px]">

      {/* ================= SECTION 1: HERO & MENU ================= */}
      <div className="pt-4 mb-[30px] flex justify-center">
        <div
          className="relative grid grid-cols-[224px_736px_224px] gap-2 h-[496px]"
          onMouseLeave={handleMouseLeave}
        >

          {/* CỘT 1: MENU */}
          <div className="w-[224px] h-full bg-white rounded-xl shadow-sm overflow-hidden flex flex-col py-1 z-20">
            {MENU_ITEMS.map((item) => (
              <a
                key={item.id}
                href="#"
                className="flex-1 flex items-center justify-between px-3 hover:bg-gray-100 transition-colors cursor-pointer group"
                onMouseEnter={() => handleMouseEnter(item)}
              >
                <div className="flex items-center gap-3 text-gray-600 font-medium text-[13px] group-hover:text-red-600 transition-colors">
                  <div className="w-5 flex justify-center">{item.icon}</div>
                  <span className="truncate w-[140px]">{item.label}</span>
                </div>
                <ChevronRight size={14} className="text-gray-300 group-hover:text-red-500" />
              </a>
            ))}
          </div>

          {/* Mega Menu */}
          {hoveredCategory && hoveredCategory.megaMenuContent && hoveredCategory.megaMenuContent.length > 0 && (
            <div className="absolute left-[224px] top-0 h-full bg-white rounded-xl shadow-xl border border-gray-100 z-10 p-6 grid grid-cols-3 gap-4 w-[960px] overflow-y-auto">
              {hoveredCategory.megaMenuContent.map((column, colIdx) => (
                <div key={colIdx} className="flex flex-col gap-2">
                  <h4 className="font-extrabold text-base text-gray-800 pb-2 mb-3 border-b border-gray-200 flex items-center gap-1">
                    {column.heading} <ChevronDown size={16} />
                  </h4>
                  <div className="flex flex-col gap-1">
                    {column.items.map((item, itemIdx) => (
                      <Link key={itemIdx} href={item.href} className="flex items-center gap-1 py-1.5 px-2 rounded-md text-gray-700 hover:bg-red-50 hover:text-[#d70018] text-sm transition-colors">
                        <ChevronRight size={16} className="text-[#d70018] shrink-0" />
                        {item.label}
                      </Link>
                    ))}
                  </div>
                </div>
              ))}
            </div>
          )}

          {/* Original CỘT 2 and CỘT 3, conditionally rendered */}
          {!hoveredCategory || (hoveredCategory && (!hoveredCategory.megaMenuContent || hoveredCategory.megaMenuContent.length === 0)) ? (
            <>
              {/* CỘT 2: BANNER CENTER */}
              <div className="w-[736px] h-full flex flex-col gap-2">
                <div className="flex-1 w-full bg-white rounded-xl shadow-sm overflow-hidden group relative">
                  <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:1036:450/q:100/plain/https://dashboard.cellphones.com.vn/storage/home_Nubia_Neo-3-Series-1125.jpg" alt="Homepage Banner" className="w-full h-full object-cover" />
                </div>
                <div className="h-[110px] w-full grid grid-cols-3 gap-2">
                  <div className="bg-white rounded-xl shadow-sm overflow-hidden cursor-pointer hover:shadow-md transition-shadow border border-transparent hover:border-gray-200">
                    <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:50/plain/https://dashboard.cellphones.com.vn/storage/a17-right-1125.png" alt="Promo Banner 1" className="w-full h-full object-cover" />
                  </div>
                  <div className="bg-white rounded-xl shadow-sm overflow-hidden cursor-pointer hover:shadow-md transition-shadow border border-transparent hover:border-gray-200">
                    <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:50/plain/https://dashboard.cellphones.com.vn/storage/RightBanner_Apple-Watch_12-2025.png" alt="Promo Banner 2" className="w-full h-full object-cover" />
                  </div>
                  <div className="bg-white rounded-xl shadow-sm overflow-hidden cursor-pointer hover:shadow-md transition-shadow border border-transparent hover:border-gray-200">
                    <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:50/plain/https://dashboard.cellphones.com.vn/storage/macbook-giao-xa.png" alt="Promo Banner 3" className="w-full h-full object-cover" />
                  </div>
                </div>
              </div>

              {/* CỘT 3: RIGHT SIDEBAR */}
              <div className="w-[224px] h-full flex flex-col gap-2">
                {/* Block User */}
                <div className="h-[140px] bg-white rounded-xl shadow-sm p-4 flex flex-col justify-between relative overflow-hidden group">
                  <div className="absolute top-[-25px] right-[-25px] w-24 h-24 bg-red-50 rounded-full opacity-60 z-0 group-hover:scale-110 transition-transform duration-500"></div>
                  <div className="flex items-center gap-3 z-10">
                    <div className="w-11 h-11 rounded-full bg-gray-50 p-1 ring-1 ring-gray-200">
                      <div className="w-full h-full rounded-full bg-white flex items-center justify-center text-gray-400 overflow-hidden">
                        <User size={24} />
                      </div>
                    </div>
                    <div>
                      <p className="text-[11px] text-gray-500 font-medium">Xin chào,</p>
                      <p className="text-[14px] font-bold text-gray-800 truncate w-[110px]" title="Nguyễn Minh Khánh">Minh Khánh</p>
                    </div>
                  </div>
                  <div className="grid grid-cols-2 gap-2 z-10">
                    <button className="flex flex-col items-center justify-center gap-1 bg-red-50 hover:bg-red-100 text-red-600 rounded-lg py-2 transition-colors">
                      <Package size={18} />
                      <span className="text-[10px] font-bold">Đơn hàng</span>
                    </button>
                    <button className="flex flex-col items-center justify-center gap-1 bg-gray-50 hover:bg-gray-100 text-gray-600 rounded-lg py-2 transition-colors">
                      <Heart size={18} />
                      <span className="text-[10px] font-bold">Yêu thích</span>
                    </button>
                  </div>
                </div>

                {/* Block Promo */}
                <div className="flex-1 bg-white rounded-xl shadow-sm p-3 flex flex-col overflow-hidden">
                  <div className="flex items-center justify-between mb-3">
                    <h3 className="text-[13px] font-bold text-gray-800 uppercase">Ưu đãi nổi bật</h3>
                    <div className="w-1.5 h-1.5 bg-red-600 rounded-full animate-pulse"></div>
                  </div>
                  <div className="flex-1 flex flex-col gap-1 overflow-y-auto pr-1 custom-scrollbar">
                    {PROMO_DATA.map((item, idx) => (
                      <div key={idx} className="flex items-center gap-3 p-2 rounded-lg hover:bg-red-50 transition-all cursor-pointer group">
                        <div className="w-8 h-8 rounded-full bg-red-100 flex items-center justify-center text-red-600 shrink-0 group-hover:scale-110 transition-transform">
                          {item.icon}
                        </div>
                        <div className="flex-1 min-w-0">
                          <p className="text-[12px] font-bold text-gray-700 leading-tight group-hover:text-red-700 truncate">{item.title}</p>
                          <p className="text-[10px] text-gray-500 truncate">{item.desc}</p>
                        </div>
                      </div>
                    ))}
                  </div>
                  <div className="mt-2 pt-2 border-t border-gray-100">
                    <button className="w-full py-1.5 rounded-md border border-red-100 text-red-600 text-[11px] font-bold hover:bg-red-600 hover:text-white transition-all flex items-center justify-center gap-1">
                      Xem tất cả ưu đãi <ChevronRight size={10} />
                    </button>
                  </div>
                </div>
              </div>
            </>
          ) : null}
        </div>
      </div>

      {/* ================= SECTION 2: PRODUCT LIST ================= */}
      <div className="flex justify-center">
        <div className="flex gap-[10px] w-[1200px]">

          {/* CỘT TRÁI: BANNER */}
          <div className="flex flex-col gap-[10px] w-[224px]">
            <div className="flex-1 w-full bg-white rounded-[10px] shadow-sm overflow-hidden relative group cursor-pointer hover:shadow-md transition-all">
              <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:321:795/q:90/plain/https://media-asset.cellphones.com.vn/page_configs/01KBEGF0Y5WWSYXKGKA2FEH90B.png" alt="Product Banner" className="w-full h-full object-cover" />
            </div>
            <div className="flex-1 w-full bg-white rounded-[10px] shadow-sm overflow-hidden relative group cursor-pointer hover:shadow-md transition-all">
              <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:321:795/q:90/plain/https://media-asset.cellphones.com.vn/page_configs/01KBEGF0Y5WWSYXKGKA2FEH90B.png" alt="Product Banner" className="w-full h-full object-cover" />
            </div>
          </div>

          {/* CỘT PHẢI: SẢN PHẨM */}
          <div className="w-[966px] bg-white rounded-[10px] shadow-sm flex flex-col overflow-hidden">
            {/* Header Tabs */}
            <div className="bg-white border-b border-gray-100 flex-none h-[110px]">
              <div className="grid grid-cols-3 h-[56px]">
                {TABS.map((tab) => (
                  <div
                    key={tab.id}
                    onClick={() => setActiveTab(tab.id)}
                    className={`
                      relative cursor-pointer flex items-center justify-center
                      text-[15px] font-bold uppercase transition-all select-none
                      ${activeTab === tab.id ? 'text-[#d70018]' : 'text-gray-600 hover:text-gray-900 bg-gray-50'}
                    `}
                  >
                    {tab.label}
                    {activeTab === tab.id && (
                      <>
                        <div className="absolute bottom-0 left-0 w-full h-[3px] bg-[#d70018] z-10"></div>
                        <div className="absolute inset-0 bg-gradient-to-t from-red-50 to-transparent opacity-50 pointer-events-none"></div>
                      </>
                    )}
                    {activeTab !== tab.id && (
                      <div className="absolute right-0 top-1/4 h-1/2 w-[1px] bg-gray-200"></div>
                    )}
                  </div>
                ))}
              </div>
              <div className="flex items-center gap-[8px] px-[12px] h-[54px]">
                {BRANDS.map((brand, idx) => (
                  <button key={idx} className={`px-[12px] py-[5px] rounded-[6px] text-[12px] font-medium border transition-colors ${idx === 0 ? 'bg-red-50 border-red-200 text-red-600' : 'bg-white border-gray-200 text-gray-600 hover:border-gray-400'}`}>
                    {brand}
                  </button>
                ))}
                <button className="px-[12px] py-[5px] rounded-[6px] text-[12px] font-medium border border-gray-200 text-gray-600 flex items-center gap-[4px] hover:border-gray-400 ml-auto">
                  Xem tất cả <ChevronRight size={14} />
                </button>
              </div>
            </div>

            {/* Product Grid */}
            <div className="flex-1 p-4 flex flex-col" id="product-section">
              <div className="flex-1 pb-4">
                <ProductGrid
                  products={currentProducts}
                  columns={4}
                  colGap={2}
                  rowGap={4}
                  onFavorite={handleFavorite}
                />
              </div>

              {/* Pagination */}
              <div className="flex-none pt-4 border-t border-gray-200">
                <Pagination
                  currentPage={currentPage}
                  totalPages={totalPages}
                  onPageChange={handlePageChange}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
