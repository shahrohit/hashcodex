import { Button } from "@/components/ui/button";
import AuthDialogMode, {
  AuthDialogComponentProps,
} from "@/types/auth-dialog-mode";
import { FC } from "react";

const LinkSentDialog: FC<AuthDialogComponentProps> = ({
  setAuthDialogMode,
}) => {
  return (
    <div>
      <div className="text-center text-sm text-muted-foreground">
        <span>Account verified? </span>
        <Button
          className="px-0 cursor-pointer"
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

export default LinkSentDialog;
