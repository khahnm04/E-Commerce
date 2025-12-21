"use client"
import Footer from "../components/footer/Footer";
import { Header } from "../components/header/Header";

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="flex flex-col min-h-screen">
      {/* Header */}
      <div className="sticky top-0 z-50">
        <Header />
      </div>
      {/* End Header */}

      <div className="bg-[#e4e4e6] flex-1">
        {children}
      </div>

      {/* Footer */}
      <Footer />
      {/* End Footer */}
    </div>
  );
}