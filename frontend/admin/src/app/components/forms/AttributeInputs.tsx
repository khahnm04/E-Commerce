"use client";

import React, { useState, useCallback, useEffect } from "react";

export interface Attribute {
  id: string; // Unique ID for keying, can be generated client-side
  name: string;
  value: string;
}

interface AttributeInputsProps {
  initialAttributes?: Attribute[];
  onAttributesChange: (attributes: Attribute[]) => void;
}

const AttributeInputs: React.FC<AttributeInputsProps> = ({
  initialAttributes = [],
  onAttributesChange,
}) => {
  const [attributes, setAttributes] = useState<Attribute[]>(
    initialAttributes.length > 0
      ? initialAttributes
      : [{ id: String(Date.now()), name: "", value: "" }] // Start with one empty attribute if none provided
  );

  useEffect(() => {
    onAttributesChange(attributes);
  }, [attributes, onAttributesChange]);

  const handleAttributeChange = useCallback(
    (index: number, field: keyof Omit<Attribute, "id">, value: string) => {
      setAttributes((prevAttributes) =>
        prevAttributes.map((attr, i) =>
          i === index ? { ...attr, [field]: value } : attr
        )
      );
    },
    []
  );

  const handleAddAttribute = useCallback(() => {
    setAttributes((prevAttributes) => [
      ...prevAttributes,
      { id: String(Date.now()), name: "", value: "" },
    ]);
  }, []);

  const handleRemoveAttribute = useCallback((idToRemove: string) => {
    setAttributes((prevAttributes) =>
      prevAttributes.filter((attr) => attr.id !== idToRemove)
    );
  }, []);

  return (
    <div className="space-y-4">
      {attributes.map((attribute, index) => (
        <div key={attribute.id} className="flex items-center gap-4">
          {/* Attribute Name Input */}
          <input
            type="text"
            placeholder="Tên thuộc tính"
            value={attribute.name}
            onChange={(e) =>
              handleAttributeChange(index, "name", e.target.value)
            }
            className="flex-[2] bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] py-[10px] font-[500] text-[14px] text-[var(--color-text)] focus:outline-none focus:ring-2 focus:ring-blue-500"
          />

          {/* Attribute Value Input */}
          <input
            type="text"
            placeholder="Giá trị"
            value={attribute.value}
            onChange={(e) =>
              handleAttributeChange(index, "value", e.target.value)
            }
            className="flex-[3] bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] py-[10px] font-[500] text-[14px] text-[var(--color-text)] focus:outline-none focus:ring-2 focus:ring-blue-500"
          />

          {/* Delete Button */}
          <button
            type="button"
            onClick={() => handleRemoveAttribute(attribute.id)}
            className="bg-red-500 text-white rounded-md p-2 flex items-center justify-center w-8 h-8 hover:bg-red-700 transition-colors mr-2"
            aria-label="Xóa thuộc tính"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="h-4 w-4"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>
        </div>
      ))}

      {/* Add Attribute Button */}
      <button
        type="button"
        onClick={handleAddAttribute}
        className="bg-blue-500 text-white rounded-md px-4 py-2 hover:bg-blue-700 transition-colors flex items-center gap-2"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-5 w-5"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={2}
            d="M12 4v16m8-8H4"
          />
        </svg>
        Thêm thuộc tính
      </button>
    </div>
  );
};

export default AttributeInputs;
