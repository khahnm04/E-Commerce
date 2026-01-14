"use client";
/* eslint-disable @next/next/no-img-element */
import React, { useState, useEffect } from 'react';
import { Play } from 'lucide-react';
import type { Product, Thumbnail } from './types';

interface ProductGalleryProps {
  product: Product;
}

export function ProductGallery({ product }: ProductGalleryProps) {
  const [activeThumb, setActiveThumb] = useState<Thumbnail | null>(null);
  const [selectedImage, setSelectedImage] = useState(product.image);

  useEffect(() => {
    if (activeThumb && activeThumb.type === 'img') {
      setSelectedImage(activeThumb.src!);
    } else if (!activeThumb) {
      setSelectedImage(product.image);
    }
  }, [activeThumb, product.image]);

  const handleThumbClick = (thumb: Thumbnail) => {
    setActiveThumb(thumb);
  };

  const isVideo = activeThumb?.type === 'video';

  return (
    <article className="product-gallery border border-gray-200 rounded-[10px] p-4">
      <div className="flex flex-col gap-4">
        {/* Main Image */}
        <div
          className="rounded-lg mb-4 h-[400px]"
        >
          <img
            src={selectedImage}
            alt={product.name}
            className="w-full h-full object-cover rounded-lg"
          />

          {isVideo && (
            <button
              className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-red-600 w-14 h-10 rounded-[10px] flex items-center justify-center shadow-lg z-20 cursor-pointer hover:bg-red-700 transition-colors"
              aria-label="Play video"
            >
              <Play size={20} fill="white" className="text-white ml-1" />
            </button>
          )}
        </div>

        {/* Thumbnails */}
        <div className="flex flex-row gap-2 justify-center">
          {product.thumbnails.map((item, idx) => (
            <button
              key={idx}
              onClick={() => handleThumbClick(item)}
              className={`w-16 h-16 border rounded-lg flex flex-col items-center justify-center cursor-pointer text-xs text-center p-1 transition-colors ${activeThumb === item
                  ? 'border-red-500 bg-red-50'
                  : 'border-gray-200 hover:border-gray-400'
                }`}
              aria-label={item.type === 'img' ? `View image ${idx + 1}` : item.label}
            >
              {item.type === 'img' ? (
                <img src={item.src} alt="" className="w-full h-full object-contain" />
              ) : (
                <div className="flex flex-col items-center justify-center h-full gap-1 text-gray-500">
                  {item.icon}
                  <span className="leading-none">{item.label}</span>
                </div>
              )}
            </button>
          ))}
        </div>
      </div>
    </article>
  );
}
