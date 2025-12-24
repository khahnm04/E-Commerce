"use client";

import React, { useState, useCallback, useEffect } from "react";

interface Option {
  value: string;
  label: string;
}

interface MultiSelectCategoryProps {
  options: Option[];
  onCategoriesChange: (categoryIds: string[]) => void;
  initialSelectedCategoryIds?: string[];
}

const MultiSelectCategory: React.FC<MultiSelectCategoryProps> = ({
  options,
  onCategoriesChange,
  initialSelectedCategoryIds = [],
}) => {
  const [selectedCategoryIds, setSelectedCategoryIds] = useState<string[]>(
    initialSelectedCategoryIds
  );
  const [dropdownValue, setDropdownValue] = useState<string>("");

  useEffect(() => {
    onCategoriesChange(selectedCategoryIds);
  }, [selectedCategoryIds, onCategoriesChange]);

  const handleDropdownChange = useCallback(
    (event: React.ChangeEvent<HTMLSelectElement>) => {
      const value = event.target.value;
      setDropdownValue(value);

      if (value && !selectedCategoryIds.includes(value)) {
        setSelectedCategoryIds((prev) => [...prev, value]);
      }
      // Reset dropdown after selection
      setDropdownValue("");
    },
    [selectedCategoryIds]
  );

  const handleRemoveCategory = useCallback((categoryId: string) => {
    setSelectedCategoryIds((prev) => prev.filter((id) => id !== categoryId));
  }, []);

  const getCategoryLabel = (value: string) => {
    const option = options.find((opt) => opt.value === value);
    return option ? option.label : value;
  };

  const availableOptions = options.filter(
    (opt) =>
      !selectedCategoryIds.includes(opt.value) && opt.value !== "" // Filter out already selected and empty option
  );

  return (
    <div className="space-y-2">
      {/* Dropdown for selection */}
      <select
        id="category-select"
        value={dropdownValue}
        onChange={handleDropdownChange}
        className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] py-[10px] font-[500] text-[14px] text-[var(--color-text)] w-full focus:outline-none focus:ring-2 focus:ring-blue-500 cursor-pointer"
        aria-label="Chọn danh mục"
      >
        <option value="" disabled>
          -- Chọn danh mục --
        </option>
        {availableOptions.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>

      {/* Display selected categories as tags */}
      {selectedCategoryIds.length > 0 && (
        <div className="flex flex-wrap gap-2 mt-2">
          {selectedCategoryIds.map((categoryId) => (
            <span
              key={categoryId}
              className="inline-flex items-center rounded-full bg-blue-100 px-3 py-1 text-sm font-medium text-blue-800"
            >
              {getCategoryLabel(categoryId)}
              <button
                type="button"
                onClick={() => handleRemoveCategory(categoryId)}
                className="ml-2 -mr-1 h-4 w-4 flex items-center justify-center rounded-full text-blue-800 hover:bg-blue-200 focus:outline-none focus:bg-blue-200"
                aria-label={`Xóa danh mục ${getCategoryLabel(categoryId)}`}
              >
                <svg
                  className="h-3 w-3"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M6 18L18 6M6 6l12 12"
                  ></path>
                </svg>
              </button>
            </span>
          ))}
        </div>
      )}
    </div>
  );
};

export default MultiSelectCategory;
