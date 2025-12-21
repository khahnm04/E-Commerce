/* eslint-disable @next/next/no-img-element */
import React from 'react';
import type { SimilarProduct } from './types';

interface ProductSimilarProps {
  products: SimilarProduct[];
  productImage: string;
}

export function ProductSimilar({ products, productImage }: ProductSimilarProps) {
  return (
    <section className="product-similar bg-white border border-gray-200 rounded-[10px] overflow-hidden shadow-sm">
      <div className="product-similar-header p-3 border-b border-gray-100 bg-gray-50">
        <h3 className="product-similar-title text-sm font-bold text-gray-800">
          Sản phẩm tương tự
        </h3>
      </div>
      <div className="product-similar-content p-3 flex flex-col gap-3">
        {products.map((item, i) => (
          <article key={i} className="product-similar-item flex items-center gap-3 border-b border-gray-50 pb-2 last:border-0 last:pb-0">
            <div className="product-similar-image w-[50px] h-[50px] bg-gray-100 rounded p-1">
              <img src={productImage} alt={item.n} className="w-full h-full object-contain" />
            </div>
            <div className="product-similar-info">
              <p className="product-similar-name text-xs font-bold text-gray-800 hover:text-blue-600 cursor-pointer transition-colors">
                {item.n}
              </p>
              <p className="product-similar-price text-xs text-[#d70018] font-bold">{item.p}</p>
            </div>
          </article>
        ))}
      </div>
    </section>
  );
}

