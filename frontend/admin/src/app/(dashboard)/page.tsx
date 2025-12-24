/* eslint-disable @next/next/no-img-element */
"use client";

import { useEffect, useRef, useState, memo } from "react";
import Chart from "chart.js/auto";
import type { ChartConfiguration } from "chart.js";

// ============================================================================
// INTERFACES - Detailed Type Safety (từ Document 1)
// ============================================================================
interface DashboardStat {
  image: string;
  name: string;
  number: string;
}

interface TourPricing {
  adults: { count: number; price: string };
  children: { count: number; price: string };
  infants: { count: number; price: string };
}

interface Tour {
  image: string;
  name: string;
  pricing: TourPricing;
}

interface Order {
  id: string;
  code: string;
  customer: {
    name: string;
    phone: string;
    note: string;
  };
  tours: Tour[];
  payment: {
    total: string;
    discount: string;
    discountCode: string;
    final: string;
    method: string;
    status: string;
  };
  status: string;
  createdAt: {
    time: string;
    date: string;
  };
}

// ============================================================================
// EXTRACTED COMPONENTS - Component Reusability (từ Document 1)
// ============================================================================

const StatCard = memo(({ item }: { item: DashboardStat }) => (
  <div className="bg-white rounded-[14px] p-[28px] flex items-center justify-center gap-[20px] shadow-sm hover:shadow-md transition-shadow">
    <div className="w-[60px] flex-shrink-0">
      <img src={item.image} alt={item.name} className="w-full h-auto" />
    </div>
    <div>
      <div className="font-[600] text-[16px] text-[var(--color-text)] mb-[4px]">
        {item.name}
      </div>
      <div className="font-[700] text-[28px] text-[var(--color-text)]">
        {item.number}
      </div>
    </div>
  </div>
));
StatCard.displayName = "StatCard";

const TourCard = memo(({ tour }: { tour: Tour }) => (
  <div className="flex items-start gap-[12px]">
    <div className="w-[76px] h-[76px] rounded-[8px] overflow-hidden flex-shrink-0 border border-gray-100">
      <img
        src={tour.image}
        alt={tour.name}
        className="w-full h-full object-cover"
      />
    </div>
    <div>
      <div className="font-[600] text-[14px] text-[var(--color-text)] mb-[4px]">
        {tour.name}
      </div>
      <div className="flex flex-col gap-[2px] font-[500] text-[12px] text-gray-500">
        <div>Người lớn: {tour.pricing.adults.count} x {tour.pricing.adults.price}</div>
        <div>Trẻ em: {tour.pricing.children.count} x {tour.pricing.children.price}</div>
        <div>Em bé: {tour.pricing.infants.count} x {tour.pricing.infants.price}</div>
      </div>
    </div>
  </div>
));
TourCard.displayName = "TourCard";

const OrderRow = memo(({ order }: { order: Order }) => (
  <tr className="hover:bg-gray-50 transition-colors">
    {/* Mã đơn hàng */}
    <td className="px-[24px] py-[16px] border-b border-[#F1F4F9] align-top">
      <div className="font-[700] text-[14px] text-[var(--color-primary)]">
        {order.code}
      </div>
    </td>

    {/* Thông tin khách */}
    <td className="px-[24px] py-[16px] border-b border-[#F1F4F9] align-top text-[14px] text-[var(--color-text)]">
      <div className="font-semibold mb-1">Họ tên: {order.customer.name}</div>
      <div className="text-gray-500 mb-1">SĐT: {order.customer.phone}</div>
      <div className="text-gray-500 italic">Ghi chú: {order.customer.note}</div>
    </td>

    {/* Danh sách tour */}
    <td className="px-[24px] py-[16px] border-b border-[#F1F4F9] align-top">
      <div className="flex flex-col gap-[15px]">
        {order.tours.map((tour, idx) => (
          <TourCard key={idx} tour={tour} />
        ))}
      </div>
    </td>

    {/* Thanh toán */}
    <td className="px-[24px] py-[16px] border-b border-[#F1F4F9] align-top text-[14px] text-[var(--color-text)]">
      <div className="flex flex-col gap-1">
        <div className="flex justify-between w-full max-w-[220px]">
          <span>Tổng tiền:</span>
          <span className="font-semibold">{order.payment.total}</span>
        </div>
        <div className="flex justify-between w-full max-w-[220px] text-green-600">
          <span>Giảm:</span>
          <span>-{order.payment.discount}</span>
        </div>
        <div className="text-xs text-gray-400">Mã: {order.payment.discountCode}</div>
        <div className="flex justify-between w-full max-w-[220px] border-t border-dashed pt-2 mt-1">
          <span>Thanh toán:</span>
          <span className="font-bold text-[var(--color-primary)]">{order.payment.final}</span>
        </div>
        <div className="text-xs text-gray-500 mt-1">
          {order.payment.method} • <span className="text-green-600 font-medium">{order.payment.status}</span>
        </div>
      </div>
    </td>

    {/* Trạng thái */}
    <td className="px-[24px] py-[16px] border-b border-[#F1F4F9] align-top">
      <span className="inline-block px-3 py-1 bg-orange-100 text-orange-600 rounded-[6px] text-[12px] font-bold border border-orange-200">
        {order.status}
      </span>
    </td>

    {/* Ngày đặt */}
    <td className="px-[24px] py-[16px] border-b border-[#F1F4F9] align-top text-right text-[14px] text-[var(--color-text)]">
      <div className="font-semibold">{order.createdAt.time}</div>
      <div className="text-gray-500">{order.createdAt.date}</div>
    </td>
  </tr>
));
OrderRow.displayName = "OrderRow";

// ============================================================================
// CONSTANTS & HELPERS
// ============================================================================

const TABLE_HEADERS = [
  "Mã",
  "Thông tin khách",
  "Danh sách tour",
  "Thanh toán",
  "Trạng thái",
  "Ngày đặt",
];

const getHeaderClass = (index: number, total: number): string => {
  const baseClass =
    "bg-[#F1F4F9] px-[24px] py-[14px] font-bold text-[14px] text-[var(--color-text)] whitespace-nowrap";
  const firstClass = index === 0 ? "rounded-l-[12px] text-left" : "text-left";
  const lastClass = index === total - 1 ? "rounded-r-[12px] text-right" : "";
  return `${baseClass} ${firstClass} ${lastClass}`;
};

// Chart Configuration (từ Document 1)
const createChartConfig = (): ChartConfiguration<"line"> => ({
  type: "line",
  data: {
    labels: ["01", "02", "03", "04", "05"],
    datasets: [
      {
        label: "Tháng 05/2025",
        data: [1000000, 2000000, 3000000, 4000000, 5000000],
        borderColor: "#36A1EA",
        borderWidth: 1.5,
        tension: 0.3, // Curved lines (từ Document 2)
      },
      {
        label: "Tháng 04/2025",
        data: [1000000, 2000000, 2000000, 1000000, 6000000],
        borderColor: "#FE6383",
        borderWidth: 1.5,
        tension: 0.3,
      },
    ],
  },
  options: {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: "bottom",
      },
    },
    scales: {
      x: {
        display: true,
        title: {
          display: true,
          text: "Ngày",
        },
      },
      y: {
        display: true,
        title: {
          display: true,
          text: "Doanh thu (VNĐ)",
        },
      },
    },
  },
});

// ============================================================================
// MAIN COMPONENT
// ============================================================================

export default function HomePage() {
  // State Management (từ Document 2)
  const [stats] = useState<DashboardStat[]>([
    {
      image: "assets/images/section-1-icon-1.svg",
      name: "Người dùng",
      number: "1.200",
    },
    {
      image: "assets/images/section-1-icon-2.svg",
      name: "Đơn hàng",
      number: "3.600",
    },
    {
      image: "assets/images/section-1-icon-3.svg",
      name: "Doanh thu",
      number: "300.000.000đ",
    },
  ]);

  const [orders] = useState<Order[]>([
    {
      id: "1",
      code: "OD000001",
      customer: {
        name: "Lê Văn A",
        phone: "0123456789",
        note: "Test...",
      },
      tours: [
        {
          image: "assets/images/tour-1.jpg",
          name: "Tour Hạ Long",
          pricing: {
            adults: { count: 3, price: "1.500.000đ" },
            children: { count: 2, price: "1.300.000đ" },
            infants: { count: 2, price: "1.000.000đ" },
          },
        },
        {
          image: "assets/images/tour-1.jpg",
          name: "Tour Hạ Long",
          pricing: {
            adults: { count: 3, price: "1.500.000đ" },
            children: { count: 2, price: "1.300.000đ" },
            infants: { count: 2, price: "1.000.000đ" },
          },
        },
      ],
      payment: {
        total: "10.000.000đ",
        discount: "400.000đ",
        discountCode: "TOURMUAHE2024",
        final: "9.600.000đ",
        method: "Ví Momo",
        status: "Đã thanh toán",
      },
      status: "Khởi tạo",
      createdAt: {
        time: "16:20",
        date: "01/01/2024",
      },
    },
  ]);

  // Chart Refs - Best Practice from Document 2
  const chartRef = useRef<HTMLCanvasElement>(null);
  const chartInstance = useRef<Chart | null>(null);

  // Chart Effect - Optimal Implementation (từ Document 2)
  useEffect(() => {
    if (chartRef.current) {
      // Destroy existing chart
      if (chartInstance.current) {
        chartInstance.current.destroy();
      }

      const ctx = chartRef.current.getContext("2d");
      if (ctx) {
        chartInstance.current = new Chart(ctx, createChartConfig());
      }
    }

    // Cleanup
    return () => {
      if (chartInstance.current) {
        chartInstance.current.destroy();
        chartInstance.current = null;
      }
    };
  }, []);

  return (
    <>
      <h1 className="mt-0 mb-[30px] font-[700] text-[32px] text-[var(--color-text)]">
        Tổng quan
      </h1>

      {/* Stats Cards - With extracted component & hover effects */}
      <div className="grid xl:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-[30px]">
        {stats.map((item, index) => (
          <StatCard key={index} item={item} />
        ))}
      </div>

      {/* Chart Section - Optimal implementation */}
      <div className="mt-[30px] bg-white shadow-[6px_6px_54px_0px_#0000000D] rounded-[14px] p-[32px]">
        <div className="flex items-center justify-between flex-wrap gap-[10px] mb-[32px]">
          <h2 className="m-0 font-[700] text-[24px] text-[var(--color-text)]">
            Biểu đồ doanh thu
          </h2>
          <input
            type="month"
            className="h-[36px] border border-[#D5D5D5] rounded-[8px] font-[600] text-[14px] px-[16px] outline-none focus:border-[var(--color-primary)] transition-colors"
            aria-label="Chọn tháng"
          />
        </div>
        <div className="h-[350px] w-full">
          <canvas ref={chartRef} aria-label="Biểu đồ doanh thu theo ngày"></canvas>
        </div>
      </div>

      {/* Orders Table - With extracted components */}
      <div className="mt-[30px] bg-white shadow-[6px_6px_54px_0px_#0000000D] rounded-[14px] p-[32px]">
        <h2 className="font-[600] text-[24px] text-[var(--color-text)] mt-0 mb-[30px]">
          Đơn hàng mới
        </h2>
        <div className="overflow-x-auto">
          <table className="w-full border-collapse min-w-[1076px]">
            <thead>
              <tr>
                {TABLE_HEADERS.map((header, index) => (
                  <th
                    key={index}
                    className={getHeaderClass(index, TABLE_HEADERS.length)}
                  >
                    {header}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {orders.map((order) => (
                <OrderRow key={order.id} order={order} />
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}