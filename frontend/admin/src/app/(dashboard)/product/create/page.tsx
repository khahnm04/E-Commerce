"use client";

import { useState, useMemo } from "react";
import toast from "react-hot-toast";

// Hooks & Components
import { useTinyMCE } from "@/hooks/useTinyMCE";
import { Form } from "@/app/components/shared/Form";
import { FormPageLayout } from "@/app/components/shared/FormPageLayout"; // Import Layout chung
import { FilePondWrapper } from "@/app/components/forms/FilePondWrapper";
import MultiImageUpload from "@/app/components/forms/MultiImageUpload";
import AttributeCategoryLayout from "@/app/components/forms/AttributeCategoryLayout";
import { Attribute } from "@/app/components/forms/AttributeInputs";

// 1. Định nghĩa Type
interface ProductFormData {
  name: string;
  slug?: string;
  price: string;
  oldPrice?: string;
  brand?: string;
  status: "active" | "inactive";
  mainImage?: File | string;
  description?: string;
  attributeCategorySection?: unknown;
  productImages?: unknown;
}

interface ProductSubmitPayload extends Omit<ProductFormData, 'attributeCategorySection' | 'productImages'> {
  attributes: Attribute[];
  categories: string[];
  galleryImages: File[];
}

// 2. Constants (Tách ra ngoài)
const CATEGORY_OPTIONS = [
  { value: "", label: "-- Chọn danh mục --" },
  { value: "dien-thoai", label: "Điện thoại" },
  { value: "laptop", label: "Laptop" },
  { value: "tablet", label: "Tablet" },
];

const STATUS_OPTIONS = [
  { value: "active", label: "Hoạt động" },
  { value: "inactive", label: "Tạm dừng" },
];

export default function ProductCreatePage() {
  const { TinyMCEEditorComponent } = useTinyMCE();

  // State cho dữ liệu phức tạp
  const [galleryImages, setGalleryImages] = useState<File[]>([]);
  const [productAttributes, setProductAttributes] = useState<Attribute[]>([]);
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]);

  // 3. Cấu hình Fields
  const fields = useMemo(() => [
    { name: "name" as const, label: "Tên sản phẩm" },
    { name: "slug" as const, label: "Chuỗi định danh URL", props: { placeholder: "Bỏ trống để tự động tạo" } },
    { name: "price" as const, label: "Giá bán" },
    { name: "oldPrice" as const, label: "Giá cũ" },
    { name: "brand" as const, label: "Thương hiệu" },
    {
      name: "status" as const,
      label: "Trạng thái",
      type: "select" as const,
      options: STATUS_OPTIONS
    },
    // Layout phức tạp: Attribute & Category
    {
      name: "attributeCategorySection" as const,
      label: "",
      component: AttributeCategoryLayout,
      props: {
        onAttributesChange: setProductAttributes,
        onCategoriesChange: setSelectedCategories,
        categoryOptions: CATEGORY_OPTIONS,
      },
      className: "md:col-span-2",
    },
    // Main Image
    {
      name: "mainImage" as const,
      label: "Ảnh đại diện",
      component: FilePondWrapper,
      props: { id: "mainImage", name: "mainImage", accept: "image/*" },
      className: "md:col-span-2",
    },
    // Gallery Images
    {
      name: "productImages" as const,
      label: "Ảnh sản phẩm khác",
      component: MultiImageUpload,
      props: { onImagesChange: setGalleryImages },
      className: "md:col-span-2",
    },
    {
      name: "description" as const,
      label: "Mô tả",
      component: () => <>{TinyMCEEditorComponent}</>,
      className: "md:col-span-2",
    },
  ], [TinyMCEEditorComponent]);

  // 4. Validation Schema
  const validationSchema = useMemo(() => [
    { selector: "#name", rules: [{ rule: "required", errorMessage: "Vui lòng nhập tên sản phẩm!" }] },
    { selector: "#price", rules: [{ rule: "required", errorMessage: "Vui lòng nhập giá bán!" }] },
  ], []);

  // 5. Handle Submit
  const handleSubmit = (formData: ProductFormData) => {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { attributeCategorySection, productImages, ...basicData } = formData;

    const payload: ProductSubmitPayload = {
      ...basicData,
      attributes: productAttributes,
      categories: selectedCategories,
      galleryImages: galleryImages
    };

    console.log("Strict Typed Payload:", payload);
    // Call API createProduct(payload);
    toast.success("Tạo sản phẩm thành công!");
  };

  // 6. Render clean với FormPageLayout
  return (
    <FormPageLayout
      title="Tạo sản phẩm"
      breadcrumbs={[
        { name: "Trang chủ", link: "/" },
        { name: "Sản phẩm", link: "/product" },
        { name: "Tạo mới" },
      ]}
      backLink="/product"
    >
      <Form<ProductFormData>
        id="product-create-form"
        fields={fields}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        submitButtonText="Tạo mới"
      />
    </FormPageLayout>
  );
}