import React from 'react';
import { Star } from 'lucide-react';
import type { Product } from './types';

interface ProductHeaderProps {
  product: Product;
  breadcrumbs?: Array<{ label: string; href?: string }>;
}

export function ProductHeader({ product, breadcrumbs }: ProductHeaderProps) {
  const defaultBreadcrumbs = [
    { label: 'Trang chủ', href: '#' },
    { label: 'Đồng hồ thông minh', href: '#' },
    { label: product.name, href: '#' }
  ];

  const items = breadcrumbs || defaultBreadcrumbs;

  return (
    <header className="product-detail-header border-b border-gray-100 mb-[5px]">
      <nav className="product-breadcrumb flex items-center gap-2 text-xs text-gray-500 mb-2" aria-label="Breadcrumb">
        {items.map((item, index) => (
          <React.Fragment key={index}>
            {index > 0 && <span>/</span>}
            <a
              href={item.href || '#'}
              className="product-breadcrumb-link hover:text-red-600 transition-colors"
            >
              {item.label}
            </a>
          </React.Fragment>
        ))}
      </nav>
      <div className="product-title-wrapper flex flex-col gap-0">
        <h1 className="product-title text-xl font-bold text-gray-900">{product.name}</h1>
        <div className="product-rating-badge flex items-center gap-1 text-sm text-orange-400 mt-1">
          <Star size={12} fill="currentColor" />
          <span className="product-rating-value font-bold text-gray-800">{product.rating}</span>
          <span className="product-rating-count text-gray-500 underline decoration-gray-400 cursor-pointer">
            ({product.reviews} đánh giá)
          </span>
        </div>
      </div>
    </header>
  );
}

