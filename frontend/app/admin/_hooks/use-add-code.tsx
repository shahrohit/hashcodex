import { ProblemCode } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { ApiResponse } from "@/types/api-response";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useAddCode(number: number) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (body: ProblemCode) => {
      const res = await api.post<ApiResponse<number>>(
        `/admin/problems/codes/${number}`,
        {
          language: body.language,
          driverCode: body.driverCode,
          userCode: body.userCode,
          solutionCode: body.solutionCode,
        }
      );
      body.id = res.data?.data ?? null;
      return body;
    },

    onSuccess: (data: ProblemCode) => {
      queryClient.setQueryData(
        [QueryKey.ADMIN, QueryKey.PROBLEMS, QueryKey.CODES, number],
        (oldData: ProblemCode[] | undefined) => {
          if (!oldData) return oldData;
          return [...oldData, data];
        }
      );
    },
  });
}
