/* eslint-disable @next/next/no-img-element */
import React from 'react';
import { Minus, Plus, Trash2 } from 'lucide-react';
import { GiftIcon } from './GiftIcon'; // Assuming GiftIcon is in the same directory

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

interface CartItemProps {
  item: ProductListItem;
  handleQuantityChange: (id: number, delta: number) => void;
  handleRemoveItem: (id: number) => void;
  handleToggleCheck: (id: number) => void;
  formatCurrency: (amount: number) => string;
}

export const CartItem: React.FC<CartItemProps> = ({
  item,
  handleQuantityChange,
  handleRemoveItem,
  handleToggleCheck,
  formatCurrency,
}) => {
  return (
    <div
      key={item.id}
      className="p-4 flex gap-4 hover:bg-gray-50/50 transition-colors"
    >
      {/* Checkbox */}
      <div className="flex items-start pt-1">
        <input
          type="checkbox"
          className="w-[18px] h-[18px] accent-[#d70018] cursor-pointer"
          checked={item.checked}
          onChange={() => handleToggleCheck(item.id)}
        />
      </div>

      {/* Image */}
      <div className="w-24 h-24 border border-gray-200 rounded-lg p-1.5 bg-white shrink-0">
        <img
          src={item.image}
          alt={item.name}
          className="w-full h-full object-contain"
        />
      </div>

      {/* Content */}
      <div className="flex-1 flex flex-col justify-between min-w-0">
        <div>
          <div className="flex justify-between items-start gap-3 mb-2">
            <div className="flex-1 min-w-0">
              <h3 className="text-[15px] font-semibold text-gray-800 line-clamp-2 leading-snug mb-1.5 hover:text-[#d70018] transition-colors cursor-pointer">
                {item.name}
              </h3>
              <span className="inline-block bg-gray-100 px-2.5 py-0.5 rounded text-xs text-gray-600">
                {item.color}
              </span>
            </div>

            {/* Price */}
            <div className="text-right shrink-0">
              <div className="text-base font-bold text-[#d70018]">
                {formatCurrency(item.price)}
              </div>
              <div className="text-xs text-gray-400 line-through">
                {formatCurrency(item.oldPrice)}
              </div>
            </div>
          </div>

          {/* Gift */}
          {item.gift && (
            <div className="flex items-center gap-1.5 text-xs text-green-700 bg-green-50 px-2.5 py-1 rounded w-fit border border-green-200/50 mt-2">
              <GiftIcon size={12} /> {item.gift}
            </div>
          )}
        </div>

        {/* Actions */}
        <div className="flex justify-between items-center mt-3 pt-3 border-t border-gray-100">
          {/* Quantity */}
          <div className="flex items-center border border-gray-300 rounded-lg h-9 bg-white">
            <button
              onClick={() => handleQuantityChange(item.id, -1)}
              className="w-9 h-full flex items-center justify-center text-gray-600 hover:bg-gray-100 rounded-l-lg disabled:opacity-40 transition-colors"
              disabled={item.quantity <= 1}
            >
              <Minus size={14} strokeWidth={2.5} />
            </button>
            <input
              type="text"
              value={item.quantity}
              readOnly
              className="w-10 h-full text-center text-sm font-semibold text-gray-800 border-x border-gray-300 outline-none"
            />
            <button
              onClick={() => handleQuantityChange(item.id, 1)}
              className="w-9 h-full flex items-center justify-center text-gray-600 hover:bg-gray-100 rounded-r-lg transition-colors"
            >
              <Plus size={14} strokeWidth={2.5} />
            </button>
          </div>

          {/* Remove */}
          <button
            onClick={() => handleRemoveItem(item.id)}
            className="flex items-center gap-1.5 text-gray-500 hover:text-[#d70018] text-sm transition-colors"
          >
            <Trash2 size={16} /> Xóa
          </button>
        </div>
      </div>
    </div>
  );
};
