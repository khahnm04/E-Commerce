import React from 'react';
import { Gift } from 'lucide-react';

interface Promotion {
  number: number;
  content: React.ReactNode;
}

interface ProductPromotionsProps {
  promotions?: Promotion[];
}

const defaultPromotions: Promotion[] = [
  {
    number: 1,
    content: <>Tặng gói trải nghiệm 3 tháng Huawei Health <a href="#" className="text-blue-600 hover:underline">Xem chi tiết</a></>
  },
  {
    number: 2,
    content: <>Danh sách cửa hàng trải nghiệm sản phẩm Huawei <a href="#" className="text-blue-600 hover:underline">Xem chi tiết</a></>
  }
];

export function ProductPromotions({ promotions = defaultPromotions }: ProductPromotionsProps) {
  return (
    <section className="product-promotions border border-blue-400 rounded-[10px] overflow-hidden">
      <div className="product-promotions-header bg-blue-50 px-3 py-2 border-b border-blue-100 flex items-center gap-2">
        <Gift size={16} className="text-[#d70018]" />
        <span className="product-promotions-title text-sm font-bold text-gray-800">
          Khuyến mãi hấp dẫn
        </span>
      </div>
      <div className="product-promotions-content p-3 bg-white">
        <ul className="product-promotions-list flex flex-col gap-2">
          {promotions.map((promo, idx) => (
            <li key={idx} className="product-promotion-item flex gap-2 items-start text-xs">
              <span className="product-promotion-number bg-blue-500 text-white rounded-full w-4 h-4 flex items-center justify-center text-[10px] shrink-0 mt-0.5">
                {promo.number}
              </span>
              <span>{promo.content}</span>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}

