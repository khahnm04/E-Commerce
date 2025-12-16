"use client";
import { Order, OrderTab } from "./types";

interface OrderHistorySectionProps {
  orders: Order[];
  orderTabs: OrderTab[];
}

export const OrderHistorySection = ({ orders, orderTabs }: OrderHistorySectionProps) => {
  return (
    <div className="bg-[#fff] p-[24px] rounded-[12px] h-[572px]">
      {/* Tabs */}
      <div className="flex gap-[32px] border-b border-[#E4E4E7] mb-[24px]">
        {orderTabs.map((tab) => (
          <button
            key={tab.id}
            className={`pb-[12px] font-[500] text-[14px] ${
              tab.id === "all"
                ? "text-[#D70018] border-b-2 border-[#D70018]"
                : "text-[#71717A]"
            }`}
          >
            {tab.label}
          </button>
        ))}
      </div>

      {/* Filter */}
      <div className="flex items-center gap-[16px] mb-[24px]">
        <span className="font-[500] text-[14px] text-[#121214]">
          Lịch sử mua hàng
        </span>
        <div className="flex items-center border border-[#E4E4E7] rounded-[8px] px-[12px] py-[8px]">
          <div className="flex items-center gap-[4px]">
            <input
              type="date"
              className="text-[14px] text-[#71717A] bg-transparent outline-none"
              defaultValue="2020-12-01"
            />
          </div>
          <span className="mx-[16px] text-[#71717A]">→</span>
          <div className="flex items-center gap-[4px]">
            <input
              type="date"
              className="text-[14px] text-[#71717A] bg-transparent outline-none"
              defaultValue="2025-11-30"
            />
          </div>
        </div>
      </div>

      {/* Order List */}
      {orders.map((order, index) => (
        <div key={index} className="border border-[#E4E4E7] rounded-[8px] p-[16px] mb-[12px]">
          <div className="flex justify-between items-center mb-[16px]">
            <div className="flex items-center gap-[16px]">
              <span className="font-[500] text-[14px] text-[#121214]">
                Đơn hàng: <span className="font-[700]">{order.orderId}</span>
              </span>
              <span className="text-[#E4E4E7]">|</span>
              <span className="font-[400] text-[14px] text-[#71717A]">
                Ngày đặt hàng: {order.orderDate}
              </span>
            </div>
            <span className={`font-[500] text-[14px] ${order.statusColor}`}>
              {order.status}
            </span>
          </div>

          <div className="flex gap-[16px]">
            <img
              src={order.product.image}
              alt={order.product.name}
              className="w-[80px] h-[80px] object-cover rounded-[8px]"
            />
            <div className="flex-1">
              <h3 className="font-[500] text-[14px] text-[#121214] mb-[8px]">
                {order.product.name}
              </h3>
              <div className="flex items-center gap-[8px]">
                <span className="font-[700] text-[16px] text-[#D70018]">
                  {order.product.price}
                </span>
                <span className="font-[400] text-[12px] text-[#10B981] bg-[#ECFDF5] px-[8px] py-[2px] rounded-[4px]">
                  {order.product.vat}
                </span>
              </div>
            </div>
            <div className="flex flex-col items-end justify-between">
              <div className="text-right">
                <div className="font-[400] text-[12px] text-[#71717A] mb-[4px]">
                  Tổng thanh toán:
                </div>
                <div className="font-[700] text-[18px] text-[#D70018]">
                  {order.total}
                </div>
              </div>
              <button className="font-[500] text-[14px] text-[#D70018] flex items-center gap-[4px]">
                Xem chi tiết
                <span>› </span>
              </button>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

