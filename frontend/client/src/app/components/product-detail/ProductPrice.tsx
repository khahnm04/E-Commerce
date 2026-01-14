import React from 'react';
import type { Product } from './types';

interface ProductPriceProps {
  product: Product;
  discountPercent?: number;
}

export function ProductPrice({ product, discountPercent = 11 }: ProductPriceProps) {
  return (
    <section className="product-price-section border border-red-100 rounded-[10px] p-4 bg-[#fffefe] shadow-sm relative">
      <span className="product-price-label text-xs text-gray-500 mb-1 block font-medium">
        Giá sản phẩm
      </span>
      <div className="product-price-wrapper flex items-end gap-3">
        <span className="product-price-current text-[28px] font-bold text-[#d70018] leading-none">
          {product.price}
        </span>
        <span className="product-price-old text-base text-gray-400 line-through font-medium">
          {product.oldPrice}
        </span>
      </div>
      <div className="product-price-discount mt-2 inline-flex items-center bg-red-100 text-[#d70018] text-[11px] font-bold px-2 py-0.5 rounded">
        Giảm {discountPercent}%
      </div>
    </section>
  );
}

