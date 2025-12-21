/* eslint-disable @next/next/no-img-element */
import { useState, useRef, useEffect, MouseEvent } from "react";
import { FaGear, FaUserGear, FaArrowRightFromBracket } from "react-icons/fa6"; // Updated icons
import { useRouter } from "next/navigation";
import Link from "next/link";

interface UserDropdownProps {
  userName: string;
  userRole: string;
  avatarSrc: string;
}

export const UserDropdown = ({ userName, userRole, avatarSrc }: UserDropdownProps) => {
  const [showDropdown, setShowDropdown] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);
  const router = useRouter();

  // Close dropdown when clicking outside
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setShowDropdown(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside as unknown as EventListener);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside as unknown as EventListener);
    };
  }, []);

  const handleLogout = () => {
    // Implement actual logout logic here
    console.log("Logging out...");
    // For now, redirect to login page
    router.push("/login");
    setShowDropdown(false);
  };

  return (
    <div className="relative cursor-pointer" ref={dropdownRef}>
      <div onClick={() => setShowDropdown(!showDropdown)} className="inline-flex items-center gap-[10px]">
        <div className="w-[44px] h-[44px] rounded-[50%] overflow-hidden">
          <img
            src={avatarSrc}
            alt="User Avatar"
            className="w-full h-full object-cover"
          />
        </div>
        <div className="flex-1">
          <div className="font-[700] text-[14px] text-[#404040] mb-[3px]">{userName}</div>
          <div className="font-[600] text-[12px] text-[#565656]">{userRole}</div>
        </div>
      </div>

      {showDropdown && (
        <div className="absolute right-0 mt-2 w-[200px] bg-white rounded-[8px] shadow-lg border border-[#E0E0E0] overflow-hidden z-10 transition-opacity duration-200 ease-in-out">
          <Link href="/settings" onClick={() => setShowDropdown(false)} className="flex items-center gap-[10px] p-4 hover:bg-gray-50 border-b border-[#E0E0E0]">
            <FaGear className="text-[16px] text-gray-600" /> {/* Changed from FaUserGear to FaGear */}
            <span className="text-[14px] font-[600] text-[var(--color-text)]">Cài đặt chung</span>
          </Link>
          <Link href="/profile" onClick={() => setShowDropdown(false)} className="flex items-center gap-[10px] p-4 hover:bg-gray-50 border-b border-[#E0E0E0]">
            <FaUserGear className="text-[16px] text-gray-600" /> {/* Changed from FaUser to FaUserGear */}
            <span className="text-[14px] font-[600] text-[var(--color-text)]">Thông tin cá nhân</span>
          </Link>
          <button onClick={handleLogout} className="flex items-center gap-[10px] p-4 w-full text-left text-[#EA0234] hover:bg-gray-50">
            <FaArrowRightFromBracket className="text-[16px]" />
            <span className="text-[14px] font-[600]">Đăng xuất</span>
          </button>
        </div>
      )}
    </div>
  );
};