import React from 'react';
import Link from 'next/link';
import { ChevronLeft } from 'lucide-react';

interface CartHeaderProps {
  cartItemCount: number;
}

export const CartHeader: React.FC<CartHeaderProps> = ({ cartItemCount }) => {
  return (
    <div className="flex items-center gap-3 mb-5">
      <Link
        href="/"
        className="flex items-center gap-1 text-gray-600 hover:text-[#d70018] transition-colors text-sm font-medium"
      >
        <ChevronLeft size={18} /> Tiếp tục mua sắm
      </Link>
      <span className="text-gray-300">|</span>
      <h1 className="text-lg font-bold text-gray-800">Giỏ hàng ({cartItemCount})</h1>
    </div>
  );
};
