import { Input } from "@/components/ui/input";
import { DetailedHTMLProps, FC, InputHTMLAttributes } from "react";

export default function InputWithIcon({
  type = "text",
  Icon,
  placeholder,
  field,
}: {
  type?: string;
  placeholder: string;
  field: DetailedHTMLProps<InputHTMLAttributes<HTMLInputElement>, HTMLInputElement>;
  Icon: FC<{ className?: string }>;
}) {
  return (
    <div className="relative w-full">
      <Icon className="text-muted-foreground absolute top-1/2 left-3 size-4 -translate-y-1/2" />
      <Input type={type} className="ps-8" placeholder={placeholder} {...field} />
    </div>
  );
}
