"use client";

import { useEffect, useState, useMemo } from "react";
import { useParams } from "next/navigation";
import toast from "react-hot-toast";

// Hooks & Components
import { useTinyMCE } from "@/hooks/useTinyMCE";
import { Form } from "@/app/components/shared/Form";
import { FilePondWrapper } from "@/app/components/forms/FilePondWrapper";
import { FormPageLayout } from "@/app/components/shared/FormPageLayout"; // Import Layout chung

// 1. Định nghĩa Interface
interface CategoryFormValues {
  id?: number;
  name: string;
  slug: string;
  parent: string;
  position: string | number;
  status: "active" | "inactive";
  avatar?: unknown;
  description?: string;
}

// 2. Constants (Tách ra ngoài để không phải khởi tạo lại mỗi lần render)
const PARENT_OPTIONS = [
  { value: "", label: "-- Chọn danh mục --" },
  { value: "danh-muc-1", label: "Danh mục 1" },
  { value: "danh-muc-2", label: "Danh mục 2" },
];

const STATUS_OPTIONS = [
  { value: "active", label: "Hoạt động" },
  { value: "inactive", label: "Tạm dừng" },
];

export default function UpdateCategoryPage() {
  const { id } = useParams();
  const [initialData, setInitialData] = useState<CategoryFormValues | null>(null);

  const { TinyMCEEditorComponent } = useTinyMCE();

  // 3. Fetch Data (Mock)
  useEffect(() => {
    // Giả lập gọi API lấy chi tiết
    const mockData: CategoryFormValues = {
      id: 1,
      name: "Điện thoại",
      slug: "dien-thoai",
      parent: "danh-muc-1",
      position: "1",
      status: "active",
      description: "<p>Mô tả danh mục điện thoại</p>"
    };
    setInitialData(mockData);
  }, [id]);

  // 4. Cấu hình Fields
  const fields = useMemo(() => [
    { name: "name" as const, label: "Tên danh mục" },
    { name: "slug" as const, label: "Chuỗi định danh URL", props: { placeholder: "Bỏ trống để tự động tạo" } },
    {
      name: "parent" as const,
      label: "Danh mục cha",
      type: "select" as const,
      options: PARENT_OPTIONS
    },
    { name: "position" as const, label: "Vị trí" },
    {
      name: "status" as const,
      label: "Trạng thái",
      type: "select" as const,
      options: STATUS_OPTIONS
    },
    {
      name: "avatar" as const,
      label: "Ảnh đại diện",
      component: FilePondWrapper,
      props: { id: "avatar", name: "avatar", accept: "image/*" },
      className: "md:col-span-2",
    },
    {
      name: "description" as const,
      label: "Mô tả",
      component: () => <>{TinyMCEEditorComponent}</>,
      className: "md:col-span-2",
    },
  ], [TinyMCEEditorComponent]);

  // 5. Validation Schema
  const validationSchema = useMemo(() => [
    { selector: "#name", rules: [{ rule: "required", errorMessage: "Vui lòng nhập tên danh mục!" }] },
  ], []);

  // 6. Handle Submit
  const handleSubmit = (data: CategoryFormValues) => {
    console.log("Form update submitted", data);
    toast.success("Cập nhật danh mục thành công!");
  };

  // Loading state
  if (!initialData) {
    return (
      <div className="p-10 text-center">
        <span className="loading-text">Đang tải dữ liệu...</span>
      </div>
    );
  }

  // 7. Render clean với FormPageLayout
  return (
    <FormPageLayout
      title="Cập nhật danh mục"
      breadcrumbs={[
        { name: "Trang chủ", link: "/" },
        { name: "Danh mục", link: "/category" },
        { name: "Cập nhật" },
      ]}
      backLink="/category"
    >
      <Form<CategoryFormValues>
        id="category-update-form"
        fields={fields}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        initialData={initialData}
        submitButtonText="Cập nhật"
      />
    </FormPageLayout>
  );
}