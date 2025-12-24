"use client";

import React from "react";
import AttributeInputs, { Attribute } from "./AttributeInputs";
import MultiSelectCategory from "./MultiSelectCategory";

interface Option {
  value: string;
  label: string;
}

interface AttributeCategoryLayoutProps {
  onAttributesChange: (attributes: Attribute[]) => void;
  onCategoriesChange: (categoryIds: string[]) => void;
  categoryOptions: Option[];
  initialAttributes?: Attribute[];
  initialSelectedCategoryIds?: string[];
}

const AttributeCategoryLayout: React.FC<AttributeCategoryLayoutProps> = ({
  onAttributesChange,
  onCategoriesChange,
  categoryOptions,
  initialAttributes,
  initialSelectedCategoryIds,
}) => {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
      {/* Product Attributes Section (Left Column) */}
      <div>
        <label className="block text-gray-700 text-sm mb-2">
          Thuộc tính sản phẩm
        </label>
        <AttributeInputs
          onAttributesChange={onAttributesChange}
          initialAttributes={initialAttributes}
        />
      </div>

      {/* Category Section (Right Column) */}
      <div>
        <label className="block text-gray-700 text-sm mb-2">
          Danh mục
        </label>
        <MultiSelectCategory
          options={categoryOptions}
          onCategoriesChange={onCategoriesChange}
          initialSelectedCategoryIds={initialSelectedCategoryIds}
        />
      </div>
    </div>
  );
};

export default AttributeCategoryLayout;
