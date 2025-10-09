import { ReactNode } from "react";
import { z } from "zod";

const AuthDialogMode = {
  LOGIN: "LOGIN",
  REGISTER: "REGISTER",
  LINK_SENT: "LINK_SENT",
  VERIFY_ACCOUNT: "VERIFY_ACCOUNT",
  ACCOUNT_CREATED: "ACCOUNT_CREATED",
  FORGET_PASSWORD: "FORGET_PASSWORD",
  RESET_PASSWORD: "RESET_PASSWORD",
} as const;

export type AuthDialogProps = {
  open?: boolean;
  mode: AuthDialogModeType;
  children?: ReactNode;
  permanent?: boolean;
};

export type AuthDialogModeHeader = {
  title: string;
  description: string;
};

export const AuthDialogModeEnum = z.enum([...Object.values(AuthDialogMode)]);
export type AuthDialogModeType = z.infer<typeof AuthDialogModeEnum>;

export interface AuthDialogComponentProps {
  setAuthDialogMode: (mode: AuthDialogModeType) => void;
}

export default AuthDialogMode;
