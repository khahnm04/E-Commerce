import Link from "next/link";
import { ReactNode, ChangeEvent } from "react";
import { FaRegPenToSquare, FaRegTrashCan, FaTrashArrowUp } from "react-icons/fa6";

interface Identifiable {
  id: number | string;
}

interface TableProps<T extends Identifiable> {
  tableHeaderList: string[];
  data: T[];
  selectedItems: (number | string)[];
  handleSelectAll: (e: ChangeEvent<HTMLInputElement>) => void;
  handleSelectItem: (id: number | string) => void;
  handleDeleteClick: (id: number | string) => void;
  handleRestoreClick?: (id: number | string) => void;
  updateLink?: string;
  renderers: Record<string, (item: T) => ReactNode>;
  centeredColumns?: number[]; // Mảng chứa index các cột cần căn giữa (bắt đầu từ 0)
}

export const Table = <T extends Identifiable>({
  tableHeaderList,
  data = [],
  selectedItems,
  handleSelectAll,
  handleSelectItem,
  handleDeleteClick,
  handleRestoreClick,
  updateLink,
  renderers,
  centeredColumns = [],
}: TableProps<T>) => {
  const columnKeys = Object.keys(renderers);

  return (
    <div className="overflow-x-auto border border-[#B9B9B9] rounded-[14px] overflow-hidden">
      <table className="bg-[#fff] w-full min-w-[1141px]">
        <thead>
          <tr>
            {/* Cột Checkbox */}
            <th className="bg-[#FCFDFD] border-b border-[#D5D5D5] p-[15px] text-center w-[50px]">
              <input
                className="w-[20px] h-[20px] cursor-pointer align-middle"
                type="checkbox"
                onChange={handleSelectAll}
                checked={data.length > 0 && selectedItems.length === data.length}
              />
            </th>

            {/* Render Header Columns */}
            {tableHeaderList.map((item, index) => (
              <th
                key={index}
                className={`bg-[#FCFDFD] border-b border-[#D5D5D5] py-[8px] px-[15px] font-[800] text-[14px] text-[var(--color-text)] align-middle
                  ${centeredColumns.includes(index) ? 'text-center' : 'text-left'}`}
              >
                {item}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((item) => (
            <tr key={item.id}>
              {/* Cột Checkbox Body */}
              <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-center align-middle">
                <input
                  className="w-[20px] h-[20px] cursor-pointer align-middle"
                  type="checkbox"
                  checked={selectedItems.includes(item.id)}
                  onChange={() => handleSelectItem(item.id)}
                />
              </td>

              {/* Render Dynamic Columns */}
              {columnKeys.map((key, index) => (
                <td
                  key={key}
                  className={`py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)] align-middle
                    ${centeredColumns.includes(index) ? 'text-center' : 'text-left'}`}
                >
                  {renderers[key]?.(item)}
                </td>
              ))}

              {/* Cột Hành động (Luôn là cột cuối cùng của header) */}
              <td className={`py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-[14px] font-[600] text-[var(--color-text)] align-middle
                  ${centeredColumns.includes(tableHeaderList.length - 1) ? 'text-center' : 'text-left'}`}>
                <div className="inline-flex bg-[#FAFBFD] border border-[#D5D5D5] rounded-[8px]">
                  {updateLink && (
                    <Link
                      className="inline-block px-[12px] py-[8px] border-r border-[#D5D5D5] text-[15px] text-[rgba(0, 0, 0, 0.6)] hover:text-blue-600"
                      href={`${updateLink}/${item.id}`}
                    >
                      <FaRegPenToSquare />
                    </Link>
                  )}
                  {handleRestoreClick && (
                    <button
                      className={`inline-block px-[12px] py-[8px] text-[15px] text-green-500 bg-transparent border-none cursor-pointer hover:text-green-700 ${updateLink ? 'border-r border-[#D5D5D5]' : ''}`}
                      onClick={() => handleRestoreClick(item.id)}
                    >
                      <FaTrashArrowUp />
                    </button>
                  )}
                  <button
                    className="inline-block px-[12px] py-[8px] text-[15px] text-[#EF3826] bg-transparent border-none cursor-pointer hover:text-red-700"
                    onClick={() => handleDeleteClick(item.id)}
                  >
                    <FaRegTrashCan />
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};