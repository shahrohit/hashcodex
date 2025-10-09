import AuthDialog from "@/app/auth/_components/dialogs/auth-dialog";
import AuthDialogMode from "@/types/auth-dialog-mode";

export default function VerifyAccountPage() {
  return (
    <AuthDialog
      mode={AuthDialogMode.VERIFY_ACCOUNT}
      open={true}
      permanent={true}
    />
  );
}
