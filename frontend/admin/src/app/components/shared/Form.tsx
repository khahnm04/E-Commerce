/* eslint-disable @typescript-eslint/no-explicit-any */
"use client";

import JustValidate from "just-validate";
import { useEffect, ElementType, useRef, useState } from "react";

interface Field<T> {
  name: keyof T & string;
  label: string;
  type?: "text" | "password" | "email" | "number" | "select" | "date";
  component?: ElementType;
  props?: Record<string, any>;
  options?: { value: string | number; label: string }[];
  className?: string;
}

interface FormProps<T> {
  id: string;
  fields: Field<T>[];
  onSubmit: (data: T) => void;
  initialData?: Partial<T>; // Dùng cho trường hợp Update
  validationSchema: {
    selector: string;
    rules: { rule: string; errorMessage?: string; validator?: (value: any) => boolean }[];
  }[];
  submitButtonText: string;
}

export const Form = <T extends Record<string, any>>({
  id,
  fields,
  onSubmit,
  initialData,
  validationSchema,
  submitButtonText,
}: FormProps<T>) => {
  const formRef = useRef<HTMLFormElement>(null);

  // 1. Quản lý state cho các component cần can thiệp sâu (như File, Editor)
  const [extraData, setExtraData] = useState<Record<string, any>>({});

  // 2. State để theo dõi giá trị các field thông thường (Controlled) 
  // nhằm mục đích Update dữ liệu khi initialData thay đổi từ API
  const [formValues, setFormValues] = useState<Record<string, any>>({});

  useEffect(() => {
    if (initialData) {
      setFormValues(initialData);
    }
  }, [initialData]);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormValues(prev => ({ ...prev, [name]: value }));
  };

  useEffect(() => {
    if (!formRef.current) return;

    const validator = new JustValidate(`#${id}`, {
      errorLabelStyle: {
        position: "absolute",
        top: "102%",
        left: "0",
        fontSize: "12px",
        color: "#e63946",
      },
    });

    validationSchema.forEach((rule) => {
      validator.addField(rule.selector, rule.rules);
    });

    validator.onSuccess(() => {
      if (formRef.current) {
        // Lấy dữ liệu từ DOM Form
        const formData = new FormData(formRef.current);
        const baseData = Object.fromEntries(formData.entries());

        // Hợp nhất dữ liệu: Initial < DOM Form < State (formValues) < Extra (Files/Editor)
        const finalData = {
          ...initialData,
          ...baseData,
          ...formValues,
          ...extraData
        } as T;

        // Ép kiểu number cho các field cần thiết
        fields.forEach(field => {
          if (field.type === "number" && finalData[field.name]) {
            (finalData as any)[field.name] = Number(finalData[field.name]);
          }
        });

        onSubmit(finalData);
      }
    });

    return () => {
      validator.destroy();
    };
  }, [id, validationSchema, onSubmit, extraData, fields, initialData, formValues]);

  return (
    <form id={id} ref={formRef} className="grid md:grid-cols-2 grid-cols-1 gap-[30px]">
      {fields.map((field) => (
        <div key={field.name} className={`inner-group relative ${field.className || ""}`}>
          <label
            className="inner-label block font-[600] text-[14px] text-[#606060] mb-[11px]"
            htmlFor={field.name}
          >
            {field.label}
          </label>

          {field.component ? (
            <field.component
              id={field.name}
              name={field.name}
              {...field.props}
              // Truyền value để component con biết dữ liệu ban đầu
              value={formValues[field.name] || ""}
              onChange={(e: any) => {
                // Hỗ trợ cả sự kiện chuẩn và giá trị trực tiếp từ custom component
                const val = e?.target ? e.target.value : e;
                setFormValues(prev => ({ ...prev, [field.name]: val }));
              }}
              onFileChange={(file: any) => {
                setExtraData(prev => ({ ...prev, [field.name]: file }));
              }}
            />
          ) : field.type === "select" ? (
            <select
              name={field.name}
              id={field.name}
              className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] w-full h-[52px] focus:outline-none focus:border-[var(--color-primary)]"
              value={formValues[field.name] || ""}
              onChange={handleInputChange}
              {...field.props}
            >
              <option value="">-- Chọn {field.label} --</option>
              {field.options?.map((option) => (
                <option key={option.value} value={option.value}>
                  {option.label}
                </option>
              ))}
            </select>
          ) : (
            <input
              type={field.type || "text"}
              name={field.name}
              id={field.name}
              className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] w-full h-[52px] focus:outline-none focus:border-[var(--color-primary)]"
              value={formValues[field.name] || ""}
              onChange={handleInputChange}
              {...field.props}
            />
          )}
        </div>
      ))}

      <div className="md:col-span-2 text-center mt-[10px]">
        <button
          type="submit"
          className="w-[274px] h-[56px] bg-[var(--color-primary)] rounded-[12px] font-[700] text-[18px] text-white opacity-[0.9] hover:opacity-[1] transition-all cursor-pointer border-none"
        >
          {submitButtonText}
        </button>
      </div>
    </form>
  );
};