import { TiHomeOutline } from "react-icons/ti";
import { Address } from "./types";

interface AddressCardProps {
  address: Address;
  onDelete?: () => void;
  onUpdate?: () => void;
  className?: string;
  showLineClamp?: boolean;
}

export const AddressCard = ({ 
  address, 
  onDelete, 
  onUpdate, 
  className = "",
  showLineClamp = true
}: AddressCardProps) => (
  <div className={`border border-[#E4E4E7] rounded-[8px] p-[16px] bg-white ${className}`}>
    {/* Header */}
    <div className="flex items-center justify-between mb-[12px]">
      <span className="font-[400] text-[14px] text-[#121214]">
        địa chỉ riêng
      </span>
      <div className="flex gap-[8px]">
        <span className="px-[8px] py-[2px] bg-[#F5F5F5] rounded-[4px] font-[400] text-[12px] text-[#71717A] flex items-center justify-center gap-[4px]">
          <TiHomeOutline /> {address.type}
        </span>
        {address.isDefault && (
          <span className="px-[8px] py-[2px] bg-[#E3F2FD] text-[#2196F3] rounded-[4px] font-[500] text-[12px]">
            Mặc định
          </span>
        )}
      </div>
    </div>

    {/* Contact Info */}
    <div className="mb-[8px]">
      <span className="font-[500] text-[14px] text-[#121214]">
        {address.name}
      </span>
      <span className="mx-[8px] text-[#71717A]">|</span>
      <span className="font-[400] text-[14px] text-[#71717A]">
        {address.phone}
      </span>
    </div>

    {/* Address */}
    <div className={`font-[400] text-[14px] text-[#71717A] ${showLineClamp ? 'line-clamp.mb-[16px]' : 'mb-[16px]'}`}>
      {address.address}
    </div>

    {/* Divider */}
    <div className="border-t border-[#E4E4E7] my-[12px]"></div>

    {/* Actions */}
    <div className="flex gap-[16px] justify-end">
      <button 
        onClick={onDelete}
        className="font-[500] text-[14px] text-[#71717A] cursor-pointer hover:text-[#DC2626]"
      >
        Xóa
      </button>
      <button 
        onClick={onUpdate}
        className="font-[500] text-[14px] text-[#2196F3] cursor-pointer hover:text-[#1976D2]"
      >
        Cập nhật
      </button>
    </div>
  </div>
);