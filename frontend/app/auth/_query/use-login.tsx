"use client";

import { LoginType } from "@/app/auth/_schemas/user-login-schema";
import useAuth from "@/hooks/use-auth";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import Helpers from "@/lib/helpers";
import { ApiResponse } from "@/types/api-response";
import User from "@/types/user";
import { Nullable } from "@/types/utility";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useLogin() {
  const { setUser } = useAuth();
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (body: LoginType) => {
      await Helpers.sleep();
      const response = await api.post<ApiResponse<Nullable<User>>>(
        "/auth/login",
        body
      );
      return response.data;
    },

    onSuccess: (response) => {
      if (response.data) setUser(response.data);
      queryClient.invalidateQueries({
        queryKey: [QueryKey.USER],
        exact: false,
      });
    },
    onError: () => setUser(null),
  });
}
