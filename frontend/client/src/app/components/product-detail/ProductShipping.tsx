import React from 'react';
import { Truck, MapPin } from 'lucide-react';

interface ProductShippingProps {
  location?: string;
  storeCount?: number;
}

export function ProductShipping({ 
  location = 'Hồ Chí Minh, Quận 1', 
  storeCount = 48 
}: ProductShippingProps) {
  return (
    <section className="product-shipping bg-white rounded-lg border border-gray-100 p-3 shadow-sm">
      <div className="product-shipping-delivery flex items-start gap-3 mb-3 border-b border-gray-100 pb-3">
        <Truck size={20} className="text-gray-400 mt-0.5 shrink-0" />
        <div className="product-shipping-info">
          <span className="product-shipping-title text-[13px] font-bold text-gray-800 block">
            Thông tin vận chuyển
          </span>
          <div className="product-shipping-location text-xs text-gray-500 flex gap-1 mt-1 items-center flex-wrap">
            Giao đến: <span className="text-blue-600 font-medium underline cursor-pointer">{location}</span>
            <span className="bg-red-500 text-white text-[9px] px-1 rounded ml-1 font-bold">Mới</span>
          </div>
        </div>
      </div>
      <div className="product-shipping-stores flex items-start gap-3">
        <MapPin size={20} className="text-gray-400 mt-0.5 shrink-0" />
        <div className="product-shipping-stores-info w-full">
          <div className="product-shipping-stores-header flex justify-between items-center mb-2 flex-wrap gap-2">
            <span className="product-shipping-stores-title text-[13px] font-bold text-gray-800">
              Xem chi nhánh có hàng
            </span>
            <div className="product-shipping-stores-selects flex gap-2">
              <select className="text-[11px] border border-gray-200 rounded px-1 py-0.5 bg-gray-50 outline-none">
                <option>Hồ Chí Minh</option>
              </select>
              <select className="text-[11px] border border-gray-200 rounded px-1 py-0.5 bg-gray-50 outline-none">
                <option>Quận/Huyện</option>
              </select>
            </div>
          </div>
          <p className="product-shipping-stores-count text-xs text-blue-600">
            Có <span className="font-bold">{storeCount}</span> cửa hàng có sản phẩm
          </p>
        </div>
      </div>
    </section>
  );
}

