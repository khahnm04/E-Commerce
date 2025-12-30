import Link from "next/link";
import { ReactNode, ChangeEvent } from "react";
import {
  FaRegPenToSquare,
  FaRegTrashCan,
  FaTrashArrowUp,
  FaSort,
  FaSortUp,
  FaSortDown,
  FaRegEye // Import thêm icon xem chi tiết
} from "react-icons/fa6";

interface Identifiable {
  id: number | string;
  slug?: string;
}

interface TableHeader {
  label: string;
  sortKey?: string;
}

interface TableProps<T extends Identifiable> {
  tableHeaderList: TableHeader[];
  data: T[];
  selectedItems?: (number | string)[];
  handleSelectAll?: (e: ChangeEvent<HTMLInputElement>) => void;
  handleSelectItem?: (id: number | string) => void;
  handleDeleteClick?: (id: number | string) => void;
  handleRestoreClick?: (id: number | string) => void;
  updateLink?: string;
  detailLink?: string; // Prop mới cho trang xem chi tiết
  updateIcon?: ReactNode;
  deleteIcon?: ReactNode;
  restoreIcon?: ReactNode;
  detailIcon?: ReactNode; // Icon cho trang chi tiết
  renderers: Record<string, (item: T) => ReactNode>;
  centeredColumns?: number[];
  sortConfig?: { key: string; direction: "asc" | "desc" };
  onSort?: (key: string) => void;
}

export const Table = <T extends Identifiable>({
  tableHeaderList,
  data = [],
  selectedItems = [],
  handleSelectAll,
  handleSelectItem,
  handleDeleteClick,
  handleRestoreClick,
  updateLink,
  detailLink,
  updateIcon,
  deleteIcon,
  restoreIcon,
  detailIcon,
  renderers,
  centeredColumns = [],
  sortConfig,
  onSort,
}: TableProps<T>) => {
  const columnKeys = Object.keys(renderers);
  const showCheckbox = !!handleSelectAll && !!handleSelectItem;
  // Kiểm tra hiển thị cột hành động nếu có bất kỳ link hoặc hàm callback nào
  const showActions = !!handleDeleteClick || !!updateLink || !!handleRestoreClick || !!detailLink;

  return (
    <div className="overflow-x-auto border border-[#B9B9B9] rounded-[14px] overflow-hidden">
      <table className="bg-[#fff] w-full min-w-[1141px]">
        <thead>
          <tr>
            {showCheckbox && (
              <th className="bg-[#FCFDFD] border-b border-[#D5D5D5] p-[15px] text-center w-[50px]">
                <input
                  type="checkbox"
                  className="w-[20px] h-[20px] cursor-pointer align-middle"
                  onChange={handleSelectAll}
                  checked={data.length > 0 && selectedItems.length === data.length}
                />
              </th>
            )}

            {tableHeaderList.map((item, index) => {
              const isSortable = !!item.sortKey && !!onSort;
              const isActive = sortConfig?.key === item.sortKey;

              return (
                <th
                  key={index}
                  onClick={() => isSortable && onSort(item.sortKey!)}
                  className={`
                    bg-[#FCFDFD] border-b border-[#D5D5D5] py-[12px] px-[15px] 
                    font-[800] text-[14px] text-[var(--color-text)] align-middle
                    ${centeredColumns.includes(index) ? "text-center" : "text-left"}
                    ${isSortable ? "cursor-pointer hover:bg-gray-100 select-none" : ""}
                  `}
                >
                  <div className={`flex items-center gap-1 ${centeredColumns.includes(index) ? "justify-center" : ""}`}>
                    {item.label}
                    {isSortable && (
                      <span className="text-[12px]">
                        {!isActive ? (
                          <FaSort className="opacity-20" />
                        ) : sortConfig != undefined && sortConfig.direction === "asc" ? (
                          <FaSortUp className="text-blue-500" />
                        ) : (
                          <FaSortDown className="text-blue-500" />
                        )}
                      </span>
                    )}
                  </div>
                </th>
              );
            })}
          </tr>
        </thead>

        <tbody>
          {data.length > 0 ? (
            data.map((item) => (
              <tr key={item.id} className="hover:bg-gray-50 transition-colors">
                {showCheckbox && (
                  <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-center align-middle">
                    <input
                      type="checkbox"
                      className="w-[20px] h-[20px] cursor-pointer align-middle"
                      checked={selectedItems.includes(item.id)}
                      onChange={() => handleSelectItem?.(item.id)}
                    />
                  </td>
                )}

                {columnKeys.map((key, index) => (
                  <td
                    key={key}
                    className={`
                      py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] 
                      text-[14px] font-[600] text-[var(--color-text)] align-middle
                      ${centeredColumns.includes(index) ? "text-center" : "text-left"}
                    `}
                  >
                    {renderers[key]?.(item)}
                  </td>
                ))}

                {showActions && (
                  <td className="py-[8px] px-[15px] border-b border-[rgba(151,151,151,0.6)] text-center align-middle w-[150px]">
                    <div className="inline-flex bg-[#FAFBFD] border border-[#D5D5D5] rounded-[8px] overflow-hidden">

                      {/* 1. ICON XEM CHI TIẾT (Luôn hiện nếu có detailLink) */}
                      {detailLink && (
                        <Link
                          href={`${detailLink}/${item.slug || item.id}`}
                          className={`inline-block px-[12px] py-[8px] text-[15px] text-blue-500 hover:text-blue-700 hover:bg-gray-100 
                            ${(updateLink || handleRestoreClick || handleDeleteClick) ? "border-r border-[#D5D5D5]" : ""}`}
                          title="Xem chi tiết"
                        >
                          {detailIcon || <FaRegEye />}
                        </Link>
                      )}

                      {/* 2. ICON CẬP NHẬT */}
                      {updateLink && (
                        <Link
                          href={`${updateLink}/${item.slug || item.id}`}
                          className={`inline-block px-[12px] py-[8px] text-[15px] text-[rgba(0,0,0,0.6)] hover:text-blue-600 hover:bg-gray-100 
                            ${(handleRestoreClick || handleDeleteClick) ? "border-r border-[#D5D5D5]" : ""}`}
                          title="Cập nhật"
                        >
                          {updateIcon || <FaRegPenToSquare />}
                        </Link>
                      )}

                      {/* 3. ICON KHÔI PHỤC (Dùng cho thùng rác) */}
                      {handleRestoreClick && (
                        <button
                          type="button"
                          onClick={() => handleRestoreClick(item.id)}
                          className={`inline-block px-[12px] py-[8px] text-[15px] text-green-500 bg-transparent border-none cursor-pointer hover:text-green-700 hover:bg-gray-100 
                            ${handleDeleteClick ? "border-r border-[#D5D5D5]" : ""}`}
                          title="Khôi phục"
                        >
                          {restoreIcon || <FaTrashArrowUp />}
                        </button>
                      )}

                      {/* 4. ICON XÓA */}
                      {handleDeleteClick && (
                        <button
                          type="button"
                          onClick={() => handleDeleteClick?.(item.id)}
                          className="inline-block px-[12px] py-[8px] text-[15px] text-[#EF3826] bg-transparent border-none cursor-pointer hover:text-red-700 hover:bg-gray-100"
                          title="Xóa"
                        >
                          {deleteIcon || <FaRegTrashCan />}
                        </button>
                      )}
                    </div>
                  </td>
                )}
              </tr>
            ))
          ) : (
            <tr>
              <td
                colSpan={tableHeaderList.length + (showCheckbox ? 1 : 0) + (showActions ? 1 : 0)}
                className="py-10 text-center text-gray-400 italic"
              >
                Không có dữ liệu hiển thị
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};