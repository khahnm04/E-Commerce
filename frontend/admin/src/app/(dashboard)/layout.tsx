"use client"
import { useState } from "react";
import { Toaster } from "react-hot-toast";
import { Header } from "../components/header/Header";
import { Sider } from "../components/sider/Sider";

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {

  const [activeSider, setActiveSider] = useState(false);

  return (
    <>
      <Toaster
        position="top-right"
      />

      {/* Header */}
      <Header
        activeSider={activeSider}
        setActiveSider={setActiveSider}
      />
      {/* End Header */}

      {/* Sider */}
      <Sider
        activeSider={activeSider}
        setActiveSider={setActiveSider}
      />
      {/* End Sider */}

      <div className="absolute top-[70px] lg:left-[240px] left-0 lg:w-[calc(100%-240px)] w-full min-h-[calc(100vh-70px)] 
        bg-[#F4F5F9] py-[30px] sm:px-[30px] px-[15px]">
        {children}
      </div>
    </>
  );
}
