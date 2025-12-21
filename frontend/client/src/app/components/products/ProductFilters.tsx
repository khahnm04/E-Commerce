/* eslint-disable @next/next/no-img-element */
import React, { useState, useRef, useEffect } from 'react';
import { Filter, ChevronDown, Check, ArrowUp, ArrowDown, Star } from 'lucide-react';
import type { Brand, SortOption } from './types';

// --- PROPS ---
interface ProductFiltersProps {
  brands: Brand[];
  selectedBrand: string;
  onBrandChange: (brandId: string) => void;
  selectedPriceRanges: string[];
  onPriceRangeChange: (priceRange: string) => void;
  // Sorting props are now included
  totalProducts: number;
  sortOptions?: SortOption[];
  selectedSort?: string;
  onSortChange?: (sortValue: string) => void;
}

// --- OPTIONS ---
const priceRangeOptions = [
    { value: 'all', label: 'Tất cả' },
    { value: '0-2', label: 'Dưới 2 triệu' },
    { value: '2-5', label: 'Từ 2 - 5 triệu' },
    { value: '5-10', label: 'Từ 5 - 10 triệu' },
    { value: '10-20', label: 'Từ 10 - 20 triệu' },
    { value: '20-50', label: 'Trên 20 triệu' },
];

const defaultSortOptions: SortOption[] = [
  { label: 'Nổi bật', value: 'featured', icon: <Star size={14} /> },
  { label: 'Giá thấp đến cao', value: 'price_asc', icon: <ArrowUp size={14} /> },
  { label: 'Giá cao đến thấp', value: 'price_desc', icon: <ArrowDown size={14} /> },
];


// --- SUB-COMPONENTS ---
const FilterDropdown: React.FC<{
    title: string;
    items: { value: string; label: string; img?: string }[];
    selectedValue: string;
    onSelect: (value: string) => void;
}> = ({ title, items, selectedValue, onSelect }) => {
    const [isOpen, setIsOpen] = useState(false);
    const dropdownRef = useRef<HTMLDivElement>(null);
    const selectedLabel = items.find(item => item.value === selectedValue)?.label || 'Tất cả';

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
                setIsOpen(false);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    const handleSelect = (value: string) => {
        onSelect(value);
        setIsOpen(false);
    };

    return (
        <div className="relative" ref={dropdownRef}>
            <button
                onClick={() => setIsOpen(!isOpen)}
                className="product-filter-btn flex items-center justify-between gap-2 h-9 px-3 border border-gray-200 bg-white rounded-lg text-[13px] text-gray-600 hover:border-gray-400 transition-colors"
            >
                <span>{title}: <strong>{selectedLabel}</strong></span>
                <ChevronDown size={14} className={`transition-transform duration-200 ${isOpen ? 'rotate-180' : ''}`} />
            </button>
            {isOpen && (
                 <div className="absolute top-full left-0 mt-1 min-w-[240px] bg-white border border-gray-200 rounded-lg shadow-lg z-20 overflow-hidden animate-fade-in-down">
                    <ul>
                        {items.map((item) => (
                            <li key={item.value} onClick={() => handleSelect(item.value)} className="flex items-center justify-between px-3 py-2 text-[13px] text-gray-700 hover:bg-red-50 hover:text-[#d70018] cursor-pointer transition-colors">
                                <div className="flex items-center gap-2">
                                    {item.img && <img src={item.img} alt={item.label} className="w-5 h-5 object-contain" />}
                                    <span>{item.label}</span>
                                </div>
                                {selectedValue === item.value && <Check size={16} className="text-[#d70018]" />}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
};


// --- MAIN COMPONENT ---
export function ProductFilters({
  brands,
  selectedBrand,
  onBrandChange,
  selectedPriceRanges,
  onPriceRangeChange,
  totalProducts,
  sortOptions = defaultSortOptions,
  selectedSort = 'featured',
  onSortChange
}: ProductFiltersProps) {

  const selectedPrice = selectedPriceRanges[0] || 'all';

  return (
    <section className="product-controls-bar bg-white rounded-xl shadow-sm mb-4 border border-gray-100 p-4">
        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
            {/* Left side: Filters */}
            <div className="flex flex-wrap items-center gap-4">
                <span className="text-sm font-bold text-gray-800 flex items-center gap-2">
                    <Filter size={16} /> Lọc theo:
                </span>
                <FilterDropdown title="Giá" items={priceRangeOptions} selectedValue={selectedPrice} onSelect={onPriceRangeChange} />
                <FilterDropdown title="Thương hiệu" items={brands.map(b => ({ value: b.id, label: b.name, img: b.img }))} selectedValue={selectedBrand} onSelect={onBrandChange} />
            </div>

            {/* Right side: Sort */}
            <div className="flex items-center gap-2 flex-wrap justify-end">
                <div className="text-sm font-medium text-gray-600">Sắp xếp theo:</div>
                 {sortOptions.map((option) => (
                    <button
                        key={option.value}
                        onClick={() => onSortChange?.(option.value)}
                        className={`px-3 py-1.5 rounded-lg text-xs font-semibold transition-colors flex items-center gap-1.5 ${
                            selectedSort === option.value
                                ? 'bg-[#d70018] text-white'
                                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                        }`}
                    >
                        {option.icon}
                        {option.label}
                    </button>
                ))}
                <div className="w-[1px] h-6 bg-gray-200 mx-2"></div>
                <div className="text-sm font-semibold text-gray-800">
                    {totalProducts} <span className="font-normal text-gray-600">sản phẩm</span>
                </div>
            </div>
        </div>
    </section>
  );
}