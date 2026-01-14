/* eslint-disable @next/next/no-img-element */
"use client"
import React, { useState } from 'react';
import { ChevronRight } from 'lucide-react';
import {
  ProductGrid,
  ProductFilters,
  ProductSort,
  type ProductListItem,
  type Brand
} from '@/app/components/products';

// Mock Data
const BRANDS: Brand[] = [
  { id: 'all', name: 'Tất cả' },
  {
    id: 'apple',
    name: 'Apple',
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:50:0/q:70/plain/https://cellphones.com.vn/media/tmp/catalog/product/t/h/thuong-hieu-apple.png'
  },
  {
    id: 'samsung',
    name: 'Samsung',
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:50:0/q:70/plain/https://cellphones.com.vn/media/tmp/catalog/product/t/h/thuong-hieu-samsung.png'
  },
  {
    id: 'xiaomi',
    name: 'Xiaomi',
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:50:0/q:70/plain/https://cellphones.com.vn/media/tmp/catalog/product/t/h/thuong-hieu-xiaomi.png'
  },
  {
    id: 'oppo',
    name: 'OPPO',
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:50:0/q:70/plain/https://cellphones.com.vn/media/tmp/catalog/product/t/h/thuong-hieu-oppo.png'
  },
  {
    id: 'vivo',
    name: 'Vivo',
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:50:0/q:70/plain/https://cellphones.com.vn/media/tmp/catalog/product/t/h/thuong-hieu-vivo.png'
  },
];

const PRODUCTS: ProductListItem[] = Array(20).fill(null).map((_, i) => ({
  id: i + 1,
  name: i % 2 === 0
    ? "iPhone 15 Pro Max 256GB | Chính hãng VN/A"
    : "Samsung Galaxy S24 Ultra 12GB 512GB",
  price: i % 2 === 0 ? "29.990.000đ" : "28.590.000đ",
  oldPrice: i % 2 === 0 ? "34.990.000đ" : "33.990.000đ",
  discount: i % 2 === 0 ? "15%" : "18%",
  rating: 4.8,
  reviews: 156,
  isInstallment: true,
  tags: i % 3 === 0 ? ["Mới", "Hot"] : i % 3 === 1 ? ["Giảm sâu"] : [],
  image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-17-pro-256-gb.png",
  promoText: "Thu cũ đổi mới giảm thêm 2 triệu",
  slug: `product-${i + 1}`
}));

export default function ProductListingPage() {
  const [selectedBrand, setSelectedBrand] = useState('all');
  const [selectedSort, setSelectedSort] = useState('featured');
  const [selectedPriceRanges, setSelectedPriceRanges] = useState(['all']);

  const handleFavorite = (productId: number | string) => {
    console.log('Toggle favorite:', productId);
    // TODO: Implement favorite logic
  };

  const handleQuickFilterClick = (filter: unknown) => {
    console.log('Quick filter:', filter);
    // TODO: Implement quick filter logic
  };

  const handleSortChange = (sortValue: string) => {
    setSelectedSort(sortValue);
    console.log('Sort changed:', sortValue);
    // TODO: Implement sort logic
  };

  const handlePriceRangeChange = (priceRange: string) => {
    // Radio button behavior: only one value at a time
    setSelectedPriceRanges([priceRange]);
  };

  return (
    <div className="products-page bg-gray-50 min-h-screen py-4 font-sans text-gray-800">
      <div className="products-container w-full max-w-[1200px] mx-auto px-4">

        {/* Breadcrumb */}
        <nav className="products-breadcrumb flex items-center gap-2 text-[13px] text-gray-500 mb-4" aria-label="Breadcrumb">
          <a href="#" className="hover:text-[#d70018] transition-colors">Trang chủ</a>
          <ChevronRight size={12} />
          <span className="text-gray-800 font-medium">Điện thoại</span>
        </nav>

        {/* Filters & Sort */}
        <ProductFilters
          brands={BRANDS}
          selectedBrand={selectedBrand}
          onBrandChange={setSelectedBrand}
          selectedPriceRanges={selectedPriceRanges}
          onPriceRangeChange={handlePriceRangeChange}
          totalProducts={PRODUCTS.length}
          selectedSort={selectedSort}
          onSortChange={handleSortChange}
        />

        {/* Product Grid */}
        <ProductGrid
          products={PRODUCTS}
          columns={5}
          onFavorite={handleFavorite}
        />

        {/* Load More */}
        <div className="products-load-more mt-8 flex justify-center">
          <button className="products-load-more-btn px-12 py-2.5 bg-white border border-gray-300 rounded-lg text-[13px] font-medium text-gray-700 hover:bg-gray-50 hover:border-gray-400 hover:text-gray-900 transition-all shadow-sm">
            Xem thêm 128 sản phẩm
          </button>
        </div>

        {/* SEO Content */}
        <section className="products-seo-content mt-10 bg-white rounded-xl p-6 border border-gray-100 shadow-sm">
          <h2 className="products-seo-title text-base font-bold text-gray-800 mb-3 uppercase">
            Điện thoại thông minh - Smartphone Chính Hãng
          </h2>
          <div className="products-seo-text text-[13px] text-gray-600 leading-relaxed space-y-2">
            <p>
              Điện thoại di động là thiết bị không thể thiếu trong thời đại công nghệ số.
              Tại đây chúng tôi cung cấp các dòng smartphone chính hãng từ Apple, Samsung,
              Xiaomi, OPPO... với mức giá tốt nhất thị trường.
            </p>
            <p>
              Mua điện thoại tại cửa hàng, bạn sẽ nhận được chế độ bảo hành chính hãng 12 tháng,
              lỗi 1 đổi 1 trong 30 ngày đầu, cùng nhiều ưu đãi hấp dẫn như trả góp 0%,
              thu cũ đổi mới trợ giá cao.
            </p>
          </div>
        </section>

      </div>
    </div>
  );
}
