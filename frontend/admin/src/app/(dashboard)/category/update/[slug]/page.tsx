/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @next/next/no-img-element */
/* eslint-disable @typescript-eslint/no-explicit-any */
"use client";

import { useEffect, useState, useMemo, useRef } from "react";
import { useParams, useRouter } from "next/navigation";
import toast from "react-hot-toast";

// Hooks & Components
import { useTinyMCE } from "@/hooks/useTinyMCE";
import { Form } from "@/app/components/shared/Form";
import { FilePondWrapper } from "@/app/components/forms/FilePondWrapper";
import { FormPageLayout } from "@/app/components/shared/FormPageLayout";
import { categoryService } from "@/services/category.service";
import { CategoryStatus, CategoryRequest } from "@/models/category.model";
import { FaChevronDown } from "react-icons/fa";

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
            setSearchTerm(e.target.value);
            setIsOpen(true);
            onChange?.(e);
          }}
          className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] pr-[40px] font-[500] text-[14px] text-[var(--color-text)] w-full h-[52px] focus:outline-none"
        />
        <FaChevronDown className={`absolute right-4 top-1/2 -translate-y-1/2 text-gray-400 transition-transform ${isOpen ? "rotate-180" : ""}`} />
      </div>
      {isOpen && (
        <div className="absolute z-50 w-full mt-1 bg-white border border-[#D5D5D5] rounded-[8px] shadow-lg max-h-[250px] overflow-y-auto">
          {filtered.map((opt) => (
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
          ))}
        </div>
      )}
    </div>
  );
};

export default function UpdateCategoryPage() {
  const { slug } = useParams();
  const router = useRouter();
  const { TinyMCEEditorComponent, getContent, setContent } = useTinyMCE();

  const [initialData, setInitialData] = useState<CategoryFormValues | null>(null);
  const [parentOptions, setParentOptions] = useState<{ value: string; label: string }[]>([]);
  const [categoryId, setCategoryId] = useState<number | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [resParents, resDetail] = await Promise.all([
          categoryService.getAll({ page: 1, size: 1000 }),
          categoryService.getBySlug(String(slug))
        ]);

        const options = resParents?.data?.map((cat: any) => ({
          value: String(cat.id),
          label: cat.name,
        })) || [];
        setParentOptions(options);

        if (resDetail?.data) {
          const detail = resDetail.data;
          setCategoryId(detail.id);

          const currentParentLabel = options.find(
            (o: any) => Number(o.value) === detail.parentId
          )?.label || "";

          setInitialData({
            name: detail.name || "",
            slug: detail.slug || "",
            parent: currentParentLabel,
            position: detail.position ?? 0,
            status: detail.status || CategoryStatus.ACTIVE,
            image: detail.image || null,
            description: detail.description || "",
          });

          if (detail.description) {
            setTimeout(() => setContent(detail.description || ""), 500);
          }
        }
      } catch (error) {
        toast.error("Lỗi tải dữ liệu danh mục");
      }
    };
    fetchData();
  }, [slug, setContent]);

  const fields = useMemo(() => [
    { name: "name", label: "Tên danh mục" },
    { name: "slug", label: "Chuỗi định danh URL" },
    {
      name: "parent",
      label: "Danh mục cha",
      className: "relative",
      component: (props: any) => <ParentCategorySelect props={props} options={parentOptions} />,
    },
    { name: "position", label: "Vị trí", type: "number" as const },
    {
      name: "status",
      label: "Trạng thái",
      type: "select" as const,
      options: [
        { value: CategoryStatus.ACTIVE, label: "Hoạt động" },
        { value: CategoryStatus.INACTIVE, label: "Tạm dừng" },
      ],
    },
    {
      name: "image",
      label: "Ảnh đại diện",
      className: "md:col-span-2",
      component: (props: any) => (
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4 items-start">
          <div className="col-span-1">
            <p className="text-[12px] text-gray-500 mb-2 italic">Ảnh hiện tại:</p>
            <div className="border border-dashed border-gray-300 rounded-lg p-2 flex items-center justify-center bg-gray-50 h-[150px]">
              {initialData?.image ? (
                <img
                  src={initialData.image}
                  alt="Current category"
                  className="max-h-full max-w-full object-contain rounded"
                />
              ) : (
                <span className="text-gray-400 text-[12px]">Không có ảnh</span>
              )}
            </div>
          </div>

          <div className="col-span-3">
            <p className="text-[12px] text-gray-500 mb-2 italic">Chọn ảnh thay thế (nếu cần):</p>
            <FilePondWrapper
              id={props.id}
              name={props.name}
              accept="image/*"
              // Đảm bảo onFileChange từ props được truyền vào đây để Form nhận diện được file mới
              onFileChange={props.onFileChange}
            />
          </div>
        </div>
      ),
    },
    {
      name: "description",
      label: "Mô tả chi tiết",
      className: "md:col-span-2",
      component: () => <div className="mt-2">{TinyMCEEditorComponent}</div>,
    },
  ], [TinyMCEEditorComponent, parentOptions, initialData]);

  const handleSubmit = async (formData: CategoryFormValues) => {
    if (!categoryId) return;
    const editorContent = getContent();

    const matchedParent = parentOptions.find(
      (opt) => opt.label.trim().toLowerCase() === (formData.parent || "").trim().toLowerCase()
    );

    const categoryRequest: CategoryRequest = {
      name: formData.name,
      slug: formData.slug,
      parentId: matchedParent ? Number(matchedParent.value) : null,
      position: Number(formData.position),
      status: formData.status,
      description: editorContent,
    };

    let imageFile: File | undefined = undefined;
    if (Array.isArray(formData.image) && formData.image.length > 0) {
      const firstItem = formData.image[0];
      if (firstItem instanceof File) {
        imageFile = firstItem;
      } else if (firstItem?.file instanceof File) {
        imageFile = firstItem.file;
      }
    }

    let loadingId;
    try {
      loadingId = toast.loading("Đang cập nhật...");
      await categoryService.update(categoryId, categoryRequest, imageFile);
      toast.success("Cập nhật thành công!", { id: loadingId });
      router.push("/category");
    } catch (error: any) {
      toast.error(error.message || "Lỗi cập nhật", { id: loadingId });
    }
  };

  if (!initialData) {
    return <div className="p-10 text-center">Đang tải dữ liệu...</div>;
  }

  return (
    <FormPageLayout
      title="Cập nhật danh mục"
      backLink="/category"
      breadcrumbs={[
        { name: "Trang chủ", link: "/" },
        { name: "Danh mục", link: "/category" },
        { name: "Cập nhật" },
      ]}
    >
      <Form<CategoryFormValues>
        id="category-update-form"
        fields={fields as any}
        onSubmit={handleSubmit}
        initialData={initialData}
        validationSchema={[
          { selector: "#name", rules: [{ rule: "required", errorMessage: "Không được để trống tên" }] }
        ]}
        submitButtonText="Lưu thay đổi"
      />
    </FormPageLayout>
  );
}