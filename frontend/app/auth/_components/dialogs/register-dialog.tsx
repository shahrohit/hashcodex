import RegisterForm from "@/app/auth/_components/forms/register-form";
import { Button } from "@/components/ui/button";
import AuthDialogMode, {
  AuthDialogComponentProps,
} from "@/types/auth-dialog-mode";
import { FC } from "react";

const RegisterDialog: FC<AuthDialogComponentProps> = ({
  setAuthDialogMode,
}) => {
  return (
    <>
      <RegisterForm
        openDialogAction={() =>
          setAuthDialogMode(AuthDialogMode.ACCOUNT_CREATED)
        }
      />

      <div className="text-muted-foreground text-center text-sm">
        <span>Already have an account? </span>
        <Button
          className="cursor-pointer px-0"
          variant="link"
          size="sm"
          onClick={() => setAuthDialogMode(AuthDialogMode.LOGIN)}
        >
          Login
        </Button>
      </div>
    </>
  );
};

export default RegisterDialog;
