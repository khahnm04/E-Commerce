/* eslint-disable jsx-a11y/alt-text */
/* eslint-disable @next/next/no-img-element */
"use client"
import { useEffect } from "react";
import Chart from 'chart.js/auto';

export default function HomePage() {

  const dashboardStats = [
    {
      image: "assets/images/section-1-icon-1.svg",
      name: "Người dùng",
      number: "1.200"
    },
    {
      image: "assets/images/section-1-icon-2.svg",
      name: "Đơn hàng",
      number: "3.600"
    },
    {
      image: "assets/images/section-1-icon-3.svg",
      name: "Doanh thu",
      number: "300.000.000đ"
    },
  ];

  const tableHeaders = ["Mã", "Thông tin khách", "Danh sách tour", "Thanh toán", "Trạng thái", "Ngày đặt"];

  const getHeaderClass = (index: number, total: number) => {
    const baseClass = "bg-[#F1F4F9] px-[24px] py-[14px] font-bold text-[14px] text-[var(--color-text)]";
    const firstClass = index === 0 ? "rounded-l-[12px] text-left" : "text-left";
    const lastClass = index === total - 1 ? "rounded-r-[12px] text-right" : "";
    return `${baseClass} ${firstClass} ${lastClass}`;
  };

  useEffect(() => {
    const chartDashboard = document.querySelector("#chart-dashboard") as HTMLCanvasElement;;

    if (chartDashboard) {
      new Chart(chartDashboard, {
        type: 'line',
        data: {
          labels: ['01', '02', '03', '04', '05'],
          datasets: [
            {
              label: "Tháng 05/2025",
              data: [1000000, 2000000, 3000000, 4000000, 5000000],
              borderColor: "#36A1EA",
              borderWidth: 1.5
            },
            {
              label: "Tháng 04/2025",
              data: [1000000, 2000000, 2000000, 1000000, 6000000],
              borderColor: "#FE6383",
              borderWidth: 1.5
            }
          ]
        },
        options: {
          plugins: {
            legend: {
              position: 'bottom',
            }
          },
          scales: {
            x: {
              display: true,
              title: {
                display: true,
                text: 'Ngày'
              }
            },
            y: {
              display: true,
              title: {
                display: true,
                text: 'Doanh thu (VNĐ)'
              }
            }
          },
          maintainAspectRatio: false
        }
      });
    }
  }, [])

  return (
    <>
      <h1 className="mt-0 mb-[30px] font-[700] text-[32px] text-[var(--color-text)]">
        Tổng quan
      </h1>

      <div className="grid xl:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-[30px]">
        {dashboardStats && (
          dashboardStats.map((item, index) => (
            <div
              key={index}
              className="inner-item bg-white rounded-[14px] p-[28px] flex items-center justify-center gap-[20px]"
            >
              <div className="inner-icon w-[60px]">
                <img
                  src={item.image}
                  className="w-full h-auto"
                />
              </div>
              <div className="inner-content">
                <div className="inner-title font-[600] text-[16px] text-[var(--color-text)] mb-[4px]">
                  {item.name}
                </div>
                <div className="inner-number font-[700] text-[28px] text-[var(--color-text)]">
                  {item.number}
                </div>
              </div>
            </div>
          ))
        )}
      </div>

      <div className="mt-[30px] bg-white shadow-[6px_6px_54px_0px_#0000000D] rounded-[14px] p-[32px]">
        <div className="flex items-center justify-between flex-wrap gap-[10px] mb-[32px]">
          <h2 className="m-0 font-[700] text-[24px] text-[var(--color-text)]">
            Biểu đồ doanh thu
          </h2>
          <div className="">
            <input
              type="month"
              className="h-[28px] border border-[#D5D5D5] rounded-[4px] font-[600] text-[12px] px-[16px]"
            />
          </div>
        </div>
        <div className="h-[350px]">
          <canvas id="chart-dashboard"></canvas>
        </div>
      </div>

      <div className="mt-[30px] bg-white shadow-[6px_6px_54px_0px_#0000000D] rounded-[14px] p-[32px]">
        <h2 className="font-[600] text-[24px] text-[var(--color-text)] mt-0 mb-[30px]">
          Đơn hàng mới
        </h2>
        <div className="overflow-x-auto">
          <table className="w-full border-collapse min-w-[1076px]">
            <thead>
              <tr>
                {tableHeaders.map((item, index) => (
                  <th
                    key={index}
                    className={getHeaderClass(index, tableHeaders.length)}
                  >
                    {item}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              <tr>
                <td className="px-[24px] py-[10px] text-left border-b border-[#97979751] font-[600] text-[14px] text-[var(--color-text)]">
                  <div className="font-[700] text-[14px] text-[var(--color-primary)]">
                    OD000001
                  </div>
                </td>
                <td className="px-[24px] py-[10px] text-left border-b border-[#97979751] font-[600] text-[14px] text-[var(--color-text)]">
                  <div>Họ tên: Lê Văn A</div>
                  <div>SĐT: 0123456789</div>
                  <div>Ghi chú: Test...</div>
                </td>
                <td className="px-[24px] py-[10px] text-left border-b border-[#97979751] font-[600] text-[14px] text-[var(--color-text)]">
                  <div className="flex flex-col gap-[10px]">
                    <div className="flex items-start gap-[10px]">
                      <div className="w-[76px] h-[76px] rounded-[6px] overflow-hidden">
                        <img src="assets/images/tour-1.jpg" className="w-full h-full object-cover" />
                      </div>
                      <div className="">
                        <div className="font-[600] text-[14px] text-[var(--color-text)] mb-[3px]">Tour Hạ Long</div>
                        <div className="flex flex-col gap-[3px] font-[600] text-[12px] text-[var(--color-text)]">
                          <div>Người lớn: 3 x 1.500.000đ</div>
                          <div>Trẻ em: 2 x 1.300.000đ</div>
                          <div>Em bé: 2 x 1.000.000đ</div>
                        </div>
                      </div>
                    </div>
                    <div className="flex items-start gap-[10px]">
                      <div className="w-[76px] h-[76px] rounded-[6px] overflow-hidden">
                        <img src="assets/images/tour-1.jpg" className="w-full h-full object-cover" />
                      </div>
                      <div className="">
                        <div className="font-[600] text-[14px] text-[var(--color-text)] mb-[3px]">Tour Hạ Long</div>
                        <div className="flex flex-col gap-[3px] font-[600] text-[12px] text-[var(--color-text)]">
                          <div>Người lớn: 3 x 1.500.000đ</div>
                          <div>Trẻ em: 2 x 1.300.000đ</div>
                          <div>Em bé: 2 x 1.000.000đ</div>
                        </div>
                      </div>
                    </div>
                  </div>
                </td>
                <td className="px-[24px] py-[10px] text-left border-b border-[#97979751] font-[600] text-[14px] text-[var(--color-text)]">
                  <div>Tổng tiền: 10.000.000đ</div>
                  <div>Giảm: 400.000đ</div>
                  <div>Mã giảm: TOURMUAHE2024</div>
                  <div>Thanh toán: 9.600.000đ</div>
                  <div>PTTT: Ví Momo</div>
                  <div>TTTT: Đã thanh toán</div>
                </td>
                <td className="px-[24px] py-[10px] text-left border-b border-[#97979751] font-[600] text-[14px] text-[var(--color-text)]">
                  <div className="badge badge-orange">Khởi tạo</div>
                </td>
                <td className="px-[24px] py-[10px] border-b border-[#97979751] font-[600] text-[14px] text-[var(--color-text)] text-right">
                  <div>16:20</div>
                  <div>01/01/2024</div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}
