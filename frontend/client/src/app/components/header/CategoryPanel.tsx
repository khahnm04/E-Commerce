import React from 'react';

interface CategoryPanelProps {
  isOpen: boolean;
  onClose: () => void;
  headerHeight: number;
}

export const CategoryPanel: React.FC<CategoryPanelProps> = ({ isOpen, onClose, headerHeight }) => {
  return (
    <div
      className={`fixed left-0 w-full bg-white shadow-lg transition-transform duration-300 ease-in-out z-50
        ${isOpen ? 'translate-y-0 opacity-100' : '-translate-y-full opacity-0'}`}
      style={{ top: headerHeight }}
    >
      <div className="max-w-[1232px] mx-auto px-4 py-8">
        <h3 className="text-xl font-bold mb-4">Danh mục sản phẩm</h3>
        <ul className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
          {[...Array(8)].map((_, i) => (
            <li key={i} className="p-2 border rounded-md hover:bg-gray-50 cursor-pointer" onClick={onClose}>
              Category {i + 1}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};