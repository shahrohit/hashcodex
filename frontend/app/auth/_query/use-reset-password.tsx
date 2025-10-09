"use client";

import { ResetPasswordType } from "@/app/auth/_schemas/reset-password-schema";
import publicApi from "@/lib/public-api";
import { useMutation } from "@tanstack/react-query";

type Data = {
  token: string;
  data: ResetPasswordType;
  publicId: string;
};

export default function useResetPassword() {
  return useMutation({
    mutationFn: async ({ token, data, publicId }: Data) =>
      await publicApi.patch("/auth/reset-password", {
        token,
        ...data,
        publicId,
      }),
  });
}
