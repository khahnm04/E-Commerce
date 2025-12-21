import React from 'react';
import { ProductCard } from './ProductCard';
import type { ProductListItem } from './types';

interface ProductGridProps {
  products: ProductListItem[];
  columns?: 2 | 3 | 4 | 5 | 6;
  rowGap?: 1 | 2 | 3 | 4 | 5 | 6;
  colGap?: 1 | 2 | 3 | 4 | 5 | 6;
  onFavorite?: (productId: number | string) => void;
  className?: string;
}

const gridColsClass = {
  2: 'grid-cols-1 sm:grid-cols-2',
  3: 'grid-cols-1 sm:grid-cols-2 lg:grid-cols-3',
  4: 'grid-cols-1 sm:grid-cols-2 lg:grid-cols-4',
  5: 'grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5',
  6: 'grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-6',
};

export function ProductGrid({
  products,
  columns = 5,
  onFavorite,
  className = ''
}: ProductGridProps) {
  return (
    <div className={`product-grid grid ${gridColsClass[columns]} gap-[10px] ${className}`}>
      {products.map((product) => (
        <ProductCard
          key={product.id}
          product={product}
          onFavorite={onFavorite}
        />
      ))}
    </div>
  );
}
