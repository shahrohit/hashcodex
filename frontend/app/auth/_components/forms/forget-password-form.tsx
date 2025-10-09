import ForgetPasswordFormUi from "@/app/auth/_components/forms/forget-password-form-ui";
import useForgetPassword from "@/app/auth/_query/use-forget-password";
import forgetPasswordSchema, {
  ForgetPasswordType,
} from "@/app/auth/_schemas/forget-password-schema";
import { handleFormError } from "@/lib/error-handler";
import { zodResolver } from "@hookform/resolvers/zod";
import { useCallback } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";

export default function ForgetPasswordForm() {
  const { isPending, mutateAsync, isSuccess } = useForgetPassword();

  const form = useForm<ForgetPasswordType>({
    resolver: zodResolver(forgetPasswordSchema),
    defaultValues: {
      email: "",
    },
  });

  const onSubmit = useCallback(
    (values: ForgetPasswordType) => {
      toast.promise(mutateAsync(values), {
        loading: "Sending...",
        success: "A reset link has been sent to your email address",
        error: (error) => handleFormError(form, error),
      });
    },
    [form, mutateAsync]
  );

  return (
    <ForgetPasswordFormUi
      form={form}
      onSubmit={onSubmit}
      submitPending={isPending}
      isSuccess={isSuccess}
    />
  );
}
