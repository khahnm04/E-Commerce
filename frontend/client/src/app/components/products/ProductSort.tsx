import React from 'react';
import type { SortOption } from './types';

interface ProductSortProps {
  totalProducts: number;
  sortOptions?: SortOption[];
  selectedSort?: string;
  onSortChange?: (sortValue: string) => void;
  categoryName?: string;
}

const defaultSortOptions: SortOption[] = [
  { label: 'Nổi bật', value: 'featured' },
  { label: 'Giá thấp đến cao', value: 'price_asc' },
  { label: 'Giá cao đến thấp', value: 'price_desc' },
];

export function ProductSort({
  totalProducts,
  sortOptions = defaultSortOptions,
  selectedSort = 'featured',
  onSortChange,
  categoryName = 'Sản phẩm'
}: ProductSortProps) {
  return (
    <div className="product-sort-bar bg-white rounded-lg shadow-sm border border-gray-100 p-4 mb-4">
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center">
            <div className="product-sort-title mb-3 sm:mb-0">
                <h2 className="text-lg font-bold text-gray-800 uppercase">{categoryName}</h2>
                <span className="text-gray-500 font-normal text-sm">
                    ({totalProducts} sản phẩm)
                </span>
            </div>
            <div className="product-sort-controls flex items-center gap-2 flex-wrap">
                <div className="text-sm font-bold text-gray-800 hidden md:block">Sắp xếp theo:</div>
                {sortOptions.map((option) => (
                    <button
                        key={option.value}
                        onClick={() => onSortChange?.(option.value)}
                        className={`px-4 py-1.5 rounded-lg text-sm font-medium transition-colors ${
                            selectedSort === option.value
                                ? 'bg-[#d70018] text-white'
                                : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
                        }`}
                    >
                        {option.label}
                    </button>
                ))}
            </div>
        </div>
    </div>
  );
}