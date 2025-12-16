"use client";
export const AppButton = (props: {
  children: string;
  onClick?: () => void;
}) => {
  const { children, onClick } = props;

  return (
    <button
      type="submit"
      onClick={onClick}
      className="w-full bg-[#D70018] hover:bg-[#b00014] px-[16px] py-[11px] rounded-[8px] cursor-pointer font-[500] text-[16px] text-[#fff] transition-colors"
    >
      {children}
    </button>
  );
}