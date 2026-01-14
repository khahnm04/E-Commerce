import React from 'react';
import type { Commitment } from './types';

interface ProductCommitmentsProps {
  commitments: Commitment[];
}

export function ProductCommitments({ commitments }: ProductCommitmentsProps) {
  return (
    <section className="product-commitments">
      <h2 className="product-section-title text-lg font-bold text-gray-800 mb-3">
        Cam kết sản phẩm
      </h2>
      <div className="product-commitments-grid grid grid-cols-1 sm:grid-cols-2 gap-3">
        {commitments.map((item, idx) => (
          <article 
            key={idx} 
            className="product-commitment-card bg-white border border-gray-100 rounded-[10px] p-4 shadow-sm hover:shadow-md transition-shadow"
          >
            <div className="product-commitment-icon mb-2">
              <div className="bg-red-50 w-fit p-1.5 rounded-md">{item.icon}</div>
            </div>
            <div className="product-commitment-desc text-[13px] text-gray-700 leading-relaxed">
              {item.desc}
            </div>
          </article>
        ))}
      </div>
    </section>
  );
}

