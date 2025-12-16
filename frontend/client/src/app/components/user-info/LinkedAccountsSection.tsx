"use client";
import { FiLink } from "react-icons/fi";

interface SocialAccount {
  name: string;
  icon: string;
}

interface LinkedAccountsSectionProps {
  accounts?: SocialAccount[];
}

const defaultAccounts: SocialAccount[] = [
  { name: "Google", icon: "/assets/images/icon-google.jpg" },
  { name: "Facebook", icon: "/assets/images/icon-facebook.jpg" }
];

export const LinkedAccountsSection = ({ accounts = defaultAccounts }: LinkedAccountsSectionProps) => {
  return (
    <div className="flex-1 bg-[#fff] p-[24px] rounded-[12px]">
      <h2 className="font-[700] text-[18px] text-[#121214] mb-[16px]">
        Tài khoản liên kết
      </h2>

      {accounts.map((account, index) => (
        <div 
          key={index} 
          className={`flex items-center justify-between ${index < accounts.length - 1 ? 'mb-[12px]' : ''}`}
        >
          <div className="flex items-center gap-[12px]">
            <img src={account.icon} alt={account.name} className="w-[24px] h-[24px]" />
            <span className="font-[500] text-[14px] text-[#121214]">
              {account.name}
            </span>
          </div>
          <button className="font-[500] text-[14px] text-[#D70018] flex items-center gap-[4px] cursor-pointer">
            <FiLink className="text-[16px]" />
            Liên kết
          </button>
        </div>
      ))}
    </div>
  );
};

