interface DeleteModalProps {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
  title?: string;
  content?: string;
}

export const DeleteModal = ({
  isOpen, onClose, onConfirm,
  title = "Xác nhận",
  content = "Bạn có chắc chắn muốn đưa mục này vào Thùng rác không?"
}: DeleteModalProps) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-gray-900/50 backdrop-blur-sm flex items-center justify-center z-50">
      <div className="bg-white p-8 rounded-lg shadow-lg">
        <h2 className="text-xl font-bold mb-4">{title}</h2>
        <p>{content}</p>
        <div className="mt-6 flex justify-end gap-4">
          <button
            className="px-4 py-2 rounded-lg border border-gray-300 cursor-pointer hover:bg-gray-50"
            onClick={onClose}
          >
            Hủy
          </button>
          <button
            className="px-4 py-2 rounded-lg bg-red-600 text-white cursor-pointer hover:bg-red-700"
            onClick={onConfirm}
          >
            Xóa
          </button>
        </div>
      </div>
    </div>
  );
};