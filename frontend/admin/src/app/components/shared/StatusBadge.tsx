interface StatusBadgeProps {
  status: string; // "active" | "inactive" hoặc tiếng Việt
  labels?: Record<string, string>; // Map từ key sang label hiển thị (nếu cần)
}

export const StatusBadge = ({ status }: StatusBadgeProps) => {
  const getStatusClass = (s: string) => {
    // Chuẩn hóa input về lower case để so sánh
    const lowerStatus = s.toLowerCase();

    if (lowerStatus === "active" || lowerStatus === "hoạt động") {
      return "bg-[#D1FAE5] text-[#065F46]";
    }
    if (lowerStatus === "inactive" || lowerStatus === "tạm dừng") {
      return "bg-[#FEE2E2] text-[#991B1B]";
    }
    return "bg-gray-200 text-gray-800";
  };

  return (
    <span className={`inline-block px-3 py-1 text-sm font-semibold rounded-[8px] ${getStatusClass(status)}`}>
      {status}
    </span>
  );
};