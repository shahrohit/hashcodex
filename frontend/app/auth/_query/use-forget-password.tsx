"use client";

import { ForgetPasswordType } from "@/app/auth/_schemas/forget-password-schema";
import publicApi from "@/lib/public-api";
import { useMutation } from "@tanstack/react-query";

export default function useForgetPassword() {
  return useMutation({
    mutationFn: async (body: ForgetPasswordType) =>
      await publicApi.post("/auth/forget-password", body),
  });
}
