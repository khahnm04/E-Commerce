import { ReactNode } from "react";
import { FaFilter, FaRotateLeft } from "react-icons/fa6";

interface FilterProps {
  onClear: () => void;
  children: ReactNode;
}

export const Filter = ({ onClear, children }: FilterProps) => (
  <div className="mb-[15px]">
    <div className="inline-flex flex-wrap border border-[#D5D5D5] bg-[#FFFFFF] rounded-[14px]">

      {/* 1. Label "Bộ lọc" - Sửa padding */}
      <div className="lg:py-[16px] lg:px-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
        <FaFilter className="text-[22px]" /> Bộ lọc
      </div>

      {/* Render các ô input tùy chỉnh */}
      {children}

      {/* 2. Nút "Xóa bộ lọc" - Sửa padding */}
      <div
        className="lg:py-[16px] lg:px-[24px] p-[15px] inline-flex items-center gap-[12px] border-[#D5D5D5] font-[700] text-[14px] text-[#EA0234] cursor-pointer hover:bg-red-50 transition-colors rounded-r-[14px]"
        onClick={onClear}
      >
        <FaRotateLeft className="text-[15px]" /> Xóa bộ lọc
      </div>
    </div>
  </div>
);

// 3. FilterItem (Các ô ở giữa) - Sửa padding
export const FilterItem = ({ children }: { children: ReactNode }) => (
  <div className="lg:py-[16px] lg:px-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
    {children}
  </div>
);