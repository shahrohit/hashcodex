"use client";
import useAuth from "@/hooks/use-auth";
import api from "@/lib/api";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useLogout() {
  const { setUser } = useAuth();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async () => await api.post("/users/logout"),
    onSuccess: () => {
      setUser(null);
      queryClient.invalidateQueries();
      sessionStorage.clear();
    },
  });
}
