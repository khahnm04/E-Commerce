"use client";
import { CiViewList } from "react-icons/ci";
import { IoIosLogOut } from "react-icons/io";
import { IoSettingsOutline } from "react-icons/io5";

interface SidebarMenuProps {
  activeTab: string;
  onTabChange: (tab: string) => void;
  onLogout: () => void;
}

export const SidebarMenu = ({ activeTab, onTabChange, onLogout }: SidebarMenuProps) => {
  return (
    <div className="w-[280px] h-[772px] bg-[#fff] p-[16px] rounded-[12px] self-stretch">
      <div
        onClick={() => onTabChange("account")}
        className={`flex items-center gap-[12px] px-[16px] py-[12px] rounded-[8px] cursor-pointer transition-colors ${
          activeTab === "account"
            ? "bg-[#FFEFEE] border-l-4 border-[#D70018]"
            : "hover:bg-[#F5F5F5]"
        }`}
      >
        <IoSettingsOutline className={`text-[20px] ${activeTab === "account" ? "text-[#D70018]" : "text-[#121214]"}`} />
        <span className={`${activeTab === "account" ? "font-[700] text-[14px] text-[#D70018]" : "font-[500] text-[14px] text-[#121214]"}`}>
          Thông tin tài khoản
        </span>
      </div>

      <div
        onClick={() => onTabChange("history")}
        className={`flex items-center gap-[12px] px-[16px] py-[12px] rounded-[8px] cursor-pointer transition-colors ${
          activeTab === "history"
            ? "bg-[#FFEFEE] border-l-4 border-[#D70018]"
            : "hover:bg-[#F5F5F5]"
        }`}
      >
        <CiViewList className={`text-[20px] ${activeTab === "history" ? "text-[#D70018]" : "text-[#121214]"}`} />
        <span className={`${activeTab === "history" ? "font-[700] text-[14px] text-[#D70018]" : "font-[500] text-[14px] text-[#121214]"}`}>
          Lịch sử mua hàng
        </span>
      </div>

      <div 
        onClick={onLogout}
        className="flex items-center gap-[12px] px-[16px] py-[12px] rounded-[8px] hover:bg-[#F5F5F5] cursor-pointer transition-colors"
      >
        <IoIosLogOut className="text-[20px] pl-[2px] text-[#121214]" />
        <span className="font-[500] text-[14px] text-[#121214]">
          Đăng xuất
        </span>
      </div>
    </div>
  );
};

