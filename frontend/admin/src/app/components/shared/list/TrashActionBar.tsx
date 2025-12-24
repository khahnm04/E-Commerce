import Link from "next/link";
import { FaMagnifyingGlass } from "react-icons/fa6";

interface TrashActionBarProps {
  backLink: string;
}

export const TrashActionBar = ({ backLink }: TrashActionBarProps) => (
  <div className="mb-[30px]">
    <div className="flex flex-wrap gap-[20px]">
      <div className="inline-flex flex-wrap border border-[#D5D5D5] bg-[#FFFFFF] rounded-[14px]">
        <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-r border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
          <select className="border-0 outline-none font-[700] text-[14px] text-[var(--color-text)] cursor-pointer">
            <option value="">-- Hành động --</option>
            <option value="restore">Khôi phục</option>
            <option value="delete_permanently">Xóa vĩnh viễn</option>
          </select>
        </div>
        <div className="lg:p-[24px] p-[15px] inline-flex items-center gap-[12px] border-[#D5D5D5] font-[700] text-[14px] text-[var(--color-text)]">
          <button className="bg-transparent border-0 font-[600] text-[14px] text-[#EA0234] cursor-pointer hover:underline">
            Áp dụng
          </button>
        </div>
      </div>
      <div className="border border-[#E2E2E2] bg-[#fff] inline-flex items-center gap-[15px] max-w-[366px] w-full lg:p-[24px] p-[15px] rounded-[14px]">
        <FaMagnifyingGlass className="text-[20px]" />
        <input type="text" placeholder="Tìm kiếm" className="text-[14px] font-[700] flex-1 border-0 outline-none text-[var(--color-text)] placeholder:text-[#979797]" />
      </div>
      <div className="">
        <Link
          href={backLink}
          className="inline-block bg-[var(--color-primary)] rounded-[14px] lg:px-[50px] px-[30px] lg:py-[24px] py-[15px] font-[700] text-[14px] text-white"
        >
          Danh sách
        </Link>
      </div>
    </div>
  </div>
);