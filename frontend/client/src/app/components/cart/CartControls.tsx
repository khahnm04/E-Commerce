import React from 'react';
import { Trash2 } from 'lucide-react';

interface ProductListItem {
  id: number;
  name: string;
  price: number;
  oldPrice: number;
  image: string;
  color: string;
  quantity: number;
  checked: boolean;
  gift: string | null;
}

interface CartControlsProps {
  cartItems: ProductListItem[];
  handleToggleAll: () => void;
  handleRemoveAll: () => void;
}

export const CartControls: React.FC<CartControlsProps> = ({
  cartItems,
  handleToggleAll,
  handleRemoveAll,
}) => {
  return (
    <div className="bg-white rounded-lg p-3.5 shadow-sm flex items-center justify-between">
      <div className="flex items-center gap-3">
        <input
          type="checkbox"
          className="w-[18px] h-[18px] accent-[#d70018] cursor-pointer"
          checked={cartItems.length > 0 && cartItems.every(i => i.checked)}
          onChange={handleToggleAll}
        />
        <span className="text-sm font-semibold text-gray-800">
          Chọn tất cả <span className="text-gray-500 font-normal">({cartItems.length})</span>
        </span>
      </div>
      <button
        onClick={handleRemoveAll}
        className="flex items-center gap-1.5 text-gray-500 hover:text-[#d70018] text-sm transition-colors"
      >
        <Trash2 size={16} /> Xóa
      </button>
    </div>
  );
};
