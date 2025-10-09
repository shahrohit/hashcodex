"use client";

import ResetPasswordForm from "@/app/auth/_components/forms/reset-password-form";
import { AuthDialogComponentProps } from "@/types/auth-dialog-mode";
import { useSearchParams } from "next/navigation";
import { FC } from "react";

const ResetPasswordDialog: FC<AuthDialogComponentProps> = () => {
  const searchParams = useSearchParams();
  const token = searchParams.get("token");
  const publicId = searchParams.get("publicId");

  return token && publicId ? (
    <ResetPasswordForm token={token} publicId={publicId} />
  ) : (
    <div>Missing token</div>
  );
};

export default ResetPasswordDialog;
