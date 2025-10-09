import AuthDialog from "@/app/auth/_components/dialogs/auth-dialog";
import { Button } from "@/components/ui/button";
import AuthDialogMode from "@/types/auth-dialog-mode";
import { ArrowRight } from "lucide-react";

export default function AuthComponent() {
  return (
    <AuthDialog mode={AuthDialogMode.LOGIN}>
      <Button>
        Get Started <ArrowRight />
      </Button>
    </AuthDialog>
  );
}
