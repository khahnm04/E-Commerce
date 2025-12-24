"use client";

import { useMemo } from "react";
import toast from "react-hot-toast";

// Components
import { useTinyMCE } from "@/hooks/useTinyMCE";
import { Form } from "@/app/components/shared/Form";
import { FilePondWrapper } from "@/app/components/forms/FilePondWrapper";
import { FormPageLayout } from "@/app/components/shared/FormPageLayout";

// Interfaces & Constants
interface BrandFormValues {
  name: string;
  slug?: string;
  country: string;
  status: "active" | "inactive";
  image?: File | string;
  description?: string;
}

const COUNTRY_OPTIONS = [
  { value: "", label: "-- Chọn quốc gia --" },
  { value: "VN", label: "Việt Nam" },
  { value: "US", label: "Hoa Kỳ" },
  { value: "KR", label: "Hàn Quốc" },
  { value: "JP", label: "Nhật Bản" },
  { value: "CN", label: "Trung Quốc" },
];

export default function CreateBrandPage() {
  const { TinyMCEEditorComponent } = useTinyMCE();

  const fields = useMemo(() => [
    { name: "name" as const, label: "Tên thương hiệu" },
    { name: "slug" as const, label: "Chuỗi định danh URL", props: { placeholder: "Bỏ trống để tự động tạo" } },
    { name: "country" as const, label: "Quốc gia", type: "select" as const, options: COUNTRY_OPTIONS },
    { name: "status" as const, label: "Trạng thái", type: "select" as const, options: [{ value: "active", label: "Hoạt động" }, { value: "inactive", label: "Tạm dừng" }] },
    { name: "image" as const, label: "Ảnh đại diện", component: FilePondWrapper, props: { id: "image", name: "image", accept: "image/*" }, className: "md:col-span-2" },
    { name: "description" as const, label: "Mô tả", component: () => <>{TinyMCEEditorComponent}</>, className: "md:col-span-2" },
  ], [TinyMCEEditorComponent]);

  const validationSchema = useMemo(() => [
    { selector: "#name", rules: [{ rule: "required", errorMessage: "Vui lòng nhập tên thương hiệu!" }] },
  ], []);

  const handleSubmit = (data: BrandFormValues) => {
    console.log("Form submitted", data);
    toast.success("Tạo thương hiệu thành công!");
  };

  return (
    <FormPageLayout
      title="Tạo thương hiệu"
      breadcrumbs={[{ name: "Trang chủ", link: "/" }, { name: "Thương hiệu", link: "/brand" }, { name: "Tạo mới" }]}
      backLink="/brand"
    >
      <Form<BrandFormValues>
        id="brand-create-form"
        fields={fields}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        submitButtonText="Tạo mới"
      />
    </FormPageLayout>
  );
}