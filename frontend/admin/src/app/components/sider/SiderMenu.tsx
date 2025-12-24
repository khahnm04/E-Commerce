import Link from "next/link";
import { IconType } from "react-icons";
import { usePathname } from "next/navigation";

interface MenuItem {
  name: string;
  link: string;
  icon: IconType;
  isLogout?: boolean;
}

interface SiderMenuProps {
  menuList: MenuItem[];
}

export const SiderMenu = ({ menuList }: SiderMenuProps) => {
  const pathname = usePathname();

  const checkIsActive = (currentPath: string, link: string) => {
    if (link === "/") return currentPath === "/";
    return currentPath.startsWith(link);
  };

  const getMenuItemClass = (isActive: boolean, isLogout?: boolean) => {
    const baseClass = "flex items-center gap-[10px] py-[12px] px-[16px] ml-[24px] mr-[10px] rounded-[6px] font-[600] text-[14px] relative transition-all";
    const activeClass = "bg-[var(--color-primary)] text-white before:absolute before:left-[-24px] before:top-0 before:h-full before:w-[4.5px] before:rounded-[0_4px_4px_0] before:bg-[var(--color-primary)] before:content-['']";
    const logoutClass = "text-[#F93C65] hover:bg-gray-50";
    const defaultClass = "text-[var(--color-text)] hover:bg-gray-50";
    if (isActive) return `${baseClass} ${activeClass}`;
    if (isLogout) return `${baseClass} ${logoutClass}`;
    return `${baseClass} ${defaultClass}`;
  };

  if (!menuList || menuList.length === 0) return null;

  return (
    <ul className="list-none p-0 my-[11px] mx-0">
      {menuList.map((item, index) => {
        const isActive = checkIsActive(pathname, item.link);

        return (
          <li key={index}>
            <Link
              href={item.link}
              className={getMenuItemClass(isActive, item.isLogout)}
            >
              <item.icon className="text-[16px]" />
              {item.name}
            </Link>
          </li>
        );
      })}
    </ul>
  );
};
