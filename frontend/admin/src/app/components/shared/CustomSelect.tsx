"use client";
import { useState, useRef, useEffect } from "react";
import { FaChevronDown } from "react-icons/fa";

interface Option {
  id: number | string;
  name: string;
}

interface CustomSelectProps {
  options: Option[];
  value: string | number;
  onChange: (val: string | number) => void;
  placeholder?: string;
  className?: string; // Class từ cha truyền vào (để chỉnh màu, nền, font...)
}

export const CustomSelect = ({
  options,
  value,
  onChange,
  placeholder = "Chọn...",
  className = ""
}: CustomSelectProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const containerRef = useRef<HTMLDivElement>(null);

  // Tìm tên hiển thị tương ứng với value đang chọn
  const selectedLabel = options.find((opt) => String(opt.id) === String(value))?.name;

  // Xử lý click ra ngoài để đóng dropdown
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (containerRef.current && !containerRef.current.contains(event.target as Node)) {
        setIsOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <div
      className={`relative w-fit ${className}`} // className từ cha sẽ áp dụng vào đây (Màu chữ, màu nền...)
      ref={containerRef}
    >
      {/* NÚT KÍCH HOẠT (TRIGGER) */}
      <div
        onClick={() => setIsOpen(!isOpen)}
        className="
          flex items-center gap-2 cursor-pointer 
          py-2 px-1
          font-[700] text-[14px] 
          hover:opacity-70 transition-opacity
        "
      // LƯU Ý: Đã xóa text-[var(--color-text)] để nhận màu từ className cha
      >
        <span className="whitespace-nowrap">
          {selectedLabel || placeholder}
        </span>
        <FaChevronDown
          className={`text-[10px] opacity-60 transition-transform duration-200 ${isOpen ? "rotate-180" : ""}`}
        />
      </div>

      {/* DANH SÁCH ĐỔ XUỐNG (DROPDOWN LIST) */}
      {isOpen && (
        <div
          className="
            absolute top-full left-0 mt-1 
            w-max min-w-full max-w-[300px] 
            bg-white border border-[#E5E7EB] rounded-[8px] shadow-xl 
            z-50 max-h-[300px] overflow-y-auto animate-fade-in-up
          "
        >
          {options.map((opt) => (
            <div
              key={opt.id}
              onClick={() => {
                onChange(opt.id);
                setIsOpen(false);
              }}
              className={`
                px-4 py-2.5 cursor-pointer text-[14px] font-medium transition-colors border-b border-gray-50 last:border-none whitespace-nowrap
                ${String(value) === String(opt.id)
                  ? "text-blue-600 bg-blue-50"
                  : "text-gray-600 hover:bg-gray-50 hover:text-black"
                }
              `}
            >
              {opt.name}
            </div>
          ))}

          {options.length === 0 && (
            <div className="px-4 py-3 text-gray-400 text-[13px] italic text-center whitespace-nowrap">
              Không có dữ liệu
            </div>
          )}
        </div>
      )}
    </div>
  );
};