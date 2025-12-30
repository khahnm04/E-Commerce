/* eslint-disable @next/next/no-img-element */
"use client";

import { useState, useMemo, ChangeEvent, useEffect } from "react";
import toast from "react-hot-toast";
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

  // 2. Handlers

  // Hàm xử lý thay đổi trạng thái nhanh qua API
  const handleStatusChange = async (id: number | string, newStatus: string) => {
    try {
      // Giả lập gọi API cập nhật trạng thái
      // await productService.updateStatus(id, newStatus);

      setData((prevData) =>
        prevData.map((item) =>
          item.id === id ? { ...item, status: newStatus } : item
        )
      );
      toast.success(`Đã chuyển trạng thái sang: ${newStatus}`);
    } catch (error) {
      toast.error("Cập nhật trạng thái thất bại!");
    }
  };

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
      toast.success("Đã đưa sản phẩm vào thùng rác");
    }
  };

  // 3. Table Renderers
  const tableHeaderList = [
    'Tên sản phẩm',
    'Ảnh',
    'Giá bán',
    'Giá cũ',
    'Thương hiệu',
    'Trạng thái',
    'Tạo bởi',
    'Cập nhật bởi',
    'Hành động'
  ];

  const renderers = useMemo(() => ({
    name: (item: Product) =>
      <span className="font-semibold text-[var(--color-text)]">
        {item.name}
      </span>,
    image: (item: Product) =>
      <img
        className="w-[60px] h-[60px] rounded-[6px] object-cover mx-auto border border-[#E5E7EB]"
        src={item.image}
        alt={item.name}
      />,
    price: (item: Product) =>
      <span className="font-bold text-[#EF3826]">
        {item.price}
      </span>,
    oldPrice: (item: Product) =>
      <span className="text-[#9CA3AF] line-through text-[13px]">
        {item.oldPrice}
      </span>,
    brand: (item: Product) =>
      <span className="font-medium bg-gray-100 px-2 py-1 rounded text-[12px] text-gray-600">
        {item.brand}
      </span>,

    // Renderer mới cho Status: Sử dụng thẻ select để cập nhật nhanh
    status: (item: Product) => (
      <select
        value={item.status}
        onChange={(e) => handleStatusChange(item.id, e.target.value)}
        className={`px-3 py-1 text-sm font-semibold rounded-[8px] border-none outline-none cursor-pointer transition-colors ${item.status === "Hoạt động"
          ? "bg-[#D1FAE5] text-[#065F46]"
          : "bg-[#FEE2E2] text-[#991B1B]"
          }`}
      >
        <option
          value="Hoạt động"
          className="bg-white text-[#000000] cursor-pointer"
        >
          Hoạt động
        </option>
        <option
          value="Tạm dừng"
          className="bg-white text-[#000000] cursor-pointer"
        >
          Tạm dừng
        </option>
      </select>
    ),

    createdBy: (item: Product) => (
      <div>
        <div className="font-medium">{item.createdBy}</div>
        <div className="text-[12px] text-gray-500">{item.createdAt}</div>
      </div>
    ),
    updatedBy: (item: Product) => (
      <div>
        <div className="font-medium">{item.updatedBy}</div>
        <div className="text-[12px] text-gray-500">{item.updatedAt}</div>
      </div>
    ),
  }), [data]); // data được thêm vào dependency để renderer cập nhật khi state data thay đổi

  return (
    <main>
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">
        Quản lý sản phẩm
      </h1>

      <Breadcrumb crumbs={[{ name: "Trang chủ", link: "/" }, { name: "Sản phẩm" }]} />

      <Filter
        status={status} setStatus={setStatus}
        creator={creator} setCreator={setCreator}
        startDate={startDate} setStartDate={setStartDate}
        endDate={endDate} setEndDate={setEndDate}
        onClear={handleClearFilters}
      />

      <ActionBar createLink="/product/create" trashLink="/product/trash" />

      <div className="mb-[15px]">
        <Table<Product>
          tableHeaderList={tableHeaderList}
          data={data}
          selectedItems={selectedItems}
          handleSelectAll={handleSelectAll}
          handleSelectItem={handleSelectItem}
          handleDeleteClick={handleDeleteClick}
          handleStatusChange={handleStatusChange} // Truyền prop mới vào Table
          updateLink="/product/update"
          renderers={renderers}
          centeredColumns={[1, 4, 5, 8]}
        />
      </div>

      <Pagination total={78} />

      <DeleteModal
        isOpen={showDeleteConfirm}
        onClose={() => setShowDeleteConfirm(false)}
        onConfirm={handleConfirmDelete}
        content="Bạn có chắc chắn muốn đưa sản phẩm này vào Thùng rác không?"
      />
    </main>
  );
}