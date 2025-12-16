interface HeaderButtonProps {
  icon: string;
  label: string;
  type?: 'dropdown' | 'simple';
  className?: string;
  onClick?: () => void;
}

export const HeaderButton = ({ 
  icon, 
  label, 
  type = 'dropdown', 
  className = '', 
  onClick 
}: HeaderButtonProps) => {
  const baseClasses = "flex items-center px-[16px] py-[8px] gap-[4px] rounded-[8px] cursor-pointer hover:bg-[#c40016]";

  if (type === 'dropdown') {
    return (
      <div className={`${baseClasses} bg-[#e45464] ${className}`} onClick={onClick}>
        <div className="w-[24px] h-[24px]">
          <img src={icon} alt={label} />
        </div>
        <div className="font-[400] text-[14px] text-[#fff]">
          {label}
        </div>
        <div>
          <img src="/assets/images/icon-down.svg" alt="down" />
        </div>
      </div>
    );
  }

  return (
    <div className={`${baseClasses} ${className}`} onClick={onClick}>
      <div className="font-[400] text-[14px] text-[#fff]">
        {label}
      </div>
      <div className="w-[24px] h-[24px]">
        <img src={icon} alt={label} />
      </div>
    </div>
  );
};

