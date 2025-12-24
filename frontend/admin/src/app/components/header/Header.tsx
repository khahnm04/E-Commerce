"use client"
import { UserDropdown } from "./UserDropdown";
import { NotificationDropdown } from "./NotificationDropdown"; // Import mới
import Link from "next/link"
import { FaBars } from "react-icons/fa6"

export const Header = (props: {
  activeSider: boolean;
  setActiveSider: (value: boolean) => void;
}) => {
  const { activeSider, setActiveSider } = props;

  return (
    <header className="bg-[#FFFFFF] border-b border-[#E0E0E0] h-[70px] fixed top-0 left-0 w-full z-[900] flex">
      <div className="sm:w-[240px] w-auto sm:ml-0 ml-[15px] flex items-center justify-center">
        <Link
          href="#"
          className="font-[800] text-[24px]"
        >
          {/* <span className="text-[var(--color-primary)]">
            Page
          </span> */}
          <span className="text-[var(--color-text)]">Trang Quản Trị</span>
        </Link>
      </div>

      <div className="flex-1 flex justify-end items-center sm:gap-[40px] gap-[20px] sm:mr-[30px] mr-[15px]">
        <NotificationDropdown />

        <UserDropdown
          userName="Le Van A"
          userRole="Admin"
          avatarSrc="/assets/images/avatar.jpg"
        />

        <button
          className="lg:hidden inline-block text-[24px] bg-transparent p-0 border-0 cursor-pointer"
          onClick={() => setActiveSider(!activeSider)}
        >
          <FaBars />
        </button>
      </div>
    </header>
  )
}
