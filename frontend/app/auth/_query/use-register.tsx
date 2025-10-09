"use client";

import { RegisterType } from "@/app/auth/_schemas/user-register-schema";
import publicApi from "@/lib/public-api";
import { useMutation } from "@tanstack/react-query";

export default function useRegister() {
  return useMutation({
    mutationFn: async (body: RegisterType) =>
      await publicApi.post("/auth/register", body),
  });
}
