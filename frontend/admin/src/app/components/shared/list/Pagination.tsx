interface PaginationProps {
  currentPage: number; // pageNo từ API
  totalPages: number;
  totalElements: number;
  pageSize?: number;
  onPageChange: (page: number) => void;
}

export const Pagination = ({
  currentPage = 1,
  totalPages = 1,
  totalElements = 0,
  pageSize = 10,
  onPageChange,
}: PaginationProps) => {
  // Tính toán vị trí bản ghi đang hiển thị
  const startRecord = totalElements === 0 ? 0 : (currentPage - 1) * pageSize + 1;
  const endRecord = Math.min(currentPage * pageSize, totalElements);

  const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);

  return (
    <div className="flex items-center gap-[20px] mt-4">
      {/* Thông tin hiển thị */}
      <span className="font-[600] text-[14px] text-[var(--color-text)] opacity-[0.6]">
        Hiển thị {startRecord} - {endRecord} của {totalElements}
      </span>

      <div className="flex items-center gap-[20px]">
        {/* Thẻ select để chuyển trang nhanh */}
        <div className="flex items-center gap-[10px]">
          <span className="text-[14px] font-[500] text-gray-500">Đến trang:</span>
          <select
            value={currentPage}
            onChange={(e) => onPageChange(Number(e.target.value))}
            className="border border-[#D5D5D5] rounded-[8px] bg-[#FAFBFD] px-[14px] py-[6px] outline-none font-[600] text-[14px] text-[var(--color-text)] cursor-pointer"
          >
            {pageNumbers.map((page) => (
              <option key={page} value={page}>
                Trang {page}
              </option>
            ))}
          </select>
        </div>

        {/* Nút Điều hướng Trước/Sau */}
        <div className="flex gap-2">
          <button
            disabled={currentPage <= 1}
            onClick={() => onPageChange(currentPage - 1)}
            className="px-4 py-2 border rounded-[8px] bg-white disabled:opacity-30 hover:bg-gray-100 transition-all text-[14px] font-medium"
          >
            Trước
          </button>
          <button
            disabled={currentPage >= totalPages}
            onClick={() => onPageChange(currentPage + 1)}
            className="px-4 py-2 border rounded-[8px] bg-white disabled:opacity-30 hover:bg-gray-100 transition-all text-[14px] font-medium"
          >
            Sau
          </button>
        </div>
      </div>
    </div>
  );
};