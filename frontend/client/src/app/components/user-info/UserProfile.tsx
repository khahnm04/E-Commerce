import { IconCircle } from "./IconCircle";

interface UserProfileProps {
  name: string;
  phone: string;
}

export const UserProfile = ({ name, phone }: UserProfileProps) => (
  <div className="flex items-center gap-[16px]">
    <IconCircle src="/assets/images/avatar-user.svg" alt="Avatar" />
    <div className="flex flex-col gap-[4px]">
      <div className="font-[700] text-[16px] text-[#121214]">
        {name}
      </div>
      <div className="font-[400] text-[14px] text-[#71717A]">
        {phone}
      </div>
    </div>
  </div>
);

