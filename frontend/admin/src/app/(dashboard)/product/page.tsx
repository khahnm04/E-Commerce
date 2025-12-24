/* eslint-disable @next/next/no-img-element */
"use client";

import { useState, useMemo, ChangeEvent } from "react";
import { Table } from "@/app/components/shared/Table";
import { Breadcrumb } from "@/app/components/shared/Breadcrumb";
import { Filter, ActionBar, Pagination, DeleteModal } from "@/app/components/shared/list";

// 1. Interface
interface Product {
  id: number;
  name: string;
  image: string;
  price: string;
  oldPrice: string;
  brand: string;
  status: string;
  createdBy: string;
  createdAt: string;
  updatedBy: string;
  updatedAt: string;
}

export default function ProductPage() {
  // Filter States
  const [status, setStatus] = useState("");
  const [creator, setCreator] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  // Data State
  const [data, setData] = useState<Product[]>([
    {
      id: 1,
      name: "iPhone 16 Pro Max",
      image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-pro-max.png",
      price: "30.000.000đ",
      oldPrice: "35.000.000đ",
      brand: "Apple",
      status: "Hoạt động",
      createdBy: "Admin",
      createdAt: "10:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "10:00 - 01/11/2025",
    },
  ]);

  const [selectedItems, setSelectedItems] = useState<number[]>([]);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [itemToDelete, setItemToDelete] = useState<number | null>(null);

  // Helper function: Status Class
  const getStatusClass = (status: string) => {
    switch (status) {
      case "Hoạt động": return "bg-[#D1FAE5] text-[#065F46]";
      case "Tạm dừng": return "bg-[#FEE2E2] text-[#991B1B]";
      default: return "bg-gray-200 text-gray-800";
    }
  };

  // 2. Table Renderers
  const tableHeaderList = ['Tên sản phẩm', 'Ảnh', 'Giá bán', 'Giá cũ', 'Thương hiệu', 'Trạng thái', 'Tạo bởi', 'Cập nhật bởi', 'Hành động'];

  const renderers = useMemo(() => ({
    name: (item: Product) => <span className="font-semibold text-[var(--color-text)]">{item.name}</span>,
    image: (item: Product) => <img className="w-[60px] h-[60px] rounded-[6px] object-cover mx-auto border border-[#E5E7EB]" src={item.image} alt={item.name} />,
    price: (item: Product) => <span className="font-bold text-[#EF3826]">{item.price}</span>,
    oldPrice: (item: Product) => <span className="text-[#9CA3AF] line-through text-[13px]">{item.oldPrice}</span>,
    brand: (item: Product) => <span className="font-medium bg-gray-100 px-2 py-1 rounded text-[12px] text-gray-600">{item.brand}</span>,
    status: (item: Product) => (
      <span className={`inline-block px-3 py-1 text-sm font-semibold rounded-[8px] ${getStatusClass(item.status)}`}>
        {item.status}
      </span>
    ),
    createdBy: (item: Product) => (
      <div>
        <div className="font-medium">{item.createdBy}</div><div className="text-[12px] text-gray-500">
          {item.createdAt}
        </div>
      </div>
    ),
    updatedBy: (item: Product) => (<div><div className="font-medium">{item.updatedBy}</div><div className="text-[12px] text-gray-500">{item.updatedAt}</div></div>),
  }), []);

  // Handlers
  const handleSelectAll = (e: ChangeEvent<HTMLInputElement>) => {
    setSelectedItems(e.target.checked ? data.map(item => item.id) : []);
  };

  const handleSelectItem = (id: number | string) => {
    const numericId = Number(id);
    setSelectedItems(prev => prev.includes(numericId) ? prev.filter(i => i !== numericId) : [...prev, numericId]);
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
      setShowDeleteConfirm(false);
      setItemToDelete(null);
      setSelectedItems(prev => prev.filter(id => id !== itemToDelete));
    }
  };

  return (
    <main>
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">
        Quản lý sản phẩm
      </h1>

      <Breadcrumb crumbs={[{ name: "Trang chủ", link: "/" }, { name: "Sản phẩm" }]} />

      {/* Sử dụng Shared Filter */}
      <Filter
        status={status} setStatus={setStatus}
        creator={creator} setCreator={setCreator}
        startDate={startDate} setStartDate={setStartDate}
        endDate={endDate} setEndDate={setEndDate}
        onClear={handleClearFilters}
      />

      {/* Sử dụng Shared ActionBar */}
      <ActionBar createLink="/product/create" trashLink="/product/trash" />

      {/* Table Section */}
      <div className="mb-[15px]">
        <Table<Product>
          tableHeaderList={tableHeaderList}
          data={data}
          selectedItems={selectedItems}
          handleSelectAll={handleSelectAll}
          handleSelectItem={handleSelectItem}
          handleDeleteClick={handleDeleteClick}
          updateLink="/product/update"
          renderers={renderers}
          centeredColumns={[1, 4, 5, 8]}
        />
      </div>

      {/* Sử dụng Shared Pagination */}
      <Pagination total={78} />

      {/* Sử dụng Shared Delete Modal */}
      <DeleteModal
        isOpen={showDeleteConfirm}
        onClose={() => setShowDeleteConfirm(false)}
        onConfirm={handleConfirmDelete}
        content="Bạn có chắc chắn muốn đưa sản phẩm này vào Thùng rác không?"
      />
    </main>
  );
}