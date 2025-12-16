"use client";
import { useRouter } from "next/navigation";

interface LogoutModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export const LogoutModal = ({ isOpen, onClose }: LogoutModalProps) => {
  const router = useRouter();

  if (!isOpen) return null;

  const handleLogout = () => {
    fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/logout`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include"
    })
      .then(res => res.json())
      .then(data => {
        console.log(data);
        if (!data.success) {
          alert(data.message || "Đăng xuất thất bại");
        }
        if (data.success) {
          localStorage.removeItem('userFullName');
          window.dispatchEvent(new Event('userUpdated'));
          onClose();
          router.push("/login");
        }
      })
      .catch(error => {
        console.error("Error:", error);
        alert("Có lỗi xảy ra khi đăng xuất");
      });
  };

  return (
    <div 
      className="fixed inset-0 bg-[rgba(0,0,0,0.5)] flex items-center justify-center z-50"
      onClick={onClose}
    >
      <div 
        className="bg-white rounded-[12px] p-[24px] w-[400px] shadow-lg"
        onClick={(e) => e.stopPropagation()}
      >
        <h2 className="font-[700] text-[18px] text-[#121214] mb-[16px]">
          Đăng xuất
        </h2>
        <p className="font-[400] text-[14px] text-[#121214] mb-[24px]">
          Bạn có chắc chắn muốn đăng xuất?
        </p>
        <div className="flex gap-[12px] justify-end">
          <button
            onClick={handleLogout}
            className="px-[24px] py-[10px] border border-[#E4E4E7] rounded-[8px] font-[500] text-[14px] text-[#121214] hover:bg-[#F5F5F5] transition-colors cursor-pointer"
          >
            Đăng xuất
          </button>
          <button
            onClick={onClose}
            className="px-[24px] py-[10px] bg-[#D70018] rounded-[8px] font-[500] text-[14px] text-white hover:bg-[#B80015] transition-colors cursor-pointer"
          >
            Ở lại trang
          </button>
        </div>
      </div>
    </div>
  );
};

