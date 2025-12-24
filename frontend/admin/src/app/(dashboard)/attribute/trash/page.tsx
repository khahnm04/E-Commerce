"use client";

import { useState, useMemo, ChangeEvent } from "react";
import toast from "react-hot-toast";

// Components
import { Breadcrumb } from "@/app/components/shared/Breadcrumb";
import { Table } from "@/app/components/shared/Table";
import { TrashActionBar, Pagination, DeleteModal } from "@/app/components/shared/list";

// 1. Định nghĩa Interface
interface Attribute {
  id: number;
  name: string;
  code: string;
  description: string;
  createdBy: string;
  createdAt: string;
  updatedBy: string;
  updatedAt: string;
}

export default function AttributeTrashPage() {
  // 2. Data State
  const [data, setData] = useState<Attribute[]>([
    {
      id: 4,
      name: "Trọng lượng",
      code: "WEIGHT",
      description: "Trọng lượng sản phẩm",
      createdBy: "Admin",
      createdAt: "14:00 - 01/11/2025",
      updatedBy: "Admin",
      updatedAt: "14:00 - 01/11/2025",
    },
  ]);

  const [selectedItems, setSelectedItems] = useState<number[]>([]);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [itemToDelete, setItemToDelete] = useState<number | null>(null);

  // 3. Header List
  const tableHeaderList = [
    'Tên thuộc tính',
    'Code',
    'Mô tả',
    'Tạo bởi',
    'Cập nhật bởi',
    'Hành động'
  ];

  // 4. Renderers tối ưu với useMemo
  const renderers = useMemo(() => ({
    name: (item: Attribute) => (
      <span className="font-semibold text-[var(--color-text)]">{item.name}</span>
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
  }), []);

  // 5. Handlers
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
      toast.success("Xóa vĩnh viễn thành công!");
    }
  };

  const handleRestoreClick = (id: number | string) => {
    setData(prev => prev.filter(item => item.id !== Number(id)));
    toast.success("Khôi phục thành công!");
  };

  return (
    <main>
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">
        Thùng rác thuộc tính
      </h1>

      <Breadcrumb
        crumbs={[
          { name: "Trang chủ", link: "/" },
          { name: "Thuộc tính", link: "/attribute" },
          { name: "Thùng rác" },
        ]}
      />

      {/* Sử dụng Shared Component */}
      <TrashActionBar backLink="/attribute" />

      <div className="mb-[15px]">
        <Table<Attribute>
          tableHeaderList={tableHeaderList}
          data={data}
          selectedItems={selectedItems}
          handleSelectAll={handleSelectAll}
          handleSelectItem={handleSelectItem}
          handleDeleteClick={handleDeleteClick}
          handleRestoreClick={handleRestoreClick}
          renderers={renderers}
          centeredColumns={[1, 5]} // Căn giữa cột Code và Hành động
        />
      </div>

      {/* Sử dụng Shared Pagination */}
      <Pagination total={data.length} />

      {/* Sử dụng Shared Modal */}
      <DeleteModal
        isOpen={showDeleteConfirm}
        onClose={() => setShowDeleteConfirm(false)}
        onConfirm={handleConfirmDelete}
        title="Xác nhận xóa vĩnh viễn"
        content="Hành động này không thể hoàn tác. Bạn có chắc chắn muốn xóa không?"
      />
    </main>
  );
}