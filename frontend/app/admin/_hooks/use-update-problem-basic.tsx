import { UpdateProblemBasicType } from "@/app/admin/_schemas/update-problem-basic-schema";
import { ProblemDetail } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useUpdateProblemBasic(slug: string, number: number) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (body: UpdateProblemBasicType) => {
      await api.patch(`/admin/problems/${number}/basic`, body);
      return body;
    },
    onSuccess: (resData: UpdateProblemBasicType) => {
      queryClient.setQueryData(
        [QueryKey.ADMIN, QueryKey.PROBLEMS, slug],
        (oldData: ProblemDetail | undefined) => {
          if (!oldData) return oldData;
          return {
            ...oldData,
            ...resData,
            updatedAt: new Date().toISOString(),
          };
        }
      );
    },
  });
}
