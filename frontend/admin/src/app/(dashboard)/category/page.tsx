/* eslint-disable @next/next/no-img-element */
"use client";

import { useState, useMemo, ChangeEvent } from "react";
import { Table } from "@/app/components/shared/Table";
import { Breadcrumb } from "@/app/components/shared/Breadcrumb";
import { Filter, ActionBar, Pagination, DeleteModal } from "@/app/components/shared/list";

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

export default function CategoryPage() {
  const [status, setStatus] = useState("");
  const [creator, setCreator] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const [data, setData] = useState<Category[]>([
    {
      id: 1,
      name: "Điện thoại",
      image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-pro-max.png",
      position: 1,
      status: "Hoạt động",
      createdBy: "Admin",
      createdAt: "10:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "10:00 - 01/11/2025",
    },
    // ... Data mẫu khác
  ]);

  const [selectedItems, setSelectedItems] = useState<number[]>([]);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [itemToDelete, setItemToDelete] = useState<number | null>(null);

  const tableHeaderList = ['Tên danh mục', 'Ảnh đại diện', 'Vị trí', 'Trạng thái', 'Tạo bởi', 'Cập nhật bởi', 'Hành động'];

  const getStatusClass = (status: string) => {
    switch (status) {
      case "Hoạt động": return "bg-[#D1FAE5] text-[#065F46]";
      case "Tạm dừng": return "bg-[#FEE2E2] text-[#991B1B]";
      default: return "bg-gray-200 text-gray-800";
    }
  };

  const renderers = useMemo(() => ({
    name: (item: Category) => <span>{item.name}</span>,
    image: (item: Category) => <img className="w-[60px] h-[60px] rounded-[6px] object-cover mx-auto" src={item.image} alt={item.name} />,
    position: (item: Category) => <span>{item.position}</span>,
    status: (item: Category) => <span className={`inline-block px-3 py-1 text-sm font-semibold rounded-[8px] mx-auto ${getStatusClass(item.status)}`}>{item.status}</span>,
    createdBy: (item: Category) =>
      <div>
        <div>
          {item.createdBy}
        </div>
        <div className="text-[12px]">
          {item.createdAt}
        </div>
      </div>,
    updatedBy: (item: Category) =>
      <div>
        <div>
          {item.updatedBy}
        </div>
        <div className="text-[12px]">
          {item.updatedAt}
        </div>
      </div>,
  }), []);

  const handleSelectAll = (e: ChangeEvent<HTMLInputElement>) => setSelectedItems(e.target.checked ? data.map(i => i.id) : []);
  const handleSelectItem = (id: number | string) => {
    const numericId = Number(id);
    setSelectedItems(prev => prev.includes(numericId) ? prev.filter(i => i !== numericId) : [...prev, numericId]);
  };
  const handleClearFilters = () => { setStatus(""); setCreator(""); setStartDate(""); setEndDate(""); };
  const handleDeleteClick = (id: number | string) => { setItemToDelete(Number(id)); setShowDeleteConfirm(true); };
  const handleConfirmDelete = () => {
    if (itemToDelete) {
      setData(prev => prev.filter(i => i.id !== itemToDelete));
      setSelectedItems(prev => prev.filter(id => id !== itemToDelete));
      setShowDeleteConfirm(false);
      setItemToDelete(null);
    }
  };

  return (
    <main>
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">Quản lý danh mục</h1>
      <Breadcrumb crumbs={[{ name: "Trang chủ", link: "/" }, { name: "Danh mục" }]} />

      <Filter
        status={status} setStatus={setStatus}
        creator={creator} setCreator={setCreator}
        startDate={startDate} setStartDate={setStartDate}
        endDate={endDate} setEndDate={setEndDate}
        onClear={handleClearFilters}
      />

      <ActionBar createLink="/category/create" trashLink="/category/trash" />

      <div className="mb-[15px]">
        <Table<Category>
          tableHeaderList={tableHeaderList}
          data={data}
          selectedItems={selectedItems}
          handleSelectAll={handleSelectAll}
          handleSelectItem={handleSelectItem}
          handleDeleteClick={handleDeleteClick}
          updateLink="/category/update"
          renderers={renderers}
          centeredColumns={[1, 2, 3, 6]}
        />
      </div>

      <Pagination total={78} />

      <DeleteModal
        isOpen={showDeleteConfirm}
        onClose={() => setShowDeleteConfirm(false)}
        onConfirm={handleConfirmDelete}
      />
    </main>
  );
}