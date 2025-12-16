interface InfoRowProps {
  label: string;
  value: string;
}

export const InfoRow = ({ label, value }: InfoRowProps) => (
  <div className="flex justify-between items-center py-[16px] border-b border-[#E4E4E7]">
    <div className="font-[400] text-[14px] text-[#71717A]">
      {label}
    </div>
    <div className="font-[500] text-[14px] text-[#121214]">
      {value}
    </div>
  </div>
);

