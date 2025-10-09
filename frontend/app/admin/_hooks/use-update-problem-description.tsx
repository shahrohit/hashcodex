import { ProblemDetail } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useUpdateProblemDescription(
  slug: string,
  number: number
) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (body: { description: string }) => {
      await api.patch(`/admin/problems/${number}/description`, body);
      return body.description;
    },
    onSuccess: (description: string) => {
      queryClient.setQueryData(
        [QueryKey.ADMIN, QueryKey.PROBLEMS, slug],
        (oldData: ProblemDetail | undefined) => {
          if (!oldData) return oldData;
          return {
            ...oldData,
            description,
            updatedAt: new Date().toISOString(),
          };
        }
      );
    },
  });
}
