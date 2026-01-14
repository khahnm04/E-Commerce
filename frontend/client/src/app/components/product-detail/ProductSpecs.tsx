import React, { useState } from 'react';
import { ChevronRight } from 'lucide-react';
import type { Spec } from './types';

interface ProductSpecsProps {
  specs: Spec[];
  onViewDetails?: () => void;
  initialVisibleSpecs?: number; // New prop to control initial visible count
}

export function ProductSpecs({ specs, onViewDetails, initialVisibleSpecs = 5 }: ProductSpecsProps) {
  const [showAllSpecs, setShowAllSpecs] = useState(false);

  const displayedSpecs = showAllSpecs ? specs : specs.slice(0, initialVisibleSpecs);
  const hasMoreSpecs = specs.length > initialVisibleSpecs;

  return (
    <section className="product-specs bg-white rounded-lg shadow-sm p-4">
      <div className="product-specs-header flex justify-between items-center mb-3">
        <h2 className="product-section-title text-xl font-semibold text-gray-800">
          Thông số kỹ thuật
        </h2>
        {hasMoreSpecs && (
          <button
            onClick={() => setShowAllSpecs(!showAllSpecs)}
            className="text-sm font-medium text-blue-600 hover:text-blue-800 flex items-center gap-1 transition-colors duration-200"
          >
            {showAllSpecs ? 'Thu gọn' : 'Xem tất cả'} <ChevronRight size={14} />
          </button>
        )}
      </div>
      <div className="product-specs-table border border-gray-200 rounded-lg overflow-hidden mb-4">
        {displayedSpecs.map((row, i) => (
          <div
            key={i}
            className={`product-specs-row flex text-sm ${
              i % 2 === 0 ? 'bg-gray-50' : 'bg-white'
            }`}
          >
            <div className="product-specs-key w-2/5 p-3 text-gray-600 font-medium border-r border-gray-100">
              {row.k}
            </div>
            <div className="product-specs-value w-3/5 p-3 text-gray-800 whitespace-pre-line">
              {row.v}
            </div>
          </div>
        ))}
      </div>
      {onViewDetails && (
        <button
          onClick={onViewDetails}
          className="w-full mt-3 px-4 py-2 bg-white border border-gray-300 rounded-lg text-sm text-gray-700 hover:bg-gray-100 transition-colors duration-200 flex items-center justify-center gap-1 shadow-sm"
        >
          Xem cấu hình chi tiết <ChevronRight size={12} />
        </button>
      )}
    </section>
  );
}

