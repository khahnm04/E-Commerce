import { FaFilter, FaRotateLeft } from "react-icons/fa6";

interface FilterProps {
  status: string;
  setStatus: (val: string) => void;
  creator: string;
  setCreator: (val: string) => void;
  startDate: string;
  setStartDate: (val: string) => void;
  endDate: string;
  setEndDate: (val: string) => void;
  onClear: () => void;
}

export const Filter = ({
  status, setStatus, creator, setCreator,
  startDate, setStartDate, endDate, setEndDate, onClear
}: FilterProps) => (
  <div className="mb-[15px]">
    <div className="inline-flex flex-wrap border border-[#D5D5D5] bg-[#FFFFFF] rounded-[14px]">
      <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
        <FaFilter className="text-[22px]" /> Bộ lọc
      </div>
      <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
        <select
          className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer"
          value={status}
          onChange={(e) => setStatus(e.target.value)}
        >
          <option value="">Trạng thái</option>
          <option value="Hoạt động">Hoạt động</option>
          <option value="Tạm dừng">Tạm dừng</option>
        </select>
      </div>
      <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
        <select
          className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer"
          value={creator}
          onChange={(e) => setCreator(e.target.value)}
        >
          <option value="">Người tạo</option>
          <option value="Admin">Admin</option>
        </select>
      </div>
      <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
        <input
          type="date"
          className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer w-[110px]"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
        />
        <span>-</span>
        <input
          type="date"
          className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer w-[110px]"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
        />
      </div>
      <div
        className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-[#D5D5D5] font-[700] text-[14px] text-[#EA0234] cursor-pointer"
        onClick={onClear}
      >
        <FaRotateLeft className="text-[15px]" /> Xóa bộ lọc
      </div>
    </div>
  </div>
);