/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable @next/next/no-img-element */
/* eslint-disable @typescript-eslint/no-explicit-any */
"use client";
import { useState, useMemo, useEffect, useCallback } from "react";
import toast from "react-hot-toast";
import { useRouter } from "next/navigation";
import { CategoryResponse } from "@/models/category.model";
import { categoryService } from "@/services/category.service";
import { Table } from "@/app/components/shared/Table";
import { Breadcrumb } from "@/app/components/shared/Breadcrumb";
import {
  Filter,
  FilterItem,
  Pagination,
  TrashActionBar,
} from "@/app/components/shared/list";
import { CustomSelect } from "@/app/components/shared/CustomSelect";
import { PaginationMeta } from "@/models/base.model";

// Import icon từ react-icons để truyền xuống Table
import { LuRotateCcw, LuEye } from "react-icons/lu";

export default function CategoryTrashPage() {
  const router = useRouter();
  const [data, setData] = useState<CategoryResponse[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  // --- QUẢN LÝ STATE ---
  const [sortConfig, setSortConfig] = useState({
    key: "position",
    direction: "asc" as "asc" | "desc",
  });

  const [meta, setMeta] = useState<PaginationMeta>({
    pageNo: 1,
    pageSize: 10,
    totalPages: 0,
    totalElements: 0,
  });

  // --- BỘ LỌC (FILTERS) & TÌM KIẾM ---
  const [parentIdFilter, setParentIdFilter] = useState("");
  const [creator, setCreator] = useState("");
  const [dateType, setDateType] = useState("createdAt");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const [searchTerm, setSearchTerm] = useState("");
  const [debouncedSearch, setDebouncedSearch] = useState("");

  const [parentOptions, setParentOptions] = useState<{ id: any; name: string }[]>([]);
  const [selectedItems, setSelectedItems] = useState<(number | string)[]>([]);

  const CREATOR_OPTIONS = [
    { id: "all", name: "Tất cả" },
    { id: "Admin", name: "Admin" }
  ];

  const DATE_TYPE_OPTIONS = [
    { id: "createdAt", name: "Ngày tạo" },
    { id: "updatedAt", name: "Ngày cập nhật" },
  ];

  const CATEGORY_BULK_ACTIONS = [
    { id: "RESTORE", name: "Khôi phục" }
  ];

  const formatDate = (dateString?: string) => {
    if (!dateString) return "N/A";
    const date = new Date(dateString);
    return date.toLocaleString("vi-VN", {
      hour: "2-digit",
      minute: "2-digit",
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    });
  };

  // --- LOGIC DEBOUNCE SEARCH ---
  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedSearch(searchTerm);
      setMeta((prev) => ({ ...prev, pageNo: 1 }));
    }, 500);
    return () => clearTimeout(timer);
  }, [searchTerm]);

  // --- API HANDLERS ---
  useEffect(() => {
    const fetchParentOptions = async () => {
      try {
        const res = await categoryService.getAll({ page: 1, size: 100 });
        if (res?.data) {
          const options = [
            { id: "all", name: "Tất cả" },
            ...res.data.map((cat) => ({ id: cat.id, name: cat.name }))
          ];
          setParentOptions(options);
        }
      } catch (error) {
        console.error("Lỗi tải danh mục cha:", error);
      }
    };
    fetchParentOptions();
  }, []);

  const fetchCategories = useCallback(
    async (page: number = 1, currentSort = sortConfig) => {
      try {
        setLoading(true);
        const response = await categoryService.getAllDeleted({
          page,
          size: meta.pageSize,
          sort: `${currentSort.key},${currentSort.direction}`,
          parentId: (parentIdFilter === "all" || parentIdFilter === "") ? undefined : parentIdFilter,
          dateType,
          startDate: startDate || undefined,
          endDate: endDate || undefined,
          createdBy: (creator === "all" || creator === "") ? undefined : creator,
          search: debouncedSearch || undefined,
        } as any);

        if (response && response.success) {
          setData(response.data);
          if (response.meta) setMeta(response.meta);
        }
      } catch (error: any) {
        toast.error(error.message || "Không thể tải danh sách danh mục");
      } finally {
        setLoading(false);
      }
    },
    [meta.pageSize, sortConfig, parentIdFilter, dateType, startDate, endDate, creator, debouncedSearch]
  );

  useEffect(() => {
    fetchCategories(meta.pageNo);
  }, [fetchCategories, meta.pageNo]);

  // --- XỬ LÝ KHÔI PHỤC ---
  const handleRestore = async (id: number | string) => {
    const loadingToast = toast.loading("Đang khôi phục...");
    try {
      await categoryService.restore(Number(id));
      toast.success("Khôi phục danh mục thành công", { id: loadingToast });
      fetchCategories(meta.pageNo);
    } catch (error: any) {
      toast.error(error.message || "Khôi phục thất bại", { id: loadingToast });
    }
  };

  const handleClearFilters = () => {
    setParentIdFilter("");
    setCreator("");
    setStartDate("");
    setEndDate("");
    setDateType("createdAt");
    setSearchTerm("");
    setMeta((prev) => ({ ...prev, pageNo: 1 }));
  };

  const handleBulkAction = async (action: string) => {
    const loadingToast = toast.loading("Đang xử lý hàng loạt...");
    try {
      await categoryService.bulkAction({
        categoryIds: selectedItems as number[],
        action: action,
      });
      toast.success("Thành công", { id: loadingToast });
      setSelectedItems([]);
      fetchCategories(meta.pageNo);
    } catch (error: any) {
      toast.error(error.message || "Thao tác thất bại", { id: loadingToast });
    }
  };

  // --- RENDERERS ---
  const renderers = useMemo(
    () => ({
      name: (item: CategoryResponse) => (
        <span className="font-semibold text-[var(--color-text)]">{item.name}</span>
      ),
      image: (item: CategoryResponse) => (
        <img
          src={item.image || "/assets/images/no-image.png"}
          alt={item.name}
          className="w-[60px] h-[60px] rounded-[6px] object-cover mx-auto border border-[#E5E7EB] bg-gray-50"
        />
      ),
      position: (item: CategoryResponse) => <span className="font-medium">{item.position}</span>,
      status: () => (
        <div className="flex justify-center">
          <span className="flex items-center gap-2 px-3 py-1.5 rounded-[6px] text-[13px] font-[600] bg-[#FEE2E2] text-[#991B1B] border border-[#FECACA]">
            <span className="w-2 h-2 rounded-full bg-[#DC2626]" />
            Đã xóa
          </span>
        </div>
      ),
      createdBy: (item: any) => (
        <div className="text-center px-2">
          <div className="font-bold text-blue-600">{item.createdName || "N/A"}</div>
          <div className="text-[11px] text-gray-500">{formatDate(item.createdAt)}</div>
        </div>
      ),
      updatedBy: (item: any) => (
        <div className="text-center px-2">
          <div className="font-bold text-orange-600">{item.updatedName || "N/A"}</div>
          <div className="text-[11px] text-gray-500">{formatDate(item.updatedAt)}</div>
        </div>
      ),
    }),
    [data]
  );

  return (
    <main>
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">
        Thùng rác danh mục
      </h1>

      <Breadcrumb
        crumbs={[
          { name: "Trang chủ", link: "/" },
          { name: "Danh mục", link: "/category" },
          { name: "Thùng rác", link: "/category/trash" }
        ]}
      />

      <Filter onClear={handleClearFilters}>
        <FilterItem>
          <CustomSelect
            placeholder="Danh mục cha"
            options={parentOptions}
            value={parentIdFilter}
            onChange={(val) => {
              setParentIdFilter(String(val));
              setMeta((prev) => ({ ...prev, pageNo: 1 }));
            }}
          />
        </FilterItem>

        <FilterItem>
          <CustomSelect
            placeholder="Người tạo"
            options={CREATOR_OPTIONS}
            value={creator}
            onChange={(val) => {
              setCreator(String(val));
              setMeta((prev) => ({ ...prev, pageNo: 1 }));
            }}
          />
        </FilterItem>

        <FilterItem>
          <div className="flex items-center gap-2 border border-[#E5E7EB] rounded-[8px] px-3 bg-white h-[40px] shadow-sm">
            <div className="min-w-[100px] flex items-center justify-center">
              <CustomSelect
                options={DATE_TYPE_OPTIONS}
                value={dateType}
                onChange={(val) => {
                  setDateType(String(val));
                  setMeta((prev) => ({ ...prev, pageNo: 1 }));
                }}
                className="border-0 shadow-none font-bold text-[13px]"
              />
            </div>
            <div className="w-[1px] h-4 bg-gray-300 mx-1" />
            <input
              type="date"
              className="border-0 outline-none font-[700] text-[14px] bg-transparent max-w-[110px] cursor-pointer"
              value={startDate}
              onChange={(e) => {
                setStartDate(e.target.value);
                setMeta((prev) => ({ ...prev, pageNo: 1 }));
              }}
            />
            <span className="text-gray-400 font-bold">-</span>
            <input
              type="date"
              className="border-0 outline-none font-[700] text-[14px] bg-transparent max-w-[110px] cursor-pointer"
              value={endDate}
              onChange={(e) => {
                setEndDate(e.target.value);
                setMeta((prev) => ({ ...prev, pageNo: 1 }));
              }}
            />
          </div>
        </FilterItem>
      </Filter>

      <TrashActionBar
        selectedItems={selectedItems}
        bulkActionOptions={CATEGORY_BULK_ACTIONS}
        onBulkAction={handleBulkAction}
        onSearch={(val) => setSearchTerm(val)}
      />

      <div className="mb-[15px]">
        {loading ? (
          <div className="p-20 text-center bg-white rounded-[14px] border border-[#B9B9B9]">
            <div className="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900 mb-2"></div>
            <div className="text-gray-500 font-medium">Đang tải danh sách dữ liệu...</div>
          </div>
        ) : (
          <Table<CategoryResponse>
            tableHeaderList={[
              { label: "Tên danh mục", sortKey: "name" },
              { label: "Ảnh đại diện" },
              { label: "Vị trí", sortKey: "position" },
              { label: "Trạng thái" },
              { label: "Tạo bởi", sortKey: "createdAt" },
              { label: "Cập nhật bởi", sortKey: "updatedAt" },
              { label: "Hành động" },
            ]}
            data={data}
            selectedItems={selectedItems}
            handleSelectAll={(e) => setSelectedItems(e.target.checked ? data.map((i) => i.id) : [])}
            handleSelectItem={(id) =>
              setSelectedItems((prev) =>
                prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
              )
            }
            // TRUYỀN ICON VÀ LINK CHI TIẾT TẠI ĐÂY
            updateLink="/category/detail"
            updateIcon={<LuEye size={18} />}
            handleRestoreClick={handleRestore}
            restoreIcon={<LuRotateCcw size={18} />}

            renderers={renderers}
            centeredColumns={[1, 2, 3, 4, 5, 6]}
            sortConfig={sortConfig}
            onSort={(key) => {
              setSortConfig((prev) => ({
                key,
                direction: prev.key === key && prev.direction === "asc" ? "desc" : "asc",
              }));
              setMeta((prev) => ({ ...prev, pageNo: 1 }));
            }}
          />
        )}
      </div>

      <Pagination
        currentPage={meta.pageNo}
        totalPages={meta.totalPages}
        totalElements={meta.totalElements}
        pageSize={meta.pageSize}
        onPageChange={(newPage) => setMeta((prev) => ({ ...prev, pageNo: newPage }))}
      />
    </main>
  );
}