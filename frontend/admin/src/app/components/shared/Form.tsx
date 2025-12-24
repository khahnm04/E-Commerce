import JustValidate from "just-validate";
import { useEffect, ElementType, ReactNode } from "react";

// 1. Định nghĩa Generic cho Field
// T là kiểu dữ liệu của toàn bộ Form (ví dụ: CategoryFormValues)
interface Field<T> {
  name: keyof T & string; // name BẮT BUỘC phải là một key trong T (VD: "name" | "status")
  label: string;
  type?: "text" | "password" | "email" | "number" | "select" | "date"; // Giới hạn các type cho phép
  component?: ElementType; // Cho phép truyền custom component (FilePond, TinyMCE...)
  props?: Record<string, any>; // Các props phụ cho input/component
  options?: { value: string | number; label: string }[];
  className?: string;
}

// 2. Định nghĩa Generic cho FormProps
interface FormProps<T> {
  id: string;
  fields: Field<T>[]; // Mảng các field dựa trên T
  onSubmit: (data: T) => void; // Data trả về sẽ có kiểu T, không phải any
  initialData?: Partial<T>; // Dữ liệu khởi tạo có thể thiếu một vài trường
  validationSchema: {
    selector: string;
    rules: { rule: string; errorMessage?: string; validator?: (value: any) => boolean }[];
  }[];
  submitButtonText: string;
}

// 3. Component Form Generic
export const Form = <T extends Record<string, any>>({
  id,
  fields,
  onSubmit,
  initialData,
  validationSchema,
  submitButtonText,
}: FormProps<T>) => {
  useEffect(() => {
    // JustValidate logic giữ nguyên, nhưng ta wrap lại để đảm bảo type safety bên ngoài
    const validator = new JustValidate(`#${id}`, {
      errorLabelStyle: {
        position: "absolute",
        top: "102%",
        left: "0",
        width: "100%",
        fontSize: "12px",
        color: "#e63946",
      },
    });

    validationSchema.forEach((rule) => {
      validator.addField(rule.selector, rule.rules);
    });

    validator.onSuccess((event: Event) => {
      // event.preventDefault() được xử lý bởi JustValidate, nhưng ta double check
      if (event.target instanceof HTMLFormElement) {
        const formData = new FormData(event.target);
        // Lưu ý: FormData mặc định trả về string/File. 
        // Trong dự án thực tế nên dùng Zod để parse lại data cho đúng kiểu number/boolean.
        // Ở đây ta ép kiểu as T để TypeScript hài lòng.
        const data = Object.fromEntries(formData.entries()) as unknown as T;
        onSubmit(data);
      }
    });

    return () => {
      validator.destroy();
    };
  }, [id, validationSchema, onSubmit]);

  return (
    <form id={id} className="grid md:grid-cols-2 grid-cols-1 gap-[30px]">
      {fields.map((field) => (
        <div
          key={field.name}
          className={`inner-group relative ${field.className || ""}`}
        >
          <label
            className="inner-label block font-[600] text-[14px] text-[#606060] mb-[11px]"
            htmlFor={field.name}
          >
            {field.label}
          </label>

          {/* Xử lý render Field */}
          {field.component ? (
            // Render Custom Component (FilePond, Editor...)
            <field.component
              id={field.name}
              name={field.name}
              {...field.props}
              defaultValue={initialData?.[field.name]}
            />
          ) : field.type === "select" ? (
            <select
              name={field.name}
              id={field.name}
              className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] font-[500] text-[14px] text-[var(--color-text)] w-full h-[52px] focus:outline-none focus:ring-2 focus:ring-blue-500"
              defaultValue={initialData?.[field.name] as string || ""}
              {...field.props}
            >
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
              className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] font-[500] text-[14px] text-[var(--color-text)] w-full h-[52px] focus:outline-none focus:ring-2 focus:ring-blue-500"
              defaultValue={initialData?.[field.name] as string || ""}
              {...field.props}
            />
          )}
        </div>
      ))}
      <div className="inner-button inner-two-col md:col-span-2 col-span-1 text-center">
        <button type="submit" className="w-[274px] h-[56px] bg-[var(--color-primary)] rounded-[12px] font-[700] text-[18px] text-white opacity-[0.9] border-0 cursor-pointer hover:opacity-[1]">
          {submitButtonText}
        </button>
      </div>
    </form>
  );
};