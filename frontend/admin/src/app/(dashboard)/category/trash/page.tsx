/* eslint-disable @next/next/no-img-element */
"use client";

import { useState, useMemo, ChangeEvent } from "react";
import toast from "react-hot-toast";

// Components
import { Breadcrumb } from "@/app/components/shared/Breadcrumb";
import { Table } from "@/app/components/shared/Table";
import { TrashActionBar, Pagination, DeleteModal } from "@/app/components/shared/list";

// 1. Định nghĩa Interface
interface Category {
  id: number;
  name: string;
  image: string;
  position: number;
  status: string;
  createdBy: string;
  createdAt: string;
  updatedBy: string;
  updatedAt: string;
}

export default function CategoryTrashPage() {
  // 2. Data State
  const [data, setData] = useState<Category[]>([
    {
      id: 5,
      name: "Đồng hồ",
      image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-pro-max.png",
      position: 5,
      status: "Đã xóa",
      createdBy: "Admin",
      createdAt: "10:00 - 02/11/2025",
      updatedBy: "Admin",
      updatedAt: "10:00 - 02/11/2025",
    },
  ]);

  const [selectedItems, setSelectedItems] = useState<number[]>([]);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [itemToDelete, setItemToDelete] = useState<number | null>(null);

  // 3. Header & Renderers cho Table
  const tableHeaderList = [
    'Tên danh mục',
    'Ảnh đại diện',
    'Vị trí',
    'Trạng thái',
    'Tạo bởi',
    'Cập nhật bởi',
    'Hành động'
  ];

  const renderers = useMemo(() => ({
    name: (item: Category) => (
      <span className="font-semibold text-[var(--color-text)]">{item.name}</span>
    ),
    image: (item: Category) => (
      <img
        className="w-[60px] h-[60px] rounded-[6px] object-cover mx-auto border border-gray-200"
        src={item.image}
        alt={item.name}
      />
    ),
    position: (item: Category) => <span>{item.position}</span>,
    status: (item: Category) => {
      const baseClass = "inline-block px-3 py-1 text-sm font-semibold rounded-[8px]";
      const statusClass = item.status === "Đã xóa"
        ? "bg-[#FEE2E2] text-[#991B1B]"
        : "bg-gray-200 text-gray-800";
      return <span className={`${baseClass} ${statusClass}`}>{item.status}</span>;
    },
    createdBy: (item: Category) => (
      <div>
        <div>{item.createdBy}</div>
        <div className="text-[12px] text-gray-500">{item.createdAt}</div>
      </div>
    ),
    updatedBy: (item: Category) => (
      <div>
        <div>{item.updatedBy}</div>
        <div className="text-[12px] text-gray-500">{item.updatedAt}</div>
      </div>
    ),
  }), []);

  // 4. Handlers
  const handleSelectAll = (e: ChangeEvent<HTMLInputElement>) => {
    setSelectedItems(e.target.checked ? data.map(item => item.id) : []);
  };

  const handleSelectItem = (id: number | string) => {
    const numericId = Number(id);
    setSelectedItems(prev =>
      prev.includes(numericId) ? prev.filter(item => item !== numericId) : [...prev, numericId]
    );
  };

  const handleRestoreClick = (id: number | string) => {
    setData(prev => prev.filter(item => item.id !== Number(id)));
    toast.success("Khôi phục thành công!");
  };

  const handleDeleteClick = (id: number | string) => {
    setItemToDelete(Number(id));
    setShowDeleteConfirm(true);
  };

  const handleConfirmDelete = () => {
    if (itemToDelete !== null) {
      setData(prev => prev.filter(item => item.id !== itemToDelete));
      setItemToDelete(null);
      setShowDeleteConfirm(false);
      setSelectedItems(prev => prev.filter(id => id !== itemToDelete));
      toast.success("Đã xóa vĩnh viễn danh mục!");
    }
  };

  // 5. Render
  return (
    <main>
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">
        Thùng rác
      </h1>

      <Breadcrumb
        crumbs={[
          { name: "Trang chủ", link: "/" },
          { name: "Danh mục", link: "/category" },
          { name: "Thùng rác" },
        ]}
      />

      {/* Sử dụng TrashActionBar dùng chung */}
      <TrashActionBar backLink="/category" />

      {/* Sử dụng Table Generic */}
      <div className="mb-[15px]">
        <Table<Category>
          tableHeaderList={tableHeaderList}
          data={data}
          selectedItems={selectedItems}
          handleSelectAll={handleSelectAll}
          handleSelectItem={handleSelectItem}
          handleDeleteClick={handleDeleteClick}
          handleRestoreClick={handleRestoreClick}
          renderers={renderers}
          // Căn giữa: Ảnh(1), Vị trí(2), Trạng thái(3), Hành động(6)
          centeredColumns={[1, 2, 3, 6]}
        />
      </div>

      {/* Sử dụng Pagination dùng chung */}
      <Pagination total={data.length} current={1} pageSize={10} />

      {/* Sử dụng Modal dùng chung */}
      <DeleteModal
        isOpen={showDeleteConfirm}
        onClose={() => setShowDeleteConfirm(false)}
        onConfirm={handleConfirmDelete}
        title="Xác nhận xóa vĩnh viễn"
        content="Hành động này không thể hoàn tác. Bạn có chắc chắn muốn xóa mục này khỏi hệ thống không?"
      />
    </main>
  );
}