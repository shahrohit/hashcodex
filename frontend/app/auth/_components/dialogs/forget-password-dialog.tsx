import ForgetPasswordForm from "@/app/auth/_components/forms/forget-password-form";
import { Button } from "@/components/ui/button";
import AuthDialogMode, {
  AuthDialogComponentProps,
} from "@/types/auth-dialog-mode";
import { FC } from "react";

const ForgetPasswordDialog: FC<AuthDialogComponentProps> = ({
  setAuthDialogMode,
}) => {
  return (
    <div>
      <ForgetPasswordForm />

      <div className="text-muted-foreground text-center text-sm">
        <span>Password Reset? </span>
        <Button
          className="cursor-pointer px-0"
          variant="link"
          size="sm"
          onClick={() => setAuthDialogMode(AuthDialogMode.LOGIN)}
        >
          Login
        </Button>
      </div>
    </div>
  );
};

export default ForgetPasswordDialog;
