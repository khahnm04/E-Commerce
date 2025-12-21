/* eslint-disable @next/next/no-img-element */
import React from 'react';
import { Star, Heart } from 'lucide-react';
import Link from 'next/link';
import type { ProductListItem } from './types';

interface ProductCardProps {
  product: ProductListItem;
  onFavorite?: (productId: number | string) => void;
  className?: string;
}

export function ProductCard({ product, onFavorite, className = '' }: ProductCardProps) {
  const handleFavoriteClick = (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    onFavorite?.(product.id);
  };

  const productUrl = product.slug ? `/product/${product.slug}` : '#';

  return (
    <Link href={productUrl}>
      <article className={`product-card bg-white border border-transparent rounded-xl p-3 shadow-sm hover:shadow-lg hover:border-gray-200 transition-all duration-300 group cursor-pointer flex flex-col h-full relative min-h-[450px] ${className}`}>
        {/* Discount & Tags Badge (Top Left) */}
        {(product.discount || product.tags?.length) && (
          <div className="product-card-badges absolute top-3 left-0 z-10 flex flex-col gap-1">
            {product.discount && (
              <div className="product-card-discount bg-[#d70018] text-white text-[10px] font-bold px-2 py-0.5 rounded-r-md rounded-tl-md shadow-sm">
                Giảm {product.discount}
              </div>
            )}
            {product.tags?.includes('Mới') && (
              <div className="product-card-tag bg-blue-500 text-white text-[10px] font-bold px-2 py-0.5 rounded-r-md rounded-tl-md shadow-sm">
                Mới
              </div>
            )}
            {product.tags?.includes('Hot') && (
              <div className="product-card-tag bg-orange-500 text-white text-[10px] font-bold px-2 py-0.5 rounded-r-md rounded-tl-md shadow-sm">
                Hot
              </div>
            )}
            {product.tags?.includes('Giảm sâu') && (
              <div className="product-card-tag bg-purple-500 text-white text-[10px] font-bold px-2 py-0.5 rounded-r-md rounded-tl-md shadow-sm">
                Giảm sâu
              </div>
            )}
          </div>
        )}

        {/* Installment Badge (Top Right) */}
        {product.isInstallment && (
          <div className="product-card-installment absolute top-3 right-3 bg-gray-100 text-[10px] font-medium px-2 py-1 rounded-md z-10">
            Trả góp 0%
          </div>
        )}

        {/* Product Image */}
        <div className="product-card-image-wrapper w-full h-[200px] flex items-center justify-center mb-3 mt-2 group-hover:-translate-y-1 transition-transform duration-300">
          <img 
            src={product.image} 
            alt={product.name} 
            className="product-card-image h-full w-full object-contain p-2" 
          />
        </div>

        {/* Product Name */}
        <h3 className="product-card-name text-sm font-semibold text-gray-800 line-clamp-2 mb-2 group-hover:text-[#d70018] transition-colors min-h-[42px]">
          {product.name}
        </h3>

        {/* Product Info Section */}
        <div className="product-card-info mt-auto flex flex-col gap-2">
          {/* Price */}
          <div className="product-card-price flex items-end gap-2">
            <span className="product-card-price-current text-base font-bold text-[#d70018] leading-none">
              {product.price}
            </span>
            {product.oldPrice && (
              <span className="product-card-price-old text-xs text-gray-400 line-through">
                {product.oldPrice}
              </span>
            )}
          </div>

          {/* Promo Text */}
          <div className="product-card-promo bg-gray-50 border border-gray-100 rounded-md p-2 min-h-[36px] flex items-center">
            {product.promoText && (
              <p className="product-card-promo-text text-[11px] text-gray-600 line-clamp-2 leading-tight">
                {product.promoText}
              </p>
            )}
          </div>

          {/* Footer: Rating & Favorite */}
          <div className="product-card-footer flex items-center justify-between border-t border-gray-100 pt-2">
            {product.rating && (product.reviews || product.reviewCount) ? (
              <div className="product-card-rating flex items-center gap-1 text-yellow-400">
                <Star size={12} fill="currentColor" />
                <span className="product-card-rating-value text-xs font-bold text-gray-600 ml-0.5">
                  {product.rating}
                </span>
                <span className="product-card-rating-count text-[11px] text-gray-400">
                  ({product.reviews || product.reviewCount})
                </span>
              </div>
            ) : (
              <div></div>
            )}
            <button
              onClick={handleFavoriteClick}
              className="product-card-favorite flex items-center gap-1 text-gray-400 text-xs hover:text-[#d70018] transition-colors"
              aria-label="Thêm vào yêu thích"
            >
              <span className="text-[10px] font-medium">Yêu thích</span>
              <Heart size={14} />
            </button>
          </div>
        </div>
      </article>
    </Link>
  );
}

