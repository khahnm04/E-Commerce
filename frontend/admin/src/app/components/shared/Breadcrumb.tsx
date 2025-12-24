import Link from "next/link";

interface Crumb {
  name: string;
  link?: string;
}

interface BreadcrumbProps {
  crumbs: Crumb[];
}

export const Breadcrumb = ({ crumbs }: BreadcrumbProps) => {
  return (
    <div className="mb-[30px] flex items-center gap-x-[8px] font-[600] text-[14px] text-[#979797]">
      {crumbs.map((crumb, index) => (
        <span key={index} className="flex items-center gap-x-[8px]">
          {crumb.link ? (
            <Link href={crumb.link} className="hover:text-[var(--color-primary)]">
              {crumb.name}
            </Link>
          ) : (
            <span>{crumb.name}</span>
          )}
          {index < crumbs.length - 1 && <span>/</span>}
        </span>
      ))}
    </div>
  );
};
