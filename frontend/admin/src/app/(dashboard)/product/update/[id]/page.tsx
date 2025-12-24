"use client";

import { useEffect, useState, useMemo } from "react";
import { useParams } from "next/navigation";
import toast from "react-hot-toast";

// Hooks & Components
import { useTinyMCE } from "@/hooks/useTinyMCE";
import { Form } from "@/app/components/shared/Form";
import { FormPageLayout } from "@/app/components/shared/FormPageLayout"; // Import Layout chung
import { FilePondWrapper } from "@/app/components/forms/FilePondWrapper";
import MultiImageUpload from "@/app/components/forms/MultiImageUpload";
import AttributeCategoryLayout from "@/app/components/forms/AttributeCategoryLayout";
import { Attribute } from "@/app/components/forms/AttributeInputs";

// 1. Types Definitions
interface ProductFormData {
  name: string;
  slug: string;
  price: string;
  oldPrice: string;
  brand: string;
  status: "active" | "inactive";
  mainImage?: string | File;
  description?: string;
  attributeCategorySection?: unknown;
  productImages?: unknown;
}

interface ProductInitialData extends ProductFormData {
  id: number;
  attributes: Attribute[];
  categoryIds: string[];
  existingGalleryImages?: string[];
}

interface ProductSubmitPayload extends Omit<ProductFormData, 'attributeCategorySection' | 'productImages'> {
  id: number;
  attributes: Attribute[];
  categories: string[];
  newGalleryImages: File[];
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

export default function UpdateProductPage() {
  const { id } = useParams();
  const { TinyMCEEditorComponent } = useTinyMCE();

  // 3. States
  const [initialData, setInitialData] = useState<ProductInitialData | null>(null);

  // State quản lý dữ liệu phức tạp
  const [newGalleryImages, setNewGalleryImages] = useState<File[]>([]);
  const [productAttributes, setProductAttributes] = useState<Attribute[]>([]);
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]);

  // 4. Fetch Data & Sync State
  useEffect(() => {
    // Giả lập API call
    const mockData: ProductInitialData = {
      id: 1,
      name: "iPhone 16 Pro Max",
      slug: "iphone-16-pro-max",
      price: "30.000.000",
      oldPrice: "35.000.000",
      brand: "Apple",
      status: "active",
      description: "<p>Mô tả sản phẩm...</p>",
      attributes: [
        { id: "attr1", name: "Màu sắc", value: "Titan Tự nhiên" },
        { id: "attr2", name: "Dung lượng", value: "256GB" },
      ],
      categoryIds: ["dien-thoai"],
    };

    setInitialData(mockData);
    // Sync state phức tạp ngay khi load xong
    setProductAttributes(mockData.attributes);
    setSelectedCategories(mockData.categoryIds);
  }, [id]);

  // 5. Cấu hình Fields
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
    // Layout Attributes & Categories
    {
      name: "attributeCategorySection" as const,
      label: "",
      component: AttributeCategoryLayout,
      props: {
        onAttributesChange: setProductAttributes,
        onCategoriesChange: setSelectedCategories,
        categoryOptions: CATEGORY_OPTIONS,
        initialAttributes: initialData?.attributes,
        initialSelectedCategoryIds: initialData?.categoryIds,
      },
      className: "md:col-span-2",
    },
    {
      name: "mainImage" as const,
      label: "Ảnh đại diện",
      component: FilePondWrapper,
      props: { id: "mainImage", name: "mainImage", accept: "image/*" },
      className: "md:col-span-2",
    },
    {
      name: "productImages" as const,
      label: "Ảnh sản phẩm khác (Thêm mới)",
      component: MultiImageUpload,
      props: { onImagesChange: setNewGalleryImages },
      className: "md:col-span-2",
    },
    {
      name: "description" as const,
      label: "Mô tả",
      component: () => <>{TinyMCEEditorComponent}</>,
      className: "md:col-span-2",
    },
  ], [TinyMCEEditorComponent, initialData]);

  const validationSchema = useMemo(() => [
    { selector: "#name", rules: [{ rule: "required", errorMessage: "Vui lòng nhập tên sản phẩm!" }] },
    { selector: "#price", rules: [{ rule: "required", errorMessage: "Vui lòng nhập giá bán!" }] },
  ], []);

  // 6. Handle Submit
  const handleSubmit = (formData: ProductFormData) => {
    if (!initialData) return;

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { attributeCategorySection, productImages, ...basicData } = formData;

    const payload: ProductSubmitPayload = {
      id: initialData.id,
      ...basicData,
      attributes: productAttributes,
      categories: selectedCategories,
      newGalleryImages: newGalleryImages,
    };

    console.log("Update Payload:", payload);
    toast.success("Cập nhật sản phẩm thành công!");
  };

  // Loading State
  if (!initialData) {
    return (
      <div className="p-10 text-center">
        <span className="loading-text">Đang tải dữ liệu sản phẩm...</span>
      </div>
    );
  }

  // 7. Render clean với FormPageLayout
  return (
    <FormPageLayout
      title="Cập nhật sản phẩm"
      breadcrumbs={[
        { name: "Trang chủ", link: "/" },
        { name: "Sản phẩm", link: "/product" },
        { name: "Cập nhật" },
      ]}
      backLink="/product"
    >
      <Form<ProductFormData>
        id="product-update-form"
        fields={fields}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        initialData={initialData}
        submitButtonText="Cập nhật"
      />
    </FormPageLayout>
  );
}