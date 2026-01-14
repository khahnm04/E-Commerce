import React from 'react';
import { Check } from 'lucide-react';
import type { ProductVersion } from './types';

interface ProductVersionSelectorProps {
  versions: ProductVersion[];
  selectedVersion: string;
  onVersionChange: (version: string) => void;
}

export function ProductVersionSelector({ 
  versions, 
  selectedVersion, 
  onVersionChange 
}: ProductVersionSelectorProps) {
  return (
    <section className="product-version-section">
      <label className="product-option-label text-[13px] font-bold text-gray-800 mb-2 block">
        Phiên bản
      </label>
      <div className="product-version-grid grid grid-cols-3 gap-2">
        {versions.map((v, i) => (
          <button
            key={i}
            onClick={() => onVersionChange(v.name)}
            className={`product-version-btn relative text-xs px-2 py-3 border rounded-lg font-medium transition-all text-center ${
              selectedVersion === v.name
                ? 'border-[#d70018] text-[#d70018] shadow-[inset_0_0_0_1px_#d70018] bg-red-50'
                : 'border-gray-200 text-gray-600 hover:border-gray-400'
            }`}
          >
            {v.name}
            {selectedVersion === v.name && (
              <div className="product-version-check absolute top-0 right-0 bg-[#d70018] text-white rounded-bl-lg rounded-tr-lg w-4 h-4 flex items-center justify-center">
                <Check size={10} strokeWidth={4} />
              </div>
            )}
          </button>
        ))}
      </div>
    </section>
  );
}

