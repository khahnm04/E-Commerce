"use client";
import { AddressCard } from "./AddressCard";
import { Address } from "./types";

interface AllAddressesModalProps {
  isOpen: boolean;
  addresses: Address[];
  onClose: () => void;
  onDelete?: (index: number) => void;
  onUpdate?: (index: number) => void;
}

export const AllAddressesModal = ({ 
  isOpen, 
  addresses, 
  onClose,
  onDelete,
  onUpdate 
}: AllAddressesModalProps) => {
  if (!isOpen) return null;

  return (
    <div 
      className="fixed inset-0 bg-[rgba(0,0,0,0.5)] flex items-center justify-center z-50 p-[16px]"
      onClick={onClose}
    >
      <div 
        className="bg-white rounded-[12px] p-[24px] w-full max-w-[1000px] max-h-[80vh] overflow-y-auto shadow-lg"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex justify-between items-center mb-[24px]">
          <h2 className="font-[700] text-[20px] text-[#121214]">
            Tất cả địa chỉ ({addresses.length})
          </h2>
          <button
            onClick={onClose}
            className="text-[#71717A] hover:text-[#121214] text-[24px] leading-none"
          >
            ×
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-[12px]">
          {addresses.map((addr, index) => (
            <AddressCard
              key={index}
              address={addr}
              onDelete={() => onDelete?.(index)}
              onUpdate={() => onUpdate?.(index)}
              showLineClamp={false}
            />
          ))}
        </div>
      </div>
    </div>
  );
};
