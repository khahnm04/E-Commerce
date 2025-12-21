/* eslint-disable @next/next/no-img-element */
import React from 'react';
import type { RelatedNews } from './types';

interface ProductRelatedNewsProps {
  news: RelatedNews[];
}

export function ProductRelatedNews({ news }: ProductRelatedNewsProps) {
  return (
    <section className="product-related-news bg-white border border-gray-200 rounded-[10px] overflow-hidden shadow-sm">
      <div className="product-related-news-header p-3 border-b border-gray-100 bg-gray-50">
        <h3 className="product-related-news-title text-sm font-bold text-gray-800">
          Tin tức về sản phẩm
        </h3>
      </div>
      <div className="product-related-news-content p-3 flex flex-col gap-3">
        {news.map((item, idx) => (
          <article key={idx} className="product-related-news-item flex gap-3 cursor-pointer group">
            <div className="product-related-news-image w-[100px] h-[60px] shrink-0 rounded-md overflow-hidden border border-gray-100">
              <img 
                src={item.img} 
                alt={item.title} 
                className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300" 
              />
            </div>
            <h4 className="product-related-news-title text-xs font-medium text-gray-800 line-clamp-2 group-hover:text-blue-600 transition-colors">
              {item.title}
            </h4>
          </article>
        ))}
        <a href="#" className="product-related-news-link text-xs text-blue-600 text-center mt-1 hover:underline">
          Xem tất cả tin tức &gt;
        </a>
      </div>
    </section>
  );
}

