"use client";
import Link from "next/link";
import { useState } from "react";
import { FaTimes } from "react-icons/fa";
import { FaFilter, FaMagnifyingGlass, FaCheck, FaRegTrashCan, FaRotateLeft } from "react-icons/fa6"; // Using FaCheck and FaTimes for read/unread status

export default function NotificationsPage() {

  const tableHeaderList = [
    'Tiêu đề',
    'Nội dung',
    'Thời gian',
    'Trạng thái',
    'Hành động'
  ];

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

  const initialNotifications = [
    {
      id: 1,
      title: "Đơn hàng mới",
      content: "Đơn #DH1234 vừa được tạo",
      time: "5 phút trước",
      read: false,
      link: "#" // Optional: link to relevant page
    },
    {
      id: 2,
      title: "Cập nhật sản phẩm",
      content: "Sản phẩm 'iPhone 15' đã được cập nhật giá",
      time: "15 phút trước",
      read: false,
      link: "#"
    },
    {
      id: 3,
      title: "Báo cáo doanh thu",
      content: "Báo cáo doanh thu tháng 11 đã có",
      time: "1 giờ trước",
      read: true,
      link: "#"
    },
    {
      id: 4,
      title: "Tin nhắn hỗ trợ",
      content: "Có tin nhắn mới từ khách hàng A",
      time: "2 giờ trước",
      read: false,
      link: "#"
    },
    {
      id: 5,
      title: "Đơn hàng đã giao",
      content: "Đơn #DH1233 đã được giao thành công",
      time: "Hôm qua",
      read: true,
      link: "#"
    },
    {
      id: 6,
      title: "Cảnh báo hệ thống",
      content: "Hệ thống bảo trì định kỳ vào 2 giờ sáng mai",
      time: "Hôm qua",
      read: false,
      link: "#"
    },
    {
      id: 7,
      title: "Khuyến mãi mới",
      content: "Khuyến mãi Tết 2026 sắp diễn ra",
      time: "2 ngày trước",
      read: false,
      link: "#"
    },
    {
      id: 8,
      title: "Báo cáo tồn kho",
      content: "Sản phẩm XYZ đang sắp hết hàng",
      time: "3 ngày trước",
      read: true,
      link: "#"
    },
  ].sort((a, b) => parseRelativeTime(b.time).getTime() - parseRelativeTime(a.time).getTime());

  const [notifications, setNotifications] = useState(initialNotifications);
  const [filterStatus, setFilterStatus] = useState('all'); // 'all', 'read', 'unread'
  const [currentPage, setCurrentPage] = useState(1);
  const notificationsPerPage = 5;

  const handleMarkAsRead = (id: number) => {
    setNotifications(prevNotifications =>
      prevNotifications.map(notif =>
        notif.id === id ? { ...notif, read: true } : notif
      )
    );
  };

  const handleDeleteNotification = (id: number) => {
    setNotifications(prevNotifications =>
      prevNotifications.filter(notif => notif.id !== id)
    );
  };

  const filteredNotifications = notifications.filter(notif => {
    if (filterStatus === 'read') return notif.read;
    if (filterStatus === 'unread') return !notif.read;
    return true;
  });

  // Pagination logic
  const indexOfLastNotification = currentPage * notificationsPerPage;
  const indexOfFirstNotification = indexOfLastNotification - notificationsPerPage;
  const currentNotifications = filteredNotifications.slice(indexOfFirstNotification, indexOfLastNotification);

  const totalPages = Math.ceil(filteredNotifications.length / notificationsPerPage);

  const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

  return (
    <>
      <main className="">
        <h1 className="mt-0 mb-[30px] font-[700] text-[32px] text-[var(--color-text)]">Quản lý thông báo</h1>

        <div className="mb-[30px] flex flex-wrap gap-[20px]">
          <div className="inline-flex flex-wrap border border-[#D5D5D5] bg-[#FFFFFF] rounded-[14px]">
            <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
              <FaFilter className="text-[22px]" /> Bộ lọc
            </div>
            <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
              <select
                className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer"
                value={filterStatus}
                onChange={(e) => setFilterStatus(e.target.value)}
              >
                <option value="all">Tất cả</option>
                <option value="read">Đã đọc</option>
                <option value="unread">Chưa đọc</option>
              </select>
            </div>
            <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-[#D5D5D5] font-[700] text-[14px] text-[#EA0234] cursor-pointer"
              onClick={() => setFilterStatus('all')}>
              <FaRotateLeft className="text-[15px]" /> Xóa bộ lọc
            </div>
          </div>

          <div className="border border-[#E2E2E2] bg-[#fff] inline-flex items-center gap-[15px] max-w-[366px] w-full lg:p-[24px] p-[15px] rounded-[14px]">
            <FaMagnifyingGlass className="text-[20px]" />
            <input type="text" placeholder="Tìm kiếm thông báo" className="text-[14px] font-[700] flex-1 border-0 outline-none text-[var(--color-text)] placeholder:text-[#979797]" />
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
                          ${['Thời gian', 'Trạng thái', 'Hành động'].includes(item) ? 'text-center' : 'text-left'}`}
                      >
                        {item}
                      </th>
                    ))
                  )}
                </tr>
              </thead>
              <tbody>
                {currentNotifications.length > 0 ? (
                  currentNotifications.map((notif) => (
                    <tr key={notif.id} className={`${!notif.read ? 'bg-blue-50 hover:bg-blue-100' : 'hover:bg-gray-50'}`}>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)]">
                        <input className="w-[20px] h-[20px]" type="checkbox" />
                      </td>
                      <td className={`py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] ${!notif.read ? 'text-blue-700' : 'text-[var(--color-text)]'}`}>
                        <Link href={notif.link || '#'} className="hover:underline" onClick={() => handleMarkAsRead(notif.id)}>
                          {notif.title}
                        </Link>
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] text-[var(--color-text)]">
                        {notif.content}
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)] text-center">
                        {notif.time}
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)] text-center">
                        {notif.read ? (
                          <span className="inline-flex items-center gap-[5px] text-green-600">
                            <FaCheck /> Đã đọc
                          </span>
                        ) : (
                          <span className="inline-flex items-center gap-[5px] text-red-600">
                            <FaTimes /> Chưa đọc
                          </span>
                        )}
                      </td>
                      <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)]">
                        <div className="bg-[#FAFBFD] border border-[#D5D5D5] inline-flex rounded-[8px]">
                          <button
                            className="inline-block px-[16.5px] py-[8.5px] border-r border-[#D5D5D5] text-[15px] text-[rgba(0, 0, 0, 0.6)] hover:bg-gray-100"
                            onClick={() => handleMarkAsRead(notif.id)}
                            title="Đánh dấu đã đọc"
                            disabled={notif.read}
                          >
                            <FaCheck />
                          </button>
                          <button
                            className="inline-block px-[16.5px] py-[8.5px] text-[15px] text-[#EF3826] hover:bg-gray-100"
                            onClick={() => handleDeleteNotification(notif.id)}
                            title="Xóa thông báo"
                          >
                            <FaRegTrashCan />
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan={tableHeaderList.length + 1} className="py-[8px] px-[15px] text-center text-gray-500">
                      Không có thông báo nào phù hợp.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>

        {/* Pagination */}
        {totalPages > 1 && (
          <div className="flex items-center gap-[10px] mt-[20px]">
            <span className="font-[600] text-[14px] text-[var(--color-text)] opacity-[0.6]">
              Hiển thị {indexOfFirstNotification + 1} - {Math.min(indexOfLastNotification, filteredNotifications.length)} của {filteredNotifications.length}
            </span>
            <div className="inline-flex border border-[#D5D5D5] rounded-[8px] bg-[#FAFBFD]">
              {Array.from({ length: totalPages }, (_, i) => (
                <button
                  key={i + 1}
                  onClick={() => paginate(i + 1)}
                  className={`px-[14px] py-[6px] font-[600] text-[14px] ${currentPage === i + 1 ? 'bg-[var(--color-primary)] text-white' : 'text-[var(--color-text)] hover:bg-gray-100'} ${i < totalPages - 1 ? 'border-r border-[#D5D5D5]' : ''}`}
                >
                  {i + 1}
                </button>
              ))}
            </div>
          </div>
        )}

      </main>
    </>
  )
}