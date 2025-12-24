"use client";

import { useMemo } from "react";
import toast from "react-hot-toast";

// Hooks & Components
import { useTinyMCE } from "@/hooks/useTinyMCE";
import { Form } from "@/app/components/shared/Form";
import { FilePondWrapper } from "@/app/components/forms/FilePondWrapper";
import { FormPageLayout } from "@/app/components/shared/FormPageLayout"; // Import Layout chung

// 1. Định nghĩa Interface
interface CategoryFormValues {
  name: string;
  slug?: string;
  parent?: string;
  position?: string | number;
  status: "active" | "inactive";
  avatar?: File;
  description?: string;
}

// Constants (Tách ra để code gọn hơn)
const STATUS_OPTIONS = [
  { value: "active", label: "Hoạt động" },
  { value: "inactive", label: "Tạm dừng" },
];

const MOCK_PARENT_OPTIONS = [
  { value: "", label: "-- Chọn danh mục --" },
  { value: "danh-muc-1", label: "Danh mục 1" },
  { value: "danh-muc-2", label: "Danh mục 2" },
];

export default function CreateCategoryPage() {
  const { TinyMCEEditorComponent } = useTinyMCE();

  // 2. Cấu hình Fields
  const fields = useMemo(() => [
    { name: "name" as const, label: "Tên danh mục" },
    { name: "slug" as const, label: "Chuỗi định danh URL", props: { placeholder: "Bỏ trống để tự động tạo" } },
    {
      name: "parent" as const,
      label: "Danh mục cha",
      type: "select" as const,
      options: MOCK_PARENT_OPTIONS
    },
    { name: "position" as const, label: "Vị trí", type: "number" as const },
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
      props: { accept: "image/*" },
      className: "md:col-span-2",
    },
    {
      name: "description" as const,
      label: "Mô tả",
      component: () => <>{TinyMCEEditorComponent}</>,
      className: "md:col-span-2",
    },
  ], [TinyMCEEditorComponent]);

  // 3. Validation Schema
  const validationSchema = useMemo(() => [
    { selector: "#name", rules: [{ rule: "required", errorMessage: "Vui lòng nhập tên danh mục!" }] },
  ], []);

  // 4. Handle Submit
  const handleSubmit = (data: CategoryFormValues) => {
    console.log("Form submitted", data);
    toast.success("Tạo danh mục thành công!");
  };

  // 5. Render clean với FormPageLayout
  return (
    <FormPageLayout
      title="Tạo danh mục"
      breadcrumbs={[
        { name: "Trang chủ", link: "/" },
        { name: "Danh mục", link: "/category" },
        { name: "Tạo mới" },
      ]}
      backLink="/category"
    >
      <Form<CategoryFormValues>
        id="category-create-form"
        fields={fields}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        submitButtonText="Tạo mới"
      />
    </FormPageLayout>
  );
}