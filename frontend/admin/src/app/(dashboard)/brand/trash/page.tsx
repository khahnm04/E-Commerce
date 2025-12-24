/* eslint-disable @next/next/no-img-element */
"use client";

import { useState, useMemo, ChangeEvent } from "react";
import toast from "react-hot-toast";

// Components
import { Breadcrumb } from "@/app/components/shared/Breadcrumb";
import { Table } from "@/app/components/shared/Table";
import { Pagination, DeleteModal, TrashActionBar } from "@/app/components/shared/list";

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

export default function BrandTrashPage() {
  // Data State
  const [data, setData] = useState<Brand[]>([
    {
      id: 5,
      name: "Nokia (deleted)",
      slug: "nokia-deleted",
      country: "Finland",
      image: "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/Nokia_logo.svg/1024px-Nokia_logo.svg.png",
      status: "Đã xóa",
      description: "Thương hiệu điện thoại Phần Lan đã bị xóa.",
      createdBy: "Admin",
      createdAt: "10:00 - 02/11/2025",
      updatedBy: "Admin",
      updatedAt: "10:00 - 02/11/2025",
    },
    {
      id: 6,
      name: "BlackBerry (deleted)",
      slug: "blackberry-deleted",
      country: "Canada",
      image: "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/BlackBerry_Limited_logo.svg/1200px-BlackBerry_Limited_logo.svg.png",
      status: "Đã xóa",
      description: "Thương hiệu điện thoại Canada đã bị xóa.",
      createdBy: "Admin",
      createdAt: "11:00 - 02/11/2025",
      updatedBy: "Admin",
      updatedAt: "11:00 - 02/11/2025",
    },
  ]);

  const [selectedItems, setSelectedItems] = useState<number[]>([]);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [itemToDelete, setItemToDelete] = useState<number | null>(null);

  const tableHeaderList = ['Tên thương hiệu', 'Quốc gia', 'Trạng thái', 'Ảnh đại diện', 'Mô tả', 'Tạo bởi', 'Cập nhật bởi', 'Hành động'];

  const getStatusClass = (status: string) => {
    return status === "Đã xóa" ? "bg-[#FEE2E2] text-[#991B1B]" : "bg-gray-200 text-gray-800";
  };

  const renderers = useMemo(() => ({
    name: (item: Brand) => <span className="font-semibold text-[#var(--color-text)]">{item.name}</span>,
    country: (item: Brand) => <span>{item.country}</span>,
    status: (item: Brand) => (
      <span className={`inline-block px-3 py-1 text-sm font-semibold rounded-[8px] mx-auto ${getStatusClass(item.status)}`}>
        {item.status}
      </span>
    ),
    image: (item: Brand) => (
      <img className="w-[60px] h-[60px] rounded-[6px] object-contain mx-auto border border-[#E5E7EB] p-1 bg-white" src={item.image} alt={item.name} />
    ),
    description: (item: Brand) => (
      <span className="line-clamp-2 max-w-[200px] text-gray-500 text-sm" title={item.description}>{item.description}</span>
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
        Thùng rác thương hiệu
      </h1>

      <Breadcrumb
        crumbs={[
          { name: "Trang chủ", link: "/" },
          { name: "Thương hiệu", link: "/brand" },
          { name: "Thùng rác" },
        ]}
      />

      {/* Sử dụng TrashActionBar đã tách */}
      <TrashActionBar backLink="/brand" />

      <div className="mb-[15px]">
        <Table<Brand>
          tableHeaderList={tableHeaderList}
          data={data}
          selectedItems={selectedItems}
          handleSelectAll={handleSelectAll}
          handleSelectItem={handleSelectItem}
          handleDeleteClick={handleDeleteClick}
          handleRestoreClick={handleRestoreClick}
          renderers={renderers}
          centeredColumns={[2, 3, 7]}
        />
      </div>

      {/* Sử dụng Pagination đã tách */}
      <Pagination total={data.length} pageSize={10} current={1} />

      {/* Sử dụng DeleteModal đã tách */}
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