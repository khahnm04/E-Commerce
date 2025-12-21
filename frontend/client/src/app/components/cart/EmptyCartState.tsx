import React from 'react';
import Link from 'next/link';
import { ShoppingBag } from 'lucide-react';

export const EmptyCartState: React.FC = () => {
  return (
    <div className="min-h-screen bg-[#f8f9fa] py-12 flex items-center justify-center px-4">
      <div className="bg-white rounded-xl shadow-sm p-10 flex flex-col items-center text-center max-w-md w-full">
        <div className="w-28 h-28 bg-red-50 rounded-full flex items-center justify-center mb-5">
          <ShoppingBag size={56} className="text-red-300" />
        </div>
        <h2 className="text-xl font-bold text-gray-800 mb-2">Giỏ hàng trống</h2>
        <p className="text-gray-500 mb-6 text-sm">Hãy khám phá và chọn sản phẩm yêu thích!</p>
        <Link
          href="/"
          className="bg-[#d70018] text-white px-8 py-3 rounded-lg font-semibold hover:bg-[#b8000f] transition-all"
        >
          Khám phá ngay
        </Link>
      </div>
    </div>
  );
};
