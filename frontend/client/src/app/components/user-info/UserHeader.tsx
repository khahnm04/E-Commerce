"use client";
import { UserProfile } from "./UserProfile";
import { InfoCard } from "./InfoCard";
import { UserStat } from "./types";

interface UserHeaderProps {
  name: string;
  phone: string;
  userStats: UserStat[];
}

export const UserHeader = ({ name, phone, userStats }: UserHeaderProps) => {
  return (
    <div className="bg-[#fff] my-[12px] flex justify-between items-center p-[25px] rounded-[12px]">
      <UserProfile name={name} phone={phone} />

      {userStats.map((stat, index) => (
        <InfoCard
          key={index}
          icon={stat.icon}
          value={stat.value}
          description={stat.description}
          showBorder={true}
        />
      ))}
    </div>
  );
};

