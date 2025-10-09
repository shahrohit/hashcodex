"use client";

import RegisterFormUi from "@/app/auth/_components/forms/register-form-ui";
import useRegister from "@/app/auth/_query/use-register";
import registerSchema, {
  RegisterType,
} from "@/app/auth/_schemas/user-register-schema";
import { handleFormError } from "@/lib/error-handler";
import { zodResolver } from "@hookform/resolvers/zod";
import { useCallback } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";

function RegisterForm({ openDialogAction }: { openDialogAction: () => void }) {
  const { isPending, mutateAsync } = useRegister();

  const registerForm = useForm<RegisterType>({
    resolver: zodResolver(registerSchema),
    defaultValues: {
      name: "",
      email: "",
      password: "",
    },
  });

  const successHandler = useCallback(() => {
    openDialogAction();
    return "Account Created";
  }, [openDialogAction]);

  const onSubmit = useCallback(
    (values: RegisterType) => {
      toast.promise(mutateAsync(values), {
        loading: "Creating Account...",
        success: successHandler,
        error: (error) => handleFormError(registerForm, error),
      });
    },
    [mutateAsync, registerForm, successHandler]
  );

  return (
    <RegisterFormUi
      form={registerForm}
      onSubmit={onSubmit}
      submitPending={isPending}
    />
  );
}

export default RegisterForm;
