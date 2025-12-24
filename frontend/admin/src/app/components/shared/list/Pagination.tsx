interface PaginationProps {
  current?: number;
  total?: number;
  pageSize?: number;
}

export const Pagination = ({ current = 1, total = 0, pageSize = 10 }: PaginationProps) => (
  <div className="flex items-center gap-[20px]">
    <span className="font-[600] text-[14px] text-[var(--color-text)] opacity-[0.6]">
      Hiển thị 1 - {pageSize < total ? pageSize : total} của {total}
    </span>
    <select className="border border-[#D5D5D5] rounded-[8px] bg-[#FAFBFD] px-[14px] py-[6px] outline-none font-[600] text-[14px] text-[var(--color-text)]">
      <option value="">Trang {current}</option>
      <option value="">Trang {current + 1}</option>
      <option value="">Trang {current + 2}</option>
    </select>
  </div>
);