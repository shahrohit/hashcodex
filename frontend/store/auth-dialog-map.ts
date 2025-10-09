import AccountCreatedDialog from "@/app/auth/_components/dialogs/account-created-dialog";
import ForgetPasswordDialog from "@/app/auth/_components/dialogs/forget-password-dialog";
import LinkSentDialog from "@/app/auth/_components/dialogs/link-sent-dialog";
import LoginDialog from "@/app/auth/_components/dialogs/login-dialog";
import RegisterDialog from "@/app/auth/_components/dialogs/register-dialog";
import ResetPasswordDialog from "@/app/auth/_components/dialogs/reset-password-dialog";
import VerifyAccountDialog from "@/app/auth/_components/dialogs/verify-account-dialog";
import {
  AuthDialogModeHeader,
  AuthDialogModeType,
} from "@/types/auth-dialog-mode";
import { FC } from "react";

const authDialogComponentMap = {
  LOGIN: LoginDialog,
  REGISTER: RegisterDialog,
  VERIFY_ACCOUNT: VerifyAccountDialog,
  LINK_SENT: LinkSentDialog,
  ACCOUNT_CREATED: AccountCreatedDialog,
  FORGET_PASSWORD: ForgetPasswordDialog,
  RESET_PASSWORD: ResetPasswordDialog,
} satisfies Record<
  AuthDialogModeType,
  FC<{ setAuthDialogMode: (mode: AuthDialogModeType) => void }>
>;

export const authDialogHeaderMap: Record<
  AuthDialogModeType,
  AuthDialogModeHeader
> = {
  LOGIN: {
    title: "Welcome Back!",
    description: "Login into Hashcodex",
  },
  REGISTER: {
    title: "Create Account",
    description: "Create your account in Hashcodex",
  },
  VERIFY_ACCOUNT: {
    title: "Verify Account",
    description: "Please confirm your verification",
  },
  LINK_SENT: {
    title: "Verify Your Account",
    description:
      "A confirmation link has been sent to your registered email address",
  },
  ACCOUNT_CREATED: {
    title: "Account Created",
    description:
      "A confirmation link has been sent to your registered email address",
  },
  FORGET_PASSWORD: {
    title: "Forget Password",
    description: "Please provide your email address.",
  },
  RESET_PASSWORD: {
    title: "Reset Password",
    description: "Update your password",
  },
};

export default authDialogComponentMap;
