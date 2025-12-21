import React from 'react';
import Link from 'next/link';
import { Ticket, ArrowRight, ChevronRight } from 'lucide-react';

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

interface CartSummaryProps {
    selectedItems: ProductListItem[];
    totalProvisional: number;
    totalDiscount: number;
    totalPayment: number;
    formatCurrency: (amount: number) => string;
}

export const CartSummary: React.FC<CartSummaryProps> = ({
    selectedItems,
    totalProvisional,
    totalDiscount,
    totalPayment,
    formatCurrency,
}) => {
    return (
        <div className="space-y-3">
            {/* Address Info */}
            <div className="bg-white rounded-lg p-4 shadow-sm">
                <div className="flex justify-between items-center mb-3">
                    <h3 className="text-sm font-semibold text-gray-800 flex items-center gap-1.5">
                        <svg className="w-4 h-4 text-[#d70018]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                        </svg>
                        Giao tới
                    </h3>
                    <a href="#" className="text-xs text-blue-600 font-semibold hover:underline">
                        Thay đổi
                    </a>
                </div>
                <div className="space-y-1.5">
                    <div className="flex items-center gap-2 text-sm">
                        <span className="font-semibold text-gray-800">Nguyễn Văn A</span>
                        <span className="text-gray-400">•</span>
                        <span className="text-gray-600">0987.654.321</span>
                    </div>
                    <p className="text-sm text-gray-500">
                        123 Đường ABC, Phường XYZ, Quận 1, TP.HCM
                    </p>
                </div>
            </div>

            {/* Voucher */}
            <div className="bg-white rounded-lg p-4 shadow-sm">
                <h3 className="text-sm font-semibold text-gray-800 mb-3 flex items-center gap-1.5">
                    <Ticket size={18} className="text-[#d70018]" />
                    Khuyến mãi
                </h3>
                <div className="flex gap-2 mb-3">
                    <input
                        type="text"
                        placeholder="Nhập mã giảm giá"
                        className="flex-1 border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:border-[#d70018] transition-colors"
                    />
                    <button className="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg text-sm font-semibold hover:bg-[#d70018] hover:text-white transition-colors whitespace-nowrap">
                        Áp dụng
                    </button>
                </div>
                {/* Voucher Item */}
                <div className="border border-blue-200 bg-blue-50 rounded-lg p-2.5 flex justify-between items-center cursor-pointer hover:bg-blue-100 transition-colors">
                    <div>
                        <p className="text-sm font-bold text-blue-700">GIAM50K</p>
                        <p className="text-xs text-gray-600">Giảm 50k cho đơn từ 2tr</p>
                    </div>
                    <span className="text-xs text-blue-600 font-semibold px-2.5 py-1 bg-white rounded-md">
                        Áp dụng
                    </span>
                </div>
            </div>

            {/* Order Summary */}
            <div className="bg-white rounded-lg p-4 shadow-sm sticky top-4">
                <div className="space-y-2.5 mb-4 pb-4 border-b border-gray-100">
                    <div className="flex justify-between items-center text-sm">
                        <span className="text-gray-600">Tạm tính</span>
                        <span className="font-semibold text-gray-800">{formatCurrency(totalProvisional)}</span>
                    </div>
                    <div className="flex justify-between items-center text-sm">
                        <span className="text-gray-600">Giảm giá</span>
                        <span className="font-semibold text-green-600">-{formatCurrency(totalDiscount)}</span>
                    </div>
                </div>

                <div className="flex justify-between items-end mb-4">
                    <span className="text-sm font-semibold text-gray-800">Tổng thanh toán</span>
                    <div className="text-right">
                        <span className="text-xl font-bold text-[#d70018]">
                            {formatCurrency(totalPayment)}
                        </span>
                        <p className="text-xs text-gray-400 mt-0.5">
                            Tiết kiệm {formatCurrency(totalDiscount)}
                        </p>
                    </div>
                </div>

                <button
                    className={`w-full py-3.5 rounded-lg text-white font-semibold text-sm uppercase transition-all flex items-center justify-center gap-2
                    ${selectedItems.length > 0
                            ? 'bg-[#d70018] hover:bg-[#b8000f] shadow-md hover:shadow-lg'
                            : 'bg-gray-300 cursor-not-allowed'}`}
                    disabled={selectedItems.length === 0}
                >
                    Mua hàng ({selectedItems.length}) <ArrowRight size={18} />
                </button>

                <p className="text-xs text-gray-400 text-center mt-3 leading-relaxed">
                    Nhấn Mua hàng đồng nghĩa bạn đồng ý với <a href="#" className="text-blue-600 hover:underline">Điều khoản</a> của chúng tôi.
                </p>
            </div>
        </div>
    );
};
