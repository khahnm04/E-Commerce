"use client";
export const SocialButton = (props: {
  icon: string,
  label: string
}) => {
  const { icon, label } = props;

  return (
    <>
      <div
        className="w-full sm:w-[210px] h-[60px] flex justify-center items-center font-[500] text-[14px] text-[#121214] rounded-[8px]"
        style={{
          boxShadow: "0px 1px 4px 0px #0000000F, 0px 0px 24px -4px #0000000F"
        }}
      >
        <div className="w-[24px] h-[24px] flex-shrink-0">
          <img src={icon} alt={label} className="w-full h-full object-contain" />
        </div>
        <span className="ml-[4px]">
          {label}
        </span>
      </div>
    </>
  )
}