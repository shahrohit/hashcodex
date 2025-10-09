"use client";

import publicApi from "@/lib/public-api";
import { useMutation } from "@tanstack/react-query";

export default function useVerifyAccount() {
  return useMutation({
    mutationFn: async ({
      token,
      publicId,
    }: {
      token: string;
      publicId: string;
    }) => await publicApi.patch("/auth/verify-account", { token, publicId }),
  });
}
