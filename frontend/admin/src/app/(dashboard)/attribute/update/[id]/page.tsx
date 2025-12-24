"use client";

import { useEffect, useState, useMemo } from "react";
import { useParams } from "next/navigation";
import toast from "react-hot-toast";

// Hooks & Components
import { useTinyMCE } from "@/hooks/useTinyMCE";
import { Form } from "@/app/components/shared/Form";
import { FormPageLayout } from "@/app/components/shared/FormPageLayout"; // Import Layout chung

// 1. Định nghĩa Interface
interface AttributeFormValues {
  name: string;
  code: string;
  description?: string;
}

interface AttributeInitialData extends AttributeFormValues {
  id: number;
}

export default function UpdateAttributePage() {
  const { id } = useParams();
  const [initialData, setInitialData] = useState<AttributeInitialData | null>(null);

  const { TinyMCEEditorComponent } = useTinyMCE();

  // 2. Fetch Data (Mock)
  useEffect(() => {
    // Giả lập API call
    const mockData: AttributeInitialData = {
      id: 1,
      name: "Màu sắc",
      code: "COLOR",
      description: "<p>Mô tả cho màu sắc</p>",
    };
    setInitialData(mockData);
  }, [id]);

  // 3. Cấu hình Fields
  const fields = useMemo(() => [
    { name: "name" as const, label: "Tên thuộc tính" },
    { name: "code" as const, label: "Code" },
    {
      name: "description" as const,
      label: "Mô tả",
      component: () => <>{TinyMCEEditorComponent}</>,
      className: "md:col-span-2",
    },
  ], [TinyMCEEditorComponent]);

  // 4. Validation Schema
  const validationSchema = useMemo(() => [
    { selector: "#name", rules: [{ rule: "required", errorMessage: "Vui lòng nhập tên thuộc tính!" }] },
    { selector: "#code", rules: [{ rule: "required", errorMessage: "Vui lòng nhập code!" }] },
  ], []);

  // 5. Handle Submit
  const handleSubmit = (data: AttributeFormValues) => {
    console.log("Form submitted", data);
    // Logic gọi API update tại đây...
    toast.success("Cập nhật thuộc tính thành công!");
  };

  // Loading state
  if (!initialData) {
    return (
      <div className="p-10 text-center">
        <span className="loading-text">Đang tải dữ liệu...</span>
      </div>
    );
  }

  // 6. Render clean với FormPageLayout
  return (
    <FormPageLayout
      title="Cập nhật thuộc tính"
      breadcrumbs={[
        { name: "Trang chủ", link: "/" },
        { name: "Thuộc tính", link: "/attribute" },
        { name: "Cập nhật" },
      ]}
      backLink="/attribute"
    >
      <Form<AttributeFormValues>
        id="attribute-update-form"
        fields={fields}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        initialData={initialData}
        submitButtonText="Cập nhật"
      />
    </FormPageLayout>
  );
}