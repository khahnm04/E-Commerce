export const HeaderSearch = () => {
  return (
    <div className="flex flex-1 gap-[8px] px-[9px] py-[8px] bg-[#fff] rounded-[8px]">
      <div className="w-[24px] h-[24px]">
        <img src="/assets/images/icon-search.svg" alt="search" />
      </div>
      <input
        className="w-full placeholder:font-[400] placeholder:text-[16px] placeholder:text-[#CFCFD3]"
        placeholder="Bạn muốn mua gì hôm nay?"
      />
    </div>
  );
};

