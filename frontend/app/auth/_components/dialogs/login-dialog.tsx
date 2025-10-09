import LoginForm from "@/app/auth/_components/forms/login-form";
import { Button } from "@/components/ui/button";
import AuthDialogMode, {
  AuthDialogComponentProps,
} from "@/types/auth-dialog-mode";
import { FC } from "react";

const LoginDialog: FC<AuthDialogComponentProps> = ({ setAuthDialogMode }) => {
  return (
    <>
      <LoginForm
        onLinkSent={() => setAuthDialogMode(AuthDialogMode.LINK_SENT)}
        onForgetPassword={() =>
          setAuthDialogMode(AuthDialogMode.FORGET_PASSWORD)
        }
      />

      <div className="text-muted-foreground text-center text-sm">
        <span>Don&#39;t have an account? </span>
        <Button
          className="cursor-pointer px-0"
          variant="link"
          size="sm"
          onClick={() => setAuthDialogMode(AuthDialogMode.REGISTER)}
        >
          Register
        </Button>
      </div>
    </>
  );
};

export default LoginDialog;
