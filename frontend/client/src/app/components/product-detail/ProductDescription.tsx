/* eslint-disable @next/next/no-img-element */
import React, { useState } from 'react';
import { ChevronDown } from 'lucide-react';
import type { DescriptionBlock } from './types';

interface ProductDescriptionProps {
  content: DescriptionBlock[];
}

export function ProductDescription({ content }: ProductDescriptionProps) {
  const [isExpanded, setIsExpanded] = useState(false);

  return (
    <section className="product-description bg-white border border-gray-200 rounded-[10px] p-4 shadow-sm overflow-hidden relative">
      <h2 className="product-section-title text-lg font-bold text-gray-800 mb-4 uppercase">
        Đặc điểm nổi bật
      </h2>
      <div className={`product-description-content flex flex-col gap-4 text-justify transition-all duration-500 ${
        isExpanded ? '' : 'max-h-[350px] overflow-hidden'
      }`}>
        {content.map((block, idx) => {
          if (block.type === 'header') {
            return (
              <h3 key={idx} className="product-description-header text-base font-bold text-gray-900 mt-2">
                {block.content}
              </h3>
            );
          }
          if (block.type === 'image') {
            return (
              <figure key={idx} className="product-description-image flex justify-center my-2">
                <img 
                  src={block.src} 
                  alt={block.alt} 
                  className="rounded-lg max-w-full h-auto border border-gray-100" 
                />
              </figure>
            );
          }
          return (
            <p key={idx} className="product-description-text text-sm text-gray-700 leading-relaxed">
              {block.content}
            </p>
          );
        })}
      </div>
      {!isExpanded && (
        <div className="product-description-fade absolute bottom-0 left-0 w-full h-[120px] bg-gradient-to-t from-white via-white/80 to-transparent flex items-end justify-center pb-4 z-10 pointer-events-none"></div>
      )}
      <div className={`product-description-toggle flex justify-center mt-4 ${!isExpanded ? 'relative z-20' : ''}`}>
        <button 
          onClick={() => setIsExpanded(!isExpanded)} 
          className="product-description-toggle-btn w-[140px] py-2 bg-white border border-blue-500 text-blue-600 rounded-lg text-[13px] font-medium hover:bg-blue-50 transition-colors flex items-center justify-center gap-1 shadow-sm"
        >
          {isExpanded ? (
            <>
              <ChevronDown size={14} className="rotate-180" /> Thu gọn
            </>
          ) : (
            <>
              Xem thêm <ChevronDown size={14} />
            </>
          )}
        </button>
      </div>
    </section>
  );
}

