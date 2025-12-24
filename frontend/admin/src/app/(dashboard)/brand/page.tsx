/* eslint-disable @next/next/no-img-element */
"use client";

import { useState, useMemo, ChangeEvent } from "react";
import { Table } from "@/app/components/shared/Table";
import { Breadcrumb } from "@/app/components/shared/Breadcrumb";
import { Filter, ActionBar, Pagination, DeleteModal } from "@/app/components/shared/list";

// Interface
interface Brand {
  id: number;
  name: string;
  slug: string;
  country: string;
  image: string;
  status: string;
  description: string;
  createdBy: string;
  createdAt: string;
  updatedBy: string;
  updatedAt: string;
}

export default function BrandPage() {
  // Filter States
  const [status, setStatus] = useState("");
  const [creator, setCreator] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  // Data State
  const [data, setData] = useState<Brand[]>([
    {
      id: 1,
      name: "Apple",
      slug: "apple",
      country: "USA",
      image: "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/1724px-Apple_logo_black.svg.png",
      status: "Hoạt động",
      description: "Thương hiệu công nghệ hàng đầu thế giới.",
      createdBy: "Admin",
      createdAt: "10:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "10:00 - 01/11/2025",
    },
    // ... (Giữ nguyên data mẫu của bạn)
  ]);

  const [selectedItems, setSelectedItems] = useState<number[]>([]);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [itemToDelete, setItemToDelete] = useState<number | null>(null);

  const tableHeaderList = ['Tên thương hiệu', 'Quốc gia', 'Ảnh đại diện', 'Trạng thái', 'Mô tả', 'Tạo bởi', 'Cập nhật bởi', 'Hành động'];

  const getStatusClass = (status: string) => {
    switch (status) {
      case "Hoạt động": return "bg-[#D1FAE5] text-[#065F46]";
      case "Tạm dừng": return "bg-[#FEE2E2] text-[#991B1B]";
      default: return "bg-gray-200 text-gray-800";
    }
  };

  const renderers = useMemo(() => ({
    name: (item: Brand) => <span className="font-semibold text-[#var(--color-text)]">{item.name}</span>,
    country: (item: Brand) => <span className="text-gray-600">{item.country}</span>,
    image: (item: Brand) => (
      <img className="w-[60px] h-[60px] rounded-[6px] object-contain mx-auto border border-[#E5E7EB] p-1 bg-white" src={item.image} alt={item.name} />
    ),
    status: (item: Brand) => (
      <span className={`inline-block px-3 py-1 text-sm font-semibold rounded-[8px] mx-auto ${getStatusClass(item.status)}`}>{item.status}</span>
    ),
    description: (item: Brand) => (
      <span className="line-clamp-2 max-w-[250px] text-gray-500 text-sm" title={item.description}>{item.description}</span>
    ),
    createdBy: (item: Brand) => <div><div className="font-medium">{item.createdBy}</div><div className="text-[12px] text-gray-500">{item.createdAt}</div></div>,
    updatedBy: (item: Brand) => <div><div className="font-medium">{item.updatedBy}</div><div className="text-[12px] text-gray-500">{item.updatedAt}</div></div>,
  }), []);

  // Handlers
  const handleSelectAll = (e: ChangeEvent<HTMLInputElement>) => {
    setSelectedItems(e.target.checked ? data.map(item => item.id) : []);
  };

  const handleSelectItem = (id: number | string) => {
    const numericId = Number(id);
    setSelectedItems(prev => prev.includes(numericId) ? prev.filter(item => item !== numericId) : [...prev, numericId]);
  };

  const handleClearFilters = () => {
    setStatus(""); setCreator(""); setStartDate(""); setEndDate("");
  };

  const handleDeleteClick = (id: number | string) => {
    setItemToDelete(Number(id));
    setShowDeleteConfirm(true);
  };

  const handleConfirmDelete = () => {
    if (itemToDelete !== null) {
      setData(prev => prev.filter(item => item.id !== itemToDelete));
      setSelectedItems(prev => prev.filter(id => id !== itemToDelete));
      setShowDeleteConfirm(false);
      setItemToDelete(null);
    }
  };

  return (
    <main>
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">Quản lý thương hiệu</h1>
      <Breadcrumb crumbs={[{ name: "Trang chủ", link: "/" }, { name: "Thương hiệu" }]} />

      <Filter
        status={status} setStatus={setStatus}
        creator={creator} setCreator={setCreator}
        startDate={startDate} setStartDate={setStartDate}
        endDate={endDate} setEndDate={setEndDate}
        onClear={handleClearFilters}
      />

      <ActionBar createLink="/brand/create" trashLink="/brand/trash" />

      <div className="mb-[15px]">
        <Table<Brand>
          tableHeaderList={tableHeaderList}
          data={data}
          selectedItems={selectedItems}
          handleSelectAll={handleSelectAll}
          handleSelectItem={handleSelectItem}
          handleDeleteClick={handleDeleteClick}
          updateLink="/brand/update"
          renderers={renderers}
          centeredColumns={[2, 3, 7]}
        />
      </div>

      <Pagination total={78} />

      <DeleteModal
        isOpen={showDeleteConfirm}
        onClose={() => setShowDeleteConfirm(false)}
        onConfirm={handleConfirmDelete}
        content="Bạn có chắc chắn muốn đưa thương hiệu này vào Thùng rác không?"
      />
    </main>
  );
}