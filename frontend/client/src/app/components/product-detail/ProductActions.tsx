import React from 'react';
import { ShoppingCart } from 'lucide-react';

interface ProductActionsProps {
  onBuyNow?: () => void;
  onAddToCart?: () => void;
  onInstallment?: () => void;
}

export function ProductActions({ 
  onBuyNow, 
  onAddToCart, 
  onInstallment 
}: ProductActionsProps) {
  return (
    <section className="product-actions flex gap-2">
      <button 
        onClick={onInstallment}
        className="product-action-installment flex-none w-20 border border-blue-600 rounded-lg flex flex-col items-center justify-center text-blue-600 hover:bg-blue-50 transition-colors py-2"
      >
        <span className="text-[11px] font-bold text-center leading-tight">
          Trả góp<br />0%
        </span>
      </button>
      <button 
        onClick={onBuyNow}
        className="product-action-buy flex-1 bg-[#d70018] hover:bg-red-700 text-white rounded-lg flex flex-col items-center justify-center py-2 shadow-lg transition-all active:scale-[0.99]"
      >
        <span className="product-action-buy-text text-base font-bold uppercase">MUA NGAY</span>
        <span className="product-action-buy-subtext text-[11px] font-normal">
          (Giao nhanh từ 2 giờ hoặc nhận tại cửa hàng)
        </span>
      </button>
      <button 
        onClick={onAddToCart}
        className="product-action-cart flex-none w-[100px] border border-[#d70018] rounded-lg flex flex-col items-center justify-center text-[#d70018] hover:bg-red-50 transition-colors py-2"
      >
        <ShoppingCart size={20} className="mb-0.5" />
        <span className="text-[9px] font-bold uppercase">Thêm vào giỏ</span>
      </button>
    </section>
  );
}

