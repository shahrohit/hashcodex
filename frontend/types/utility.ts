import { FieldValues, UseFormReturn } from "react-hook-form";

export type Nullable<T> = T | null;

export type FormProps<T extends FieldValues, Extra extends object = object> = {
  form: UseFormReturn<T>;
  onSubmit: (data: T) => void;
  submitPending: boolean;
} & Extra;

export type IconProp = { className?: string | undefined };
