"use client";
import { BsPencilSquare } from "react-icons/bs";
import { InfoRow } from "./InfoRow";
import { InfoItem } from "./types";

interface PersonalInfoSectionProps {
  personalInfo: InfoItem[];
  contactInfo: InfoItem[];
}

export const PersonalInfoSection = ({ personalInfo, contactInfo }: PersonalInfoSectionProps) => {
  return (
    <div className="bg-[#fff] p-[24px] rounded-[12px]">
      <div className="flex justify-between items-center mb-[24px]">
        <h2 className="font-[700] text-[18px] text-[#121214]">
          Thông tin cá nhân
        </h2>
        <button className="font-[500] text-[14px] text-[#D70018] flex items-center gap-[4px] cursor-pointer">
          <BsPencilSquare className="text-[16px]" />
          Cập nhật
        </button>
      </div>

      <div className="grid grid-cols-2 gap-x-[48px]">
        <div>
          {personalInfo.map((info, index) => (
            <InfoRow key={index} label={info.label} value={info.value} />
          ))}
        </div>
        <div>
          {contactInfo.map((info, index) => (
            <InfoRow key={index} label={info.label} value={info.value} />
          ))}
        </div>
      </div>
    </div>
  );
};

