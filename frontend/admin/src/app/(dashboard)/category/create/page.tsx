/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-explicit-any */
"use client";
import { useMemo, useState, useEffect, useRef } from "react";
import toast from "react-hot-toast";
import { useRouter } from "next/navigation";
import { useTinyMCE } from "@/hooks/useTinyMCE";
import { Form } from "@/app/components/shared/Form";
import { FilePondWrapper } from "@/app/components/forms/FilePondWrapper";
import { FormPageLayout } from "@/app/components/shared/FormPageLayout";
import { categoryService } from "@/services/category.service";
import { CategoryStatus, CategoryRequest } from "@/models/category.model";
import { FaChevronDown } from "react-icons/fa";

// Interface cho dữ liệu Form trên UI
interface CategoryFormValues {
  name: string;
  slug: string;
  parent: string;
  position: number | string;
  status: CategoryStatus;
  image: any;
  description?: string;
}

// --- COMPONENT CON: ParentCategorySelect ---
const ParentCategorySelect = ({ props, options }: { props: any; options: any[] }) => {
  const { onFileChange, value, onChange, ...validProps } = props;

  const [searchTerm, setSearchTerm] = useState(value || "");
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (value !== undefined) setSearchTerm(value);
  }, [value]);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setIsOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const filtered = options.filter((opt) =>
    opt.label.toLowerCase().includes(String(searchTerm).toLowerCase())
  );

  return (
    <div className="relative w-full" ref={dropdownRef}>
      <div className="relative">
        <input
          {...validProps}
          type="text"
          value={searchTerm}
          placeholder="Tìm hoặc chọn danh mục cha..."
          onFocus={() => setIsOpen(true)}
          onChange={(e) => {
            const val = e.target.value;
            setSearchTerm(val);
            setIsOpen(true);
            onChange?.(e);
          }}
          className="
            bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] 
            px-[22px] pr-[40px] font-[500] text-[14px] text-[var(--color-text)] 
            w-full h-[52px] focus:outline-none focus:ring-2 focus:ring-blue-500
          "
        />
        <FaChevronDown
          className={`
            absolute right-4 top-1/2 -translate-y-1/2 text-gray-400 
            transition-transform duration-200 ${isOpen ? "rotate-180" : ""}
          `}
        />
      </div>

      {isOpen && (
        <div className="absolute z-50 w-full mt-1 bg-white border border-[#D5D5D5] rounded-[8px] shadow-lg max-h-[250px] overflow-y-auto">
          {filtered.length > 0 ? (
            filtered.map((opt) => (
              <div
                key={opt.value}
                onClick={() => {
                  setSearchTerm(opt.label);
                  setIsOpen(false);
                  onChange?.({ target: { name: validProps.name, value: opt.label } });
                }}
                className="px-[22px] py-3 cursor-pointer hover:bg-blue-50 text-[14px] font-[500] text-[#606060] border-b border-gray-50 last:border-none transition-colors"
              >
                {opt.label}
              </div>
            ))
          ) : (
            <div className="px-[22px] py-3 text-gray-400 text-[14px] italic">
              Không tìm thấy danh mục...
            </div>
          )}
        </div>
      )}
    </div>
  );
};

// --- COMPONENT CHÍNH ---
export default function CreateCategoryPage() {
  const { TinyMCEEditorComponent, getContent } = useTinyMCE();
  const router = useRouter();
  const [parentOptions, setParentOptions] = useState<{ value: string; label: string }[]>([]);

  useEffect(() => {
    const fetchParents = async () => {
      try {
        const response = await categoryService.getAll({ page: 1, size: 1000 });
        if (response && response.data) {
          const options = response.data.map((cat: any) => ({
            value: String(cat.id),
            label: cat.name,
          }));
          setParentOptions(options);
        }
      } catch (error) {
        console.error("Lỗi fetch danh mục:", error);
      }
    };
    fetchParents();
  }, []);

  const fields = useMemo(
    () => [
      { name: "name" as keyof CategoryFormValues, label: "Tên danh mục" },
      { name: "slug" as keyof CategoryFormValues, label: "Chuỗi định danh URL", props: { placeholder: "Bỏ trống để tự động tạo" } },
      {
        name: "parent" as keyof CategoryFormValues,
        label: "Danh mục cha",
        className: "relative",
        component: (props: any) => <ParentCategorySelect props={props} options={parentOptions} />,
      },
      { name: "position" as keyof CategoryFormValues, label: "Vị trí", type: "number" as const },
      {
        name: "status" as keyof CategoryFormValues,
        label: "Trạng thái",
        type: "select" as const,
        options: [
          { value: CategoryStatus.ACTIVE, label: "Hoạt động" },
          { value: CategoryStatus.INACTIVE, label: "Tạm dừng" },
        ],
      },
      {
        name: "image" as keyof CategoryFormValues,
        label: "Ảnh đại diện",
        component: FilePondWrapper,
        className: "md:col-span-2",
        props: { id: "image", name: "image", accept: "image/*" },
      },
      {
        name: "description" as keyof CategoryFormValues,
        label: "Mô tả chi tiết",
        className: "md:col-span-2",
        component: () => <>{TinyMCEEditorComponent}</>,
      },
    ],
    [TinyMCEEditorComponent, parentOptions]
  );

  const handleSubmit = async (formData: CategoryFormValues) => {
    const editorContent = getContent();

    // 1. Tìm ID chính xác từ danh sách parentOptions dựa trên Label
    const matchedParent = parentOptions.find(
      (opt) => opt.label.trim().toLowerCase() === (formData.parent || "").trim().toLowerCase()
    );

    const parentId = matchedParent ? Number(matchedParent.value) : null;

    // 2. Chuẩn bị Request data
    const categoryRequest: CategoryRequest = {
      name: formData.name,
      slug: formData.slug || "",
      parentId: parentId,
      position: formData.position ? Number(formData.position) : 0,
      status: formData.status,
      description: editorContent,
    };

    // 3. Xử lý Image File từ FilePond
    if (!formData.image || (Array.isArray(formData.image) && formData.image.length === 0)) {
      toast.error("Vui lòng tải lên ảnh đại diện!");
      return;
    }

    const imageFile = Array.isArray(formData.image) ? formData.image[0] : formData.image;

    let loadingId;
    try {
      loadingId = toast.loading("Đang xử lý dữ liệu...");
      // Gọi service create với cấu trúc mới
      await categoryService.create(categoryRequest, imageFile);
      toast.success("Tạo danh mục thành công!", { id: loadingId });
      setTimeout(() => router.push("/category"), 800);
    } catch (error: any) {
      toast.error(error.message || "Đã xảy ra lỗi", { id: loadingId });
    }
  };

  return (
    <FormPageLayout
      title="Tạo danh mục"
      backLink="/category"
      breadcrumbs={[
        { name: "Trang chủ", link: "/" },
        { name: "Danh mục", link: "/category" },
        { name: "Tạo mới" },
      ]}
    >
      <Form<CategoryFormValues>
        id="category-create-form"
        fields={fields as any}
        onSubmit={handleSubmit}
        validationSchema={[
          {
            selector: "#name",
            rules: [{ rule: "required", errorMessage: "Tên danh mục không được để trống!" }],
          },
        ]}
        submitButtonText="Tạo danh mục"
      />
    </FormPageLayout>
  );
}