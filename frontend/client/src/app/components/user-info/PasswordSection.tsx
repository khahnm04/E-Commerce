"use client";
import { BsPencilSquare } from "react-icons/bs";

interface PasswordSectionProps {
  lastUpdated?: string;
}

export const PasswordSection = ({ lastUpdated = "08/11/2025 08:29" }: PasswordSectionProps) => {
  return (
    <div className="flex-1 bg-[#fff] p-[24px] rounded-[12px]">
      <div className="flex justify-between items-center mb-[16px]">
        <h2 className="font-[700] text-[18px] text-[#121214]">
          Mật khẩu
        </h2>
        <button className="font-[500] text-[14px] text-[#D70018] flex items-center gap-[4px] cursor-pointer">
          <BsPencilSquare className="text-[16px]" />
          Thay đổi mật khẩu
        </button>
      </div>
      <div className="flex justify-between items-center">
        <span className="font-[400] text-[14px] text-[#71717A]">
          Cập nhật lần cuối lúc:
        </span>
        <span className="font-[500] text-[14px] text-[#121214]">
          {lastUpdated}
        </span>
      </div>
    </div>
  );
};

