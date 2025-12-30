/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable react-hooks/exhaustive-deps */
"use client";

import { useState, useMemo, ChangeEvent, useEffect } from "react";
import toast from "react-hot-toast";

// Components
import { Table } from "@/app/components/shared/Table";
import { Breadcrumb } from "@/app/components/shared/Breadcrumb";
import { Filter, ActionBar, Pagination, DeleteModal } from "@/app/components/shared/list";

// 1. Định nghĩa Interface
interface Attribute {
  id: number;
  name: string;
  code: string;
  description: string;
  status: string; // Thêm trường status
  createdBy: string;
  createdAt: string;
  updatedBy: string;
  updatedAt: string;
}

export default function AttributePage() {
  // Filter States
  const [status, setStatus] = useState("");
  const [creator, setCreator] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  // 2. Data State
  const [data, setData] = useState<Attribute[]>([
    {
      id: 1,
      name: "Màu sắc",
      code: "COLOR",
      description: "Màu sắc của sản phẩm",
      status: "Hoạt động",
      createdBy: "Admin",
      createdAt: "10:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "10:00 - 01/11/2025",
    },
    {
      id: 2,
      name: "Kích thước",
      code: "SIZE",
      description: "Kích thước sản phẩm (S, M, L, XL)",
      status: "Hoạt động",
      createdBy: "Admin",
      createdAt: "11:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "11:00 - 01/11/2025",
    },
    {
      id: 3,
      name: "Chất liệu",
      code: "MATERIAL",
      description: "Chất liệu sản phẩm",
      status: "Tạm dừng",
      createdBy: "Admin",
      createdAt: "12:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "12:00 - 01/11/2025",
    },
  ]);

  const [selectedItems, setSelectedItems] = useState<number[]>([]);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [itemToDelete, setItemToDelete] = useState<number | null>(null);

  // 3. Handlers

  // Hàm xử lý thay đổi trạng thái nhanh (Sẽ gọi API PATCH trong thực tế)
  const handleStatusChange = async (id: number | string, newStatus: string) => {
    try {
      // Giả lập gọi API thành công
      // await attributeService.update(id, { status: newStatus });

      setData((prevData) =>
        prevData.map((item) =>
          item.id === id ? { ...item, status: newStatus } : item
        )
      );
      toast.success(`Cập nhật trạng thái thành công!`);
    } catch (error) {
      toast.error("Không thể cập nhật trạng thái");
    }
  };

  const handleSelectAll = (e: ChangeEvent<HTMLInputElement>) => {
    setSelectedItems(e.target.checked ? data.map(item => item.id) : []);
  };

  const handleSelectItem = (id: number | string) => {
    const numericId = Number(id);
    setSelectedItems(prevSelected =>
      prevSelected.includes(numericId)
        ? prevSelected.filter(item => item !== numericId)
        : [...prevSelected, numericId]
    );
  };

  const handleClearFilters = () => {
    setStatus("");
    setCreator("");
    setStartDate("");
    setEndDate("");
  };

  const handleDeleteClick = (id: number | string) => {
    setItemToDelete(Number(id));
    setShowDeleteConfirm(true);
  };

  const handleConfirmDelete = () => {
    if (itemToDelete !== null) {
      setData(prevList => prevList.filter(item => item.id !== itemToDelete));
      setShowDeleteConfirm(false);
      setItemToDelete(null);
      setSelectedItems(prev => prev.filter(id => id !== itemToDelete));
      toast.success("Đưa vào thùng rác thành công!");
    }
  };

  // 4. Header List
  const tableHeaderList = [
    'Tên thuộc tính',
    'Code',
    'Mô tả',
    'Trạng thái', // Thêm cột Trạng thái
    'Tạo bởi',
    'Cập nhật bởi',
    'Hành động'
  ];

  // 5. Renderers tối ưu với useMemo
  const renderers = useMemo(() => ({
    name: (item: Attribute) => (
      <span className="font-semibold text-[#var(--color-text)]">{item.name}</span>
    ),
    code: (item: Attribute) => (
      <span className="inline-block bg-blue-50 text-blue-600 px-2 py-1 rounded text-xs font-bold uppercase">
        {item.code}
      </span>
    ),
    description: (item: Attribute) => (
      <span className="max-w-[200px] block truncate text-gray-600" title={item.description}>
        {item.description}
      </span>
    ),
    // Renderer cho cột trạng thái với thẻ select để cập nhật nhanh
    status: (item: Attribute) => (
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
    createdBy: (item: Attribute) => (
      <div>
        <div className="font-medium">{item.createdBy}</div>
        <div className="text-[12px] text-gray-500">{item.createdAt}</div>
      </div>
    ),
    updatedBy: (item: Attribute) => (
      <div>
        <div className="font-medium">{item.updatedBy}</div>
        <div className="text-[12px] text-gray-500">{item.updatedAt}</div>
      </div>
    ),
  }), [data]); // Thêm data vào dependency để update UI khi đổi trạng thái

  return (
    <main>
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">
        Thuộc tính sản phẩm
      </h1>

      <Breadcrumb
        crumbs={[
          { name: "Trang chủ", link: "/" },
          { name: "Thuộc tính" },
        ]}
      />

      <Filter
        status={status} setStatus={setStatus}
        creator={creator} setCreator={setCreator}
        startDate={startDate} setStartDate={setStartDate}
        endDate={endDate} setEndDate={setEndDate}
        onClear={handleClearFilters}
      />

      <ActionBar createLink="/attribute/create" trashLink="/attribute/trash" />

      <div className="mb-[15px]">
        <Table<Attribute>
          tableHeaderList={tableHeaderList}
          data={data}
          selectedItems={selectedItems}
          handleSelectAll={handleSelectAll}
          handleSelectItem={handleSelectItem}
          handleDeleteClick={handleDeleteClick}
          handleStatusChange={handleStatusChange} // Truyền prop mới vào Table
          updateLink="/attribute/update"
          renderers={renderers}
          centeredColumns={[1, 3, 6]} // Căn giữa Code (1), Trạng thái (3), Hành động (6)
        />
      </div>

      <Pagination total={data.length} />

      <DeleteModal
        isOpen={showDeleteConfirm}
        onClose={() => setShowDeleteConfirm(false)}
        onConfirm={handleConfirmDelete}
        content="Bạn có chắc chắn muốn đưa thuộc tính này vào Thùng rác không?"
      />
    </main>
  );
}