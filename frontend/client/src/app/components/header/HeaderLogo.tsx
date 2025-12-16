"use client";
import { useRouter } from 'next/navigation';

export const HeaderLogo = () => {
  const router = useRouter();
  
  return (
    <div 
      className="w-[161px] h-[27px] cursor-pointer"
      onClick={() => router.push('/')}
    >
      <img src="/assets/images/logo.svg" alt="logo" />
    </div>
  );
};

