"use client";

import LoginFormUi from "@/app/auth/_components/forms/login-form-ui";
import useLogin from "@/app/auth/_query/use-login";
import loginSchema, { LoginType } from "@/app/auth/_schemas/user-login-schema";
import { handleFormError } from "@/lib/error-handler";
import { ApiResponse, ResponseCode } from "@/types/api-response";
import User, { Role } from "@/types/user";
import { Nullable } from "@/types/utility";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { useCallback } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";

type Props = {
  onLinkSent: () => void;
  onForgetPassword: () => void;
};

function LoginForm({ onLinkSent, onForgetPassword }: Props) {
  const router = useRouter();
  const { isPending, mutateAsync } = useLogin();

  const loginForm = useForm<LoginType>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const successHandler = useCallback(
    (data: ApiResponse<Nullable<User>>) => {
      if (data && data.code === ResponseCode.SENT) {
        onLinkSent();
        return "A confirmation Link has been sent to your email address";
      }
      if (data.data?.role === Role.ADMIN) router.push("/admin");
      return "Logged in successfully";
    },
    [onLinkSent, router]
  );

  const onSubmit = useCallback(
    (values: LoginType) => {
      toast.promise(mutateAsync(values), {
        loading: "Verifying...",
        success: successHandler,
        error: (error) => handleFormError(loginForm, error),
      });
    },
    [loginForm, mutateAsync, successHandler]
  );

  return (
    <LoginFormUi
      form={loginForm}
      onSubmit={onSubmit}
      submitPending={isPending}
      onForgetPassword={onForgetPassword}
    />
  );
}

export default LoginForm;
