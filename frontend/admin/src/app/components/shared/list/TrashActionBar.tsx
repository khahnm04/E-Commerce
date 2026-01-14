"use client";

import { useState, ChangeEvent } from "react";
import { FaMagnifyingGlass } from "react-icons/fa6";
import { CustomSelect } from "@/app/components/shared/CustomSelect";
import toast from "react-hot-toast";

// Định nghĩa kiểu cho Action để module nào cũng dùng được
export interface BulkActionOption {
  id: string;
  name: string;
}

interface ActionBarProps {
  selectedItems: (number | string)[];
  bulkActionOptions: BulkActionOption[]; // Truyền danh sách hành động từ trang cha
  onBulkAction: (action: string) => Promise<void>; // Hàm xử lý API do trang cha cung cấp
  onSearch: (value: string) => void; // Hàm báo giá trị tìm kiếm thay đổi
}

export const TrashActionBar = ({
  selectedItems,
  bulkActionOptions,
  onBulkAction,
  onSearch,
}: ActionBarProps) => {
  const [action, setAction] = useState("");
  const [searchValue, setSearchValue] = useState("");

  // Xử lý khi nhấn nút Áp dụng hành động hàng loạt
  const handleApply = async () => {
    if (selectedItems.length === 0) {
      toast.error("Vui lòng chọn ít nhất một mục");
      return;
    }
    if (!action) {
      toast.error("Vui lòng chọn hành động");
      return;
    }
    await onBulkAction(action);
    setAction("");
  };

  const handleSearchChange = (e: ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setSearchValue(value);
    onSearch(value);
  };

  return (
    <div className="mb-[30px]">
      <div className="flex flex-wrap gap-[20px] items-center">

        {/* KHỐI HÀNH ĐỘNG HÀNG LOẠT */}
        <div className="inline-flex items-center border border-[#D5D5D5] bg-[#FFFFFF] rounded-[14px]">
          {/* Ô Chọn Hành Động */}
          <div className="w-[180px] h-[71px] flex items-center justify-center border-r border-[#D5D5D5] rounded-l-[14px]">
            <CustomSelect
              placeholder="Hành động"
              options={bulkActionOptions}
              value={action}
              onChange={(val) => setAction(String(val))}
              className="font-[700] text-[14px] text-[var(--color-text)] border-0 shadow-none"
            />
          </div>

          {/* Nút Áp Dụng */}
          <div className="h-[71px] px-[24px] flex items-center justify-center bg-white hover:bg-gray-50 transition-colors rounded-r-[14px]">
            <button
              onClick={handleApply}
              className="bg-transparent border-0 font-[600] text-[14px] text-[#EA0234] cursor-pointer outline-none"
            >
              Áp dụng
            </button>
          </div>
        </div>

        {/* KHỐI TÌM KIẾM (SEARCH) */}
        <div className="border border-[#E2E2E2] bg-[#fff] inline-flex items-center gap-[15px] max-w-[366px] w-full h-[71px] px-[24px] rounded-[14px]">
          <FaMagnifyingGlass className="text-[20px] text-[#979797]" />
          <input
            type="text"
            placeholder="Tìm kiếm"
            value={searchValue}
            onChange={handleSearchChange}
            className="text-[14px] font-[700] flex-1 border-0 outline-none text-[var(--color-text)] placeholder:text-[#979797] bg-transparent"
          />
        </div>

      </div>
    </div>
  );
};