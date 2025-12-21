/* eslint-disable @next/next/no-img-element */
"use client"
import React, { useState, useEffect } from 'react';
import {
  Minus, Plus, Trash2, ChevronLeft, Ticket,
  ArrowRight, ShoppingBag, ShieldCheck
} from 'lucide-react';
import { CartHeader } from '@/app/components/cart/CartHeader';
import { CartControls } from '@/app/components/cart/CartControls';
import { CartItem } from '@/app/components/cart/CartItem';
import { CartSummary } from '@/app/components/cart/CartSummary';
import { EmptyCartState } from '@/app/components/cart/EmptyCartState';

// --- MOCK DATA GIỎ HÀNG ---
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

const INITIAL_CART_ITEMS: ProductListItem[] = [
  {
    id: 1,
    name: "iPhone 15 Pro Max 256GB | Chính hãng VN/A",
    price: 29990000,
    oldPrice: 34990000,
    image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone_air-3_2.jpg",
    color: "Titan Tự Nhiên",
    quantity: 1,
    checked: true,
    gift: "Tặng gói bảo hành VIP 12 tháng"
  },
  {
    id: 2,
    name: "Tai nghe Bluetooth AirPods Pro 2 MagSafe",
    price: 5990000,
    oldPrice: 6990000,
    image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone_air-3_2.jpg",
    color: "Trắng",
    quantity: 2,
    checked: false,
    gift: null
  },
  {
    id: 3,
    name: "Samsung Galaxy S24 Ultra 256GB",
    price: 27990000,
    oldPrice: 31990000,
    image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone_air-3_2.jpg",
    color: "Titan Xám",
    quantity: 1,
    checked: true,
    gift: "Tặng ốp lưng chính hãng"
  }
];

const formatCurrency = (amount: number) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
};

export default function CartPage() {
  const [cartItems, setCartItems] = useState<ProductListItem[]>(INITIAL_CART_ITEMS);
  const [isClient, setIsClient] = useState(false);

  useEffect(() => {
    setIsClient(true);
  }, []);

  const handleQuantityChange = (id: number, delta: number) => {
    setCartItems(items => items.map(item => {
      if (item.id === id) {
        const newQuantity = item.quantity + delta;
        return { ...item, quantity: newQuantity > 0 ? newQuantity : 1 };
      }
      return item;
    }));
  };

  const handleRemoveItem = (id: number) => {
    if (confirm("Bạn có chắc muốn xóa sản phẩm này?")) {
      setCartItems(items => items.filter(item => item.id !== id));
    }
  };

  const handleToggleCheck = (id: number) => {
    setCartItems(items => items.map(item =>
      item.id === id ? { ...item, checked: !item.checked } : item
    ));
  };

  const handleToggleAll = () => {
    const areAllChecked = cartItems.every(item => item.checked);
    setCartItems(items => items.map(item => ({ ...item, checked: !areAllChecked })));
  };

  const handleRemoveAll = () => {
    if (confirm("Bạn có chắc muốn xóa tất cả sản phẩm?")) {
      setCartItems([]);
    }
  };

  const selectedItems = cartItems.filter(item => item.checked);
  const totalProvisional = selectedItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  const totalDiscount = selectedItems.reduce((sum, item) => sum + (item.oldPrice - item.price) * item.quantity, 0);
  const totalPayment = totalProvisional; // For now, totalPayment is same as totalProvisional

  if (!isClient) return null;

  // --- EMPTY STATE ---
  if (cartItems.length === 0) {
    return <EmptyCartState />;
  }

  // --- MAIN CART ---
  return (
    <div className="min-h-screen bg-[#f8f9fa] py-6 font-sans text-gray-800">
      <div className="max-w-[1232px] mx-auto px-4">

        {/* HEADER */}
        <CartHeader cartItemCount={cartItems.length} />

        {/* MAIN GRID */}
        <div className="grid lg:grid-cols-[1fr_380px] gap-4">

          {/* LEFT: PRODUCT LIST */}
          <div className="space-y-3">

            {/* Select All Header */}
            <CartControls
              cartItems={cartItems}
              handleToggleAll={handleToggleAll}
              handleRemoveAll={handleRemoveAll}
            />

            {/* Product Items */}
            <div className="bg-white rounded-lg shadow-sm divide-y divide-gray-100">
              {cartItems.map((item) => (
                <CartItem
                  key={item.id}
                  item={item}
                  handleQuantityChange={handleQuantityChange}
                  handleRemoveItem={handleRemoveItem}
                  handleToggleCheck={handleToggleCheck}
                  formatCurrency={formatCurrency}
                />
              ))}
            </div>

            {/* Policy Note */}
            <div className="flex items-start gap-2.5 bg-yellow-50 p-3.5 rounded-lg border border-yellow-200/60 text-sm text-gray-600">
              <ShieldCheck size={18} className="text-yellow-600 shrink-0 mt-0.5" />
              <p className="leading-relaxed text-xs">
                Bằng việc Mua hàng, bạn đồng ý với
                <a href="#" className="text-blue-600 hover:underline mx-1">Điều khoản</a>
                và
                <a href="#" className="text-blue-600 hover:underline mx-1">Chính sách</a>
                của CellphoneS.
              </p>
            </div>
          </div>

          {/* RIGHT: CHECKOUT SIDEBAR */}
          <CartSummary
            selectedItems={selectedItems}
            totalProvisional={totalProvisional}
            totalDiscount={totalDiscount}
            totalPayment={totalPayment}
            formatCurrency={formatCurrency}
          />

        </div>
      </div>
    </div>
  );
}
