/* eslint-disable @next/next/no-img-element */
import React from 'react';
import { Check } from 'lucide-react';
import type { ProductColor } from './types';

interface ProductColorSelectorProps {
  colors: ProductColor[];
  selectedColor: string;
  productImage: string;
  onColorChange: (color: string) => void;
}

export function ProductColorSelector({ 
  colors, 
  selectedColor, 
  productImage,
  onColorChange 
}: ProductColorSelectorProps) {
  return (
    <section className="product-color-section">
      <label className="product-option-label text-[13px] font-bold text-gray-800 mb-2 block">
        Màu sắc
      </label>
      <div className="product-color-grid grid grid-cols-3 gap-2">
        {colors.map((c, i) => (
          <button
            key={i}
            onClick={() => onColorChange(c.name)}
            className={`product-color-btn relative flex items-center gap-2 p-2 border rounded-lg cursor-pointer transition-all text-left ${
              selectedColor === c.name
                ? 'border-[#d70018] shadow-[inset_0_0_0_1px_#d70018] bg-red-50'
                : 'border-gray-200 hover:border-gray-400'
            }`}
          >
            <div className="product-color-preview w-9 h-9 bg-gray-100 rounded p-0.5 shrink-0">
              <img 
                src={c.image || productImage} 
                alt={c.name} 
                className="w-full h-full object-contain" 
              />
            </div>
            <div className="product-color-info flex flex-col">
              <span className="product-color-name text-xs font-bold text-gray-800">{c.name}</span>
              <span className="product-color-price text-[11px] text-gray-500">{c.price}</span>
            </div>
            {selectedColor === c.name && (
              <div className="product-color-check absolute top-0 right-0 bg-[#d70018] text-white rounded-bl-lg rounded-tr-lg w-4 h-4 flex items-center justify-center">
                <Check size={10} strokeWidth={4} />
              </div>
            )}
          </button>
        ))}
      </div>
    </section>
  );
}

