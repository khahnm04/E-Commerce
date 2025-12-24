/* eslint-disable @next/next/no-img-element */
"use client";

import { useState, useMemo, ChangeEvent } from "react";
import toast from "react-hot-toast";

// Components
import { Breadcrumb } from "@/app/components/shared/Breadcrumb";
import { Table } from "@/app/components/shared/Table";
import { TrashActionBar, Pagination, DeleteModal } from "@/app/components/shared/list";

// 1. Định nghĩa Interface
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

export default function ProductTrashPage() {
  // 2. Data State
  const [data, setData] = useState<Product[]>([
    {
      id: 2,
      name: "Macbook Pro M4",
      image: "https://cdn2.cellphones.com.vn/insecure/rs:fill:300:300/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-pro-max.png",
      price: "50.000.000đ",
      oldPrice: "55.000.000đ",
      brand: "Apple",
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

  // Header List
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

  // 3. Renderers tối ưu với useMemo
  const renderers = useMemo(() => ({
    name: (item: Product) => (
      <span className="font-semibold text-[#var(--color-text)]">{item.name}</span>
    ),
    image: (item: Product) => (
      <img
        className="w-[60px] h-[60px] rounded-[6px] object-cover mx-auto border border-[#E5E7EB]"
        src={item.image}
        alt={item.name}
      />
    ),
    price: (item: Product) => (
      <span className="font-bold text-[#EF3826]">{item.price}</span>
    ),
    oldPrice: (item: Product) => (
      <span className="text-[#9CA3AF] line-through text-[13px]">{item.oldPrice}</span>
    ),
    brand: (item: Product) => (
      <span className="bg-gray-100 px-2 py-1 rounded text-[12px] text-gray-600 font-medium">
        {item.brand}
      </span>
    ),
    status: (item: Product) => (
      <span className="inline-block px-3 py-1 text-sm font-semibold rounded-[8px] bg-[#FEE2E2] text-[#991B1B]">
        {item.status}
      </span>
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
  }), []);

  // Handlers
  const handleSelectAll = (e: ChangeEvent<HTMLInputElement>) => {
    setSelectedItems(e.target.checked ? data.map(item => item.id) : []);
  };

  const handleSelectItem = (id: number | string) => {
    const numericId = Number(id);
    setSelectedItems(prev =>
      prev.includes(numericId) ? prev.filter(item => item !== numericId) : [...prev, numericId]
    );
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
      toast.success("Đã xóa vĩnh viễn sản phẩm!");
    }
  };

  const handleRestoreClick = (id: number | string) => {
    setData(prev => prev.filter(item => item.id !== Number(id)));
    toast.success("Khôi phục thành công!");
  };

  return (
    <main>
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">
        Thùng rác sản phẩm
      </h1>

      <Breadcrumb
        crumbs={[
          { name: "Trang chủ", link: "/" },
          { name: "Sản phẩm", link: "/product" },
          { name: "Thùng rác" },
        ]}
      />

      {/* Sử dụng Shared Component cho Toolbar */}
      <TrashActionBar backLink="/product" />

      <div className="mb-[15px]">
        <Table<Product>
          tableHeaderList={tableHeaderList}
          data={data}
          selectedItems={selectedItems}
          handleSelectAll={handleSelectAll}
          handleSelectItem={handleSelectItem}
          handleDeleteClick={handleDeleteClick}
          handleRestoreClick={handleRestoreClick}
          renderers={renderers}
          // Căn giữa: Ảnh(1), Thương hiệu(4), Trạng thái(5), Hành động(8)
          centeredColumns={[1, 4, 5, 8]}
        />
      </div>

      {/* Sử dụng Shared Component cho Pagination */}
      <Pagination total={data.length} />

      {/* Sử dụng Shared Component cho Modal */}
      <DeleteModal
        isOpen={showDeleteConfirm}
        onClose={() => setShowDeleteConfirm(false)}
        onConfirm={handleConfirmDelete}
        title="Xác nhận xóa vĩnh viễn"
        content="Hành động này không thể hoàn tác. Bạn có chắc chắn muốn xóa sản phẩm này không?"
      />
    </main>
  );
}