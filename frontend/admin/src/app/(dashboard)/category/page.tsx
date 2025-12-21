"use client";
/* eslint-disable jsx-a11y/alt-text */
/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable @next/next/no-img-element */
import Link from "next/link";
import { useEffect, useState } from "react";
import { FaFilter, FaMagnifyingGlass, FaRegPenToSquare, FaRegTrashCan, FaRotateLeft } from "react-icons/fa6";

export default function CategoryPage() {

  const tableHeaderList = [
    'Tên danh mục',
    'Ảnh đại diện',
    'Vị trí',
    'Trạng thái',
    'Tạo bởi',
    'Cập nhật bởi',
    'Hành động'
  ];

  const [bodyHeaderList, setBodyHeaderList] = useState<any[]>([
    {
      id: 1,
      name: "Điện thoại",
      image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-pro-max.png",
      position: 1,
      status: "Hoạt động",
      createdBy: "Admin",
      createdAt: "10:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "10:00 - 01/11/2025",
    },
    {
      id: 2,
      name: "Laptop",
      image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-pro-max.png",
      position: 2,
      status: "Hoạt động",
      createdBy: "Admin",
      createdAt: "11:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "11:00 - 01/11/2025",
    },
    {
      id: 3,
      name: "Tablet",
      image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-pro-max.png",
      position: 3,
      status: "Tạm dừng",
      createdBy: "Admin",
      createdAt: "12:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "12:00 - 01/11/2025",
    },
    {
      id: 4,
      name: "Phụ kiện",
      image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-pro-max.png",
      position: 4,
      status: "Hoạt động",
      createdBy: "Admin",
      createdAt: "13:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "13:00 - 01/11/2025",
    },
  ]);

  return (
    <>
      <main className="">
        <h1 className="mt-0 mb-[30px] font-[700] text-[32px] text-[var(--color-text)]">Quản lý danh mục</h1>

        <div className="mb-[15px]">
          <div className="inline-flex flex-wrap border border-[#D5D5D5] bg-[#FFFFFF] rounded-[14px]">
            <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
              <FaFilter className="text-[22px]" /> Bộ lọc
            </div>
            <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
              <select className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer">
                <option value="">Trạng thái</option>
                <option value="">Hoạt động</option>
                <option value="">Tạm dừng</option>
              </select>
            </div>
            <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
              <select className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer">
                <option value="">Người tạo</option>
                <option value="">Lê Văn A</option>
                <option value="">Lê Văn B</option>
              </select>
            </div>
            <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
              <input type="date" className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer w-[110px]" />
              <span>-</span>
              <input type="date" className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer w-[110px]" />
            </div>
            <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-[#D5D5D5] font-[700] text-[14px] text-[#EA0234] cursor-pointer">
              <FaRotateLeft className="text-[15px]" /> Xóa bộ lọc
            </div>
          </div>
        </div>

        <div className="mb-[30px]">
          <div className="flex flex-wrap gap-[20px]">
            <div className="inline-flex flex-wrap border border-[#D5D5D5] bg-[#FFFFFF] rounded-[14px]">
              <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
                <select className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer">
                  <option value="">-- Hành động --</option>
                  <option value="">Hoạt động</option>
                  <option value="">Tạm dừng</option>
                  <option value="">Xóa</option>
                </select>
              </div>
              <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
                <button className="bg-transparent border-0 font-[600] text-[14px] text-[#EA0234] cursor-pointer">
                  Áp dụng
                </button>
              </div>
            </div>
            <div className="border border-[#E2E2E2] bg-[#fff] inline-flex items-center gap-[15px] max-w-[366px] w-full lg:p-[24px] p-[15px] rounded-[14px]">
              <FaMagnifyingGlass className="text-[20px]" />
              <input type="text" placeholder="Tìm kiếm" className="text-[14px] font-[700] flex-1 border-0 outline-none text-[var(--color-text)] placeholder:text-[#979797]" />
            </div>
            <div className="">
              <Link
                href="/category/create"
                className="inline-block bg-[var(--color-primary)] rounded-[14px] lg:px-[50px] px-[30px] lg:py-[24px] py-[15px] font-[700] text-[14px] text-white"
              >
                + Tạo mới
              </Link>
            </div>
            <div className="">
              <Link href="#" className="inline-block bg-[#EF382633] rounded-[14px] px-[30px] lg:py-[24px] py-[15px] font-[700] text-[14px] text-[#EF3826]">
                Thùng rác
              </Link>
            </div>
          </div>
        </div>

        <div className="mb-[15px]">
          <div className="overflow-x-auto border border-[#B9B9B9] rounded-[14px] overflow-hidden">
            <table className="bg-[#fff] w-full min-w-[1141px]">
              <thead>
                <tr>
                  <th className="bg-[#FCFDFD] border-b border-[#D5D5D5] p-[15px] font-[800] text-[14px] text-[var(--color-text)] text-left">
                    <input className="w-[20px] h-[20px]" type="checkbox" />
                  </th>
                  {tableHeaderList && (
                    tableHeaderList.map((item, index) => (
                      <th
                        key={index}
                        className={`bg-[#FCFDFD] border-b border-[#D5D5D5] p-[15px] font-[800] text-[14px] text-[var(--color-text)] 
                          ${[1, 2, 3].includes(index) ? 'text-center' : 'text-left'}`}
                      >
                        {item}
                      </th>
                    ))
                  )}
                </tr>
              </thead>
              <tbody>
                {bodyHeaderList && (
                  bodyHeaderList.map((item, index) => (
                    <tr key={index}>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)]">
                        <input className="w-[20px] h-[20px]" type="checkbox" />
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)]">
                        {item.name}
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)] text-center">
                        <img
                          className="w-[60px] h-[60px] rounded-[6px] object-cover mx-auto"
                          src={item.image}
                        />
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)] text-center">
                        {item.id}
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)] text-center">
                        <div className="">
                          {item.status}
                        </div>
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)]">
                        <div>
                          {item.createdBy}
                        </div>
                        <div className="text-[12px]">
                          {item.createdAt}
                        </div>
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)]">
                        <div>
                          {item.updatedBy}
                        </div>
                        <div className="text-[12px]">
                          {item.updatedAt}
                        </div>
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)]">
                        <div className="bg-[#FAFBFD] border border-[#D5D5D5] inline-flex rounded-[8px]">
                          <a className="inline-block px-[16.5px] py-[8.5px] border-r border-[#D5D5D5] text-[15px] text-[rgba(0, 0, 0, 0.6)]" href="#">
                            <FaRegPenToSquare />
                          </a>
                          <a className="inline-block px-[16.5px] py-[8.5px] text-[15px] text-[#EF3826]" href="#">
                            <FaRegTrashCan />
                          </a>
                        </div>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>

        <div className="flex items-center gap-[20px]">
          <span className="font-[600] text-[14px] text-[var(--color-text)] opacity-[0.6]">
            Hiển thị 1 - 9 của 78
          </span>
          <select className="border border-[#D5D5D5] rounded-[8px] bg-[#FAFBFD] px-[14px] py-[6px] outline-none font-[600] text-[14px] text-[var(--color-text)]">
            <option value="">Trang 1</option>
            <option value="">Trang 2</option>
            <option value="">Trang 3</option>
          </select>
        </div>

      </main>
    </>
  )
}