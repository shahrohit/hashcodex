import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { ApiResponse } from "@/types/api-response";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useUpdateSlug(number: number) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (body: { slug: string }) => {
      const response = await api.patch<ApiResponse<string>>(
        `/admin/problems/${number}/slug`,
        body
      );
      return response.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QueryKey.ADMIN, QueryKey.PROBLEMS],
        exact: false,
      });
    },
  });
}
