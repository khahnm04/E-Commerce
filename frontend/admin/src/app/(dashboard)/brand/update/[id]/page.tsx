"use client";

import { useEffect, useState, useMemo } from "react";
import { useParams } from "next/navigation";
import toast from "react-hot-toast";

import { useTinyMCE } from "@/hooks/useTinyMCE";
import { Form } from "@/app/components/shared/Form";
import { FilePondWrapper } from "@/app/components/forms/FilePondWrapper";
import { FormPageLayout } from "@/app/components/shared/FormPageLayout";

interface BrandFormValues {
  name: string;
  slug: string;
  country: string;
  status: "active" | "inactive";
  image?: File | string;
  description?: string;
}

interface BrandInitialData extends BrandFormValues { id: number; }

const COUNTRY_OPTIONS = [
  { value: "", label: "-- Chọn quốc gia --" },
  { value: "VN", label: "Việt Nam" },
  { value: "US", label: "Hoa Kỳ" },
  { value: "KR", label: "Hàn Quốc" },
  { value: "JP", label: "Nhật Bản" },
  { value: "CN", label: "Trung Quốc" },
];

export default function UpdateBrandPage() {
  const { id } = useParams();
  const [initialData, setInitialData] = useState<BrandInitialData | null>(null);
  const { TinyMCEEditorComponent } = useTinyMCE();

  useEffect(() => {
    // Mock API call
    setInitialData({
      id: 1, name: "Apple", slug: "apple", country: "US", status: "active",
      description: "<p>Thương hiệu công nghệ hàng đầu thế giới.</p>",
    });
  }, [id]);

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
    console.log("Form updated", data);
    toast.success("Cập nhật thương hiệu thành công!");
  };

  if (!initialData) return <div className="p-10 text-center">Đang tải dữ liệu...</div>;

  return (
    <FormPageLayout
      title="Cập nhật thương hiệu"
      breadcrumbs={[{ name: "Trang chủ", link: "/" }, { name: "Thương hiệu", link: "/brand" }, { name: "Cập nhật" }]}
      backLink="/brand"
    >
      <Form<BrandFormValues>
        id="brand-update-form"
        fields={fields}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        initialData={initialData}
        submitButtonText="Cập nhật"
      />
    </FormPageLayout>
  );
}