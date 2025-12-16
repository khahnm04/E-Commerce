"use client"
import { Header } from "../components/header/Header";

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="flex flex-col min-h-screen">
      {/* Header */}
      <Header />
      {/* End Header */}

      <div className="bg-[#e4e4e6] flex-1">
        {children}
      </div>
    </div>
  );
}