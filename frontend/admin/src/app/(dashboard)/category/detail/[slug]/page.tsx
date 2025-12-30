/* eslint-disable @next/next/no-img-element */
/* eslint-disable @typescript-eslint/no-explicit-any */
"use client";
import { useState, useEffect, useMemo } from "react";
import { useParams } from "next/navigation";
import { FormPageLayout } from "@/app/components/shared/FormPageLayout";
import { categoryService } from "@/services/category.service";
import { CategoryStatus } from "@/models/category.model";

export default function CategoryDetailPage() {
  const { slug } = useParams();
  const [categoryData, setCategoryData] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDetail = async () => {
      try {
        setLoading(true);
        const response = await categoryService.getBySlug(String(slug));
        if (response && response.data) {
          setCategoryData(response.data);
        }
      } catch (error) {
        console.error("Lỗi fetch chi tiết danh mục theo slug:", error);
      } finally {
        setLoading(false);
      }
    };

    if (slug) fetchDetail();
  }, [slug]);

  // Định nghĩa các trường hiển thị tương tự cấu trúc Form của bạn
  const detailFields = useMemo(() => {
    if (!categoryData) return [];
    return [
      { label: "Tên danh mục", value: categoryData.name },
      { label: "Chuỗi định danh URL (Slug)", value: categoryData.slug },
      { label: "Danh mục cha", value: categoryData.parentName || "Không có" },
      { label: "Vị trí", value: categoryData.position },
      {
        label: "Trạng thái",
        value: categoryData.status === CategoryStatus.ACTIVE ? "Hoạt động" : "Tạm dừng",
        isStatus: true
      },
      {
        label: "Ảnh đại diện",
        value: categoryData.image,
        isImage: true,
        className: "md:col-span-2"
      },
      {
        label: "Mô tả chi tiết",
        value: categoryData.description,
        isHtml: true,
        className: "md:col-span-2"
      },
    ];
  }, [categoryData]);

  // CSS dùng chung khớp với style input trong file Form của bạn
  const infoBoxClass = "bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] font-[500] text-[14px] text-[var(--color-text)] w-full h-[52px] flex items-center";

  if (loading) return <div className="p-10 text-center">Đang tải dữ liệu...</div>;
  if (!categoryData) return <div className="p-10 text-center">Không tìm thấy dữ liệu danh mục!</div>;

  return (
    <FormPageLayout
      title="Chi tiết danh mục"
      backLink="/category"
      breadcrumbs={[
        { name: "Trang chủ", link: "/" },
        { name: "Danh mục", link: "/category" },
        { name: "Chi tiết" },
      ]}
    >
      <div className="grid md:grid-cols-2 grid-cols-1 gap-[30px]">
        {detailFields.map((field, index) => (
          <div key={index} className={`relative ${field.className || ""}`}>
            <label className="block font-[600] text-[14px] text-[#606060] mb-[11px]">
              {field.label}
            </label>

            {field.isImage ? (
              <div className="border-2 border-dashed border-[#D5D5D5] rounded-[10px] p-6 flex justify-center items-center bg-[#F9F9F9]">
                {field.value ? (
                  <div className="text-center">
                    <img
                      src={field.value}
                      alt="Preview"
                      className="max-h-[250px] rounded-[8px] object-contain shadow-sm"
                    />
                  </div>
                ) : (
                  <span className="text-gray-400 italic">Không có ảnh đại diện</span>
                )}
              </div>
            ) : field.isHtml ? (
              <div
                className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] p-[22px] min-h-[150px] text-[14px] prose max-w-none text-[#374151]"
                dangerouslySetInnerHTML={{ __html: field.value || "<i>Không có mô tả</i>" }}
              />
            ) : field.isStatus ? (
              <div className="flex items-center h-[52px]">
                <span
                  className={`
                    flex items-center gap-2 px-3 py-1.5 rounded-[6px] text-[13px] font-[600] 
                    ${categoryData.status === CategoryStatus.ACTIVE
                      ? "bg-[#D1FAE5] text-[#065F46] border border-[#A7F3D0]"
                      : "bg-[#FEE2E2] text-[#991B1B] border border-[#FECACA]"
                    }
                  `}
                >
                  {/* Thêm một dấu chấm tròn nhỏ để nhìn chuyên nghiệp hơn */}
                  <span className={`w-2 h-2 rounded-full ${categoryData.status === CategoryStatus.ACTIVE ? "bg-[#059669]" : "bg-[#DC2626]"}`} />

                  {field.value}
                </span>
              </div>
            ) : (
              <div className={infoBoxClass}>
                {field.value}
              </div>
            )}
          </div>
        ))}
      </div>
    </FormPageLayout>
  );
}