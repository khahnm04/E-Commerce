interface IconCircleProps {
  src: string;
  alt: string;
}

export const IconCircle = ({ src, alt }: IconCircleProps) => (
  <div className="w-[60px] h-[60px] bg-[#ffefee] rounded-[50%] flex items-center justify-center">
    <img src={src} alt={alt} />
  </div>
);

