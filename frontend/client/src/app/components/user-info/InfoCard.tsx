import { IconCircle } from "./IconCircle";

interface InfoCardProps {
  icon: string;
  value: string;
  description: string;
  showBorder?: boolean;
}

export const InfoCard = ({
  icon,
  value,
  description,
  showBorder = false
}: InfoCardProps) => (
  <div className={`flex items-center gap-[16px] ${showBorder ? 'border-l-4 border-[#D70018] pl-[16px]' : ''}`}>
    <IconCircle src={icon} alt={description} />
    <div className="flex flex-col gap-[4px]">
      <div className="font-[700] text-[24px] text-[#121214]">
        {value}
      </div>
      <div className="font-[400] text-[14px] text-[#71717A]">
        {description}
      </div>
    </div>
  </div>
);

