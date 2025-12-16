"use client";
import { useState } from "react";
import { FaEye, FaEyeSlash } from "react-icons/fa6";

export const InputField = (props: {
  label: string;
  type: string;
  id: string;
  placeholder: string;
  icon?: string;
  max?: string;
  min?: string;
}) => {
  const { label, type, id, placeholder, icon, max, min } = props;
  const [showPassword, setShowPassword] = useState(false);

  // Xác định type thực tế của input
  const inputType = type === "password" && showPassword ? "text" : type;

  // Toggle hiển thị mật khẩu
  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <div className="flex flex-col gap-2 relative w-full">
      <label
        className="font-medium text-sm text-[#121214] cursor-pointer"
        htmlFor={id}
      >
        {label}
      </label>

      <div className="group border border-[#CFCFD3] rounded-lg flex items-center px-4 py-3 transition-all duration-200 focus-within:border-[#374151] focus-within:ring-1 focus-within:ring-[#374151] bg-white hover:border-[#374151]">
        <input
          type={inputType}
          id={id}
          name={id}
          placeholder={placeholder}
          max={max}
          min={min}
          className="flex-1 w-full text-base outline-none border-none bg-transparent text-[#121214] placeholder:font-normal placeholder:text-[#CFCFD3]"
        />

        {type === "password" ? (
          <button
            type="button"
            onClick={togglePasswordVisibility}
            className="ml-2 cursor-pointer opacity-60 hover:opacity-100 transition-opacity"
          >
            {showPassword ? (
              <FaEyeSlash className="w-5 h-5" />
            ) : (
              <FaEye className="w-5 h-5" />
            )}
          </button>
        ) : (
          icon && (
            <img
              src={icon}
              className="w-5 h-5 ml-2 cursor-pointer opacity-60 hover:opacity-100 transition-opacity"
              alt="icon"
            />
          )
        )}
      </div>
    </div>
  );
};