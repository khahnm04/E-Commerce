"use client";
import { FaPlus } from "react-icons/fa6";
import { AddressCard } from "./AddressCard";
import { Address } from "./types";

interface AddressSectionProps {
  addresses: Address[];
  onShowAll: () => void;
  onDelete?: (index: number) => void;
  onUpdate?: (index: number) => void;
}

export const AddressSection = ({ 
  addresses, 
  onShowAll,
  onDelete,
  onUpdate 
}: AddressSectionProps) => {
  return (
    <div className="bg-[#fff] p-[24px] rounded-[12px]">
      <div className="flex justify-between items-center mb-[24px]">
        <h2 className="font-[700] text-[18px] text-[#121214]">
          Sổ địa chỉ
        </h2>
        <button className="font-[500] text-[14px] text-[#D70018] flex items-center gap-[4px] cursor-pointer">
          <FaPlus className="text-[14px]" />
          Thêm địa chỉ
        </button>
      </div>

      {/* Hiển thị 2 địa chỉ nằm ngang */}
      <div className="grid grid-cols-2 gap-[12px] mb-[16px]">
        {addresses.slice(0, 2).map((addr, index) => (
          <AddressCard
            key={index}
            address={addr}
            onDelete={() => onDelete?.(index)}
            onUpdate={() => onUpdate?.(index)}
            showLineClamp={true}
          />
        ))}
      </div>

      {addresses.length > 2 && (
        <div className="flex justify-center">
          <button
            onClick={onShowAll}
            className="font-[500] text-[14px] text-[#D70018] hover:underline"
          >
            Xem tất cả ({addresses.length} địa chỉ)
          </button>
        </div>
      )}
    </div>
  );
};

