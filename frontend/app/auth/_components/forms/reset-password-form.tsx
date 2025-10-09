import ResetPasswordFormUi from "@/app/auth/_components/forms/reset-password-form-ui";
import useResetPassword from "@/app/auth/_query/use-reset-password";
import resetPasswordSchema, {
  ResetPasswordType,
} from "@/app/auth/_schemas/reset-password-schema";
import { getError } from "@/lib/error-handler";
import { ErrorCode } from "@/types/error-response";
import { zodResolver } from "@hookform/resolvers/zod";
import { useCallback } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";

export default function ResetPasswordForm({
  token,
  publicId,
}: {
  token: string;
  publicId: string;
}) {
  const form = useForm<ResetPasswordType>({
    resolver: zodResolver(resetPasswordSchema),
    defaultValues: {
      password: "",
      confirmPassword: "",
    },
  });

  const errorHandler = useCallback(
    (err: Error | undefined) => {
      const error = getError(err);
      if (error.code === ErrorCode.VALIDATION_ERROR) {
        Object.entries(error.errors ?? {}).forEach(([field, message]) => {
          const key =
            field === "token" || field === "publicId"
              ? "root"
              : (field as keyof ResetPasswordType);
          form.setError(key, { type: "server", message });
        });
      } else {
        form.setError("root", { type: "server", message: error.message });
      }

      return error.message;
    },
    [form]
  );

  const { isPending, mutateAsync, isSuccess } = useResetPassword();

  const onSubmit = useCallback(
    (data: ResetPasswordType) => {
      if (!token) toast.error("Missing token");
      else if (!publicId) toast.error("Missing id");
      else {
        toast.promise(mutateAsync({ token, data, publicId }), {
          loading: "Processing...",
          success: "Password Reset Successfully",
          error: errorHandler,
        });
      }
    },
    [errorHandler, mutateAsync, publicId, token]
  );

  if (isSuccess) {
    return (
      <div>
        <div className="flex flex-col text-center">
          <span className="font-semibold text-green-300">
            Password has been updated.
          </span>
          <span className="text-muted-foreground text-sm">
            You may now close this window
          </span>
        </div>
      </div>
    );
  }

  return (
    <ResetPasswordFormUi
      form={form}
      onSubmit={onSubmit}
      submitPending={isPending}
    />
  );
}
