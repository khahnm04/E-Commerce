"use client";

import Link from "next/link";
import { useState, useMemo, ChangeEvent } from "react";
import { FaFilter, FaMagnifyingGlass, FaRotateLeft, FaCheck, FaEnvelope } from "react-icons/fa6";

// Components
import { Table } from "@/app/components/shared/Table";
import { Pagination, DeleteModal } from "@/app/components/shared/list"; // Import component chung

// 1. Định nghĩa Interface
interface Notification {
  id: number;
  title: string;
  content: string;
  time: string;
  read: boolean;
  link: string;
}

// Helper: Parse time (Nên tách ra file utils/date.ts trong thực tế)
const parseRelativeTime = (timeString: string): number => {
  const now = new Date();
  if (timeString.includes("phút trước")) {
    now.setMinutes(now.getMinutes() - parseInt(timeString));
  } else if (timeString.includes("giờ trước")) {
    now.setHours(now.getHours() - parseInt(timeString));
  } else if (timeString.includes("ngày trước")) {
    now.setDate(now.getDate() - parseInt(timeString));
  } else if (timeString === "Hôm qua") {
    now.setDate(now.getDate() - 1);
  }
  return now.getTime();
};

export default function NotificationsPage() {
  // 2. Initial Data
  const initialNotifications: Notification[] = [
    { id: 1, title: "Đơn hàng mới", content: "Đơn #DH1234 vừa được tạo", time: "5 phút trước", read: false, link: "#" },
    { id: 2, title: "Cập nhật sản phẩm", content: "Sản phẩm 'iPhone 15' đã được cập nhật giá", time: "15 phút trước", read: false, link: "#" },
    { id: 3, title: "Báo cáo doanh thu", content: "Báo cáo doanh thu tháng 11 đã có", time: "1 giờ trước", read: true, link: "#" },
    { id: 4, title: "Tin nhắn hỗ trợ", content: "Có tin nhắn mới từ khách hàng A", time: "2 giờ trước", read: false, link: "#" },
    { id: 5, title: "Đơn hàng đã giao", content: "Đơn #DH1233 đã được giao thành công", time: "Hôm qua", read: true, link: "#" },
    { id: 6, title: "Cảnh báo hệ thống", content: "Hệ thống bảo trì định kỳ vào 2 giờ sáng mai", time: "Hôm qua", read: false, link: "#" },
    { id: 7, title: "Khuyến mãi mới", content: "Khuyến mãi Tết 2026 sắp diễn ra", time: "2 ngày trước", read: false, link: "#" },
    { id: 8, title: "Báo cáo tồn kho", content: "Sản phẩm XYZ đang sắp hết hàng", time: "3 ngày trước", read: true, link: "#" },
  ];

  // States
  const [notifications, setNotifications] = useState<Notification[]>(
    [...initialNotifications].sort((a, b) => parseRelativeTime(b.time) - parseRelativeTime(a.time))
  );
  const [filterStatus, setFilterStatus] = useState('all');
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedItems, setSelectedItems] = useState<number[]>([]);

  // Modal States
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [itemToDelete, setItemToDelete] = useState<number | null>(null);

  // Table Config
  const tableHeaderList = ['Tiêu đề', 'Nội dung', 'Thời gian', 'Trạng thái', 'Hành động'];

  // 3. Logic Filter
  const filteredData = useMemo(() => {
    return notifications.filter(notif => {
      const matchStatus = filterStatus === 'all' ? true : (filterStatus === 'read' ? notif.read : !notif.read);
      const matchSearch = notif.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
        notif.content.toLowerCase().includes(searchQuery.toLowerCase());
      return matchStatus && matchSearch;
    });
  }, [notifications, filterStatus, searchQuery]);

  // 4. Handlers
  const handleSelectAll = (e: ChangeEvent<HTMLInputElement>) => {
    setSelectedItems(e.target.checked ? filteredData.map(item => item.id) : []);
  };

  const handleSelectItem = (id: number | string) => {
    const numId = Number(id);
    setSelectedItems(prev => prev.includes(numId) ? prev.filter(i => i !== numId) : [...prev, numId]);
  };

  const handleMarkAsRead = (id: number) => {
    setNotifications(prev => prev.map(n => n.id === id ? { ...n, read: true } : n));
  };

  // Thay đổi logic xóa: Mở modal trước
  const handleDeleteClick = (id: number | string) => {
    setItemToDelete(Number(id));
    setShowDeleteConfirm(true);
  };

  const handleConfirmDelete = () => {
    if (itemToDelete !== null) {
      setNotifications(prev => prev.filter(n => n.id !== itemToDelete));
      setSelectedItems(prev => prev.filter(i => i !== itemToDelete));
      setShowDeleteConfirm(false);
      setItemToDelete(null);
    }
  };

  // 5. Renderers
  const renderers = useMemo(() => ({
    title: (item: Notification) => (
      <Link
        href={item.link}
        onClick={() => handleMarkAsRead(item.id)}
        className={`hover:underline flex items-center gap-2 ${!item.read ? 'font-bold text-[var(--color-primary)]' : 'text-[var(--color-text)]'}`}
      >
        {!item.read && <span className="w-2 h-2 rounded-full bg-red-500 inline-block flex-shrink-0"></span>}
        {item.title}
      </Link>
    ),
    content: (item: Notification) => <span className="text-gray-600 line-clamp-1">{item.content}</span>,
    time: (item: Notification) => <span className="text-sm text-gray-500 whitespace-nowrap">{item.time}</span>,
    read: (item: Notification) => (
      <span className={`inline-flex items-center gap-[6px] px-2 py-1 rounded text-sm font-medium whitespace-nowrap ${item.read ? 'text-green-600 bg-green-50' : 'text-blue-600 bg-blue-50'}`}>
        {item.read ? <><FaCheck /> Đã đọc</> : <><FaEnvelope /> Chưa đọc</>}
      </span>
    ),
  }), []);

  return (
    <main>
      <h1 className="mt-0 mb-[30px] font-[700] text-[32px] text-[var(--color-text)]">
        Quản lý thông báo
      </h1>

      {/* Toolbar (Giữ nguyên UI local vì logic filter khác biệt) */}
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
          <div
            className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-[#D5D5D5] font-[700] text-[14px] text-[#EA0234] cursor-pointer hover:bg-gray-50"
            onClick={() => { setFilterStatus('all'); setSearchQuery(''); }}
          >
            <FaRotateLeft className="text-[15px]" /> Xóa bộ lọc
          </div>
        </div>

        <div className="border border-[#E2E2E2] bg-[#fff] inline-flex items-center gap-[15px] max-w-[366px] w-full lg:p-[24px] p-[15px] rounded-[14px]">
          <FaMagnifyingGlass className="text-[20px]" />
          <input
            type="text"
            placeholder="Tìm kiếm thông báo"
            className="text-[14px] font-[700] flex-1 border-0 outline-none text-[var(--color-text)] placeholder:text-[#979797]"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
        </div>
      </div>

      {/* Table */}
      <div className="mb-[15px]">
        <Table<Notification>
          tableHeaderList={tableHeaderList}
          data={filteredData}
          selectedItems={selectedItems}
          handleSelectAll={handleSelectAll}
          handleSelectItem={handleSelectItem}
          handleDeleteClick={handleDeleteClick} // Đã đổi sang hàm mở Modal
          renderers={renderers}
          centeredColumns={[2, 3, 4]}
        />
      </div>

      {/* Pagination (Dùng component chung) */}
      <Pagination total={filteredData.length} />

      {/* Modal xác nhận xóa (Dùng component chung) */}
      <DeleteModal
        isOpen={showDeleteConfirm}
        onClose={() => setShowDeleteConfirm(false)}
        onConfirm={handleConfirmDelete}
        content="Bạn có chắc chắn muốn xóa thông báo này không?"
      />
    </main>
  );
}