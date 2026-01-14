import Link from "next/link";
import { Breadcrumb } from "@/app/components/shared/Breadcrumb";

interface FormPageLayoutProps {
  title: string;
  breadcrumbs: { name: string; link?: string }[];
  backLink: string;
  children: React.ReactNode;
}

export const FormPageLayout = ({ title, breadcrumbs, backLink, children }: FormPageLayoutProps) => {
  return (
    <>
      <h1 className="mt-0 mb-1.5 font-bold text-[32px] text-[var(--color-text)]">
        {title}
      </h1>

      <Breadcrumb crumbs={breadcrumbs} />

      <div className="bg-white border border-[#B9B9B9] rounded-[14px] md:p-[50px] p-[20px]">
        {children}
        <div className="inner-back text-center mt-[30px]">
          <Link
            href={backLink}
            className="font-[700] text-[18px] text-[var(--color-primary)] underline"
          >
            Quay lại danh sách
          </Link>
        </div>
      </div>
    </>
  );
};