export interface DropdownItem {
  id: number;
  label: string;
  icon: string;
}

export interface ActionItem {
  id: number;
  label: string;
  icon: string;
  bgClass: string;
  onClick?: () => void;
}

export const defaultDropdownItems: DropdownItem[] = [
  {
    id: 1,
    label: "Danh mục",
    icon: "/assets/images/icon-category.svg",
  },
  {
    id: 2,
    label: "Hồ Chí Minh",
    icon: "/assets/images/icon-location.svg",
  },
];

