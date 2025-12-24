/* eslint-disable @next/next/no-img-element */
"use client";

import Link from "next/link";
import { useState, useRef, useEffect } from "react";

export const NotificationDropdown = () => {
  const [showDropdown, setShowDropdown] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  // Logic xử lý thời gian (nên tách ra utils nếu dùng nhiều nơi)
  const parseRelativeTime = (timeString: string): Date => {
    const now = new Date();
    if (timeString.includes("phút trước")) {
      const minutes = parseInt(timeString.replace(" phút trước", ""));
      now.setMinutes(now.getMinutes() - minutes);
    } else if (timeString.includes("giờ trước")) {
      const hours = parseInt(timeString.replace(" giờ trước", ""));
      now.setHours(now.getHours() - hours);
    } else if (timeString === "Hôm qua") {
      now.setDate(now.getDate() - 1);
    }
    return now;
  };

  const [notifications, setNotifications] = useState([
    { id: 1, title: "Đơn hàng mới", content: "Đơn #DH1234 vừa được tạo", time: "5 phút trước", read: false },
    { id: 2, title: "Cập nhật sản phẩm", content: "Sản phẩm 'iPhone 15' đã cập nhật giá", time: "15 phút trước", read: false },
    { id: 3, title: "Báo cáo doanh thu", content: "Báo cáo tháng 11 đã có", time: "1 giờ trước", read: true },
  ].sort((a, b) => parseRelativeTime(b.time).getTime() - parseRelativeTime(a.time).getTime()));

  // Click outside để đóng dropdown
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setShowDropdown(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const handleNotificationClick = (id: number) => {
    setNotifications(prev => prev.map(n => n.id === id ? { ...n, read: true } : n));
    setShowDropdown(false);
  };

  const unreadCount = notifications.filter(n => !n.read).length;

  return (
    <div className="relative cursor-pointer" ref={dropdownRef}>
      <div onClick={() => setShowDropdown(!showDropdown)} className="relative">
        <img
          src="/assets/images/icon-bell.svg"
          alt="Notification"
          className="w-[24px] h-auto"
        />
        {unreadCount > 0 && (
          <span className="h-[16px] min-w-[16px] bg-[#F93C65] font-[700] text-[12px] text-white px-[4px] rounded-[25px] absolute -top-[5px] -right-[4px] flex items-center justify-center">
            {unreadCount}
          </span>
        )}
      </div>

      {showDropdown && (
        <div className="absolute right-0 mt-2 w-[300px] bg-white rounded-[8px] shadow-lg border border-[#E0E0E0] overflow-hidden z-50">
          <div className="p-4 border-b border-[#E0E0E0] font-bold text-[16px]">Thông báo</div>
          <div className="max-h-[300px] overflow-y-auto">
            {notifications.length > 0 ? (
              notifications.map((notif) => (
                <div
                  key={notif.id}
                  className={`p-4 border-b border-[#E0E0E0] hover:bg-gray-50 ${!notif.read ? 'bg-blue-50' : ''}`}
                  onClick={() => handleNotificationClick(notif.id)}
                >
                  <div className={`font-semibold text-[14px] ${!notif.read ? 'text-blue-700' : 'text-gray-800'}`}>
                    {notif.title}
                    {!notif.read && <span className="ml-2 w-2 h-2 bg-blue-500 rounded-full inline-block"></span>}
                  </div>
                  <div className="text-[12px] text-gray-600 mt-1">{notif.content}</div>
                  <div className="text-[10px] text-gray-400 mt-1">{notif.time}</div>
                </div>
              ))
            ) : (
              <div className="p-4 text-center text-gray-500">Không có thông báo mới</div>
            )}
          </div>
          <div className="p-2 text-center border-t border-[#E0E0E0]">
            <Link href="/notifications" className="text-[12px] text-blue-500 hover:underline">Xem tất cả</Link>
          </div>
        </div>
      )}
    </div>
  );
};