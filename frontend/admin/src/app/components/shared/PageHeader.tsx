import { Breadcrumb } from "./Breadcrumb";

interface PageHeaderProps {
  title: string;
  crumbs: { name: string; link?: string }[];
}

export const PageHeader = ({ title, crumbs }: PageHeaderProps) => {
  return (
    <div className="mb-5">
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">{title}</h1>
      <Breadcrumb crumbs={crumbs} />
    </div>
  );
};