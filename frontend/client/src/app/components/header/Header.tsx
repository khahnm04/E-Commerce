"use client";
import React, { useMemo } from 'react';
import { useRouter } from 'next/navigation';
import { HeaderLogo } from './HeaderLogo';
import { HeaderSearch } from './HeaderSearch';
import { HeaderButton } from './HeaderButton';
import { defaultDropdownItems } from './constants';
import { useUserName } from '@/hooks/useUserName';

export const Header = () => {
  const router = useRouter();
  const userName = useUserName();

  const dropdownItems = defaultDropdownItems;

  // Sử dụng useMemo để actionItems được cập nhật khi userName thay đổi
  const actionItems = useMemo(() => [
    {
      id: 1,
      label: "Giỏ hàng",
      icon: "/assets/images/icon-cart.svg",
      bgClass: "",
      onClick: undefined,
    },
    {
      id: 2,
      label: userName || "Đăng nhập",
      icon: "/assets/images/icon-info.svg",
      bgClass: "bg-[#e45464]",
      onClick: userName ? () => router.push('/user-info') : () => router.push('/login'),
    },
  ], [userName, router]);

  return (
    <>
      {/* Header */}
      <div style={{
        background: "linear-gradient(180deg, #BF4251 0%, #D70018 100%)"
      }}>
        <div className="container py-[16px] flex gap-[12px] justify-between items-center">

          <HeaderLogo />

          {dropdownItems.map((item) => (
            <HeaderButton
              key={item.id}
              label={item.label}
              icon={item.icon}
              type="dropdown"
            />
          ))}

          <HeaderSearch />

          {actionItems.map((item) => (
            <HeaderButton
              key={item.id}
              label={item.label}
              icon={item.icon}
              type="simple"
              className={item.bgClass}
              onClick={item.onClick}
            />
          ))}

        </div>
      </div>
      {/* End Header */}
    </>
  );
}