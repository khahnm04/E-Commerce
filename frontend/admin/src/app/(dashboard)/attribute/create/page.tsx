"use client";

import { useMemo } from "react";
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

export default function CreateAttributePage() {
  const { TinyMCEEditorComponent } = useTinyMCE();

  // 2. Cấu hình Fields
  const fields = useMemo(() => [
    {
      name: "name" as const,
      label: "Tên thuộc tính"
    },
    {
      name: "code" as const,
      label: "Code"
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
    { selector: "#name", rules: [{ rule: "required", errorMessage: "Vui lòng nhập tên thuộc tính!" }] },
    { selector: "#code", rules: [{ rule: "required", errorMessage: "Vui lòng nhập code!" }] },
  ], []);

  // 4. Handle Submit
  const handleSubmit = (data: AttributeFormValues) => {
    console.log("Form submitted", data);
    // Call API createAttribute(data) here
    toast.success("Tạo thuộc tính thành công!");
  };

  // 5. Render clean với FormPageLayout
  return (
    <FormPageLayout
      title="Tạo thuộc tính"
      breadcrumbs={[
        { name: "Trang chủ", link: "/" },
        { name: "Thuộc tính", link: "/attribute" },
        { name: "Tạo mới" },
      ]}
      backLink="/attribute"
    >
      <Form<AttributeFormValues>
        id="attribute-create-form"
        fields={fields}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        submitButtonText="Tạo mới"
      />
    </FormPageLayout>
  );
}