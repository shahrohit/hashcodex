import AuthDialog from "@/app/auth/_components/dialogs/auth-dialog";
import AuthDialogMode from "@/types/auth-dialog-mode";

export default function ResetPasswordPage() {
  return (
    <AuthDialog
      mode={AuthDialogMode.RESET_PASSWORD}
      open={true}
      permanent={true}
    />
  );
}
