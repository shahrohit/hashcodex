"use client";

import { useProblem } from "@/app/problems/_providers/use-problem";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { ApiResponse } from "@/types/api-response";
import { useMutation, useQueryClient } from "@tanstack/react-query";

const useSubmitCode = () => {
  const { problem } = useProblem();
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (body: { language: string; code: string }) => {
      const res = await api.post<ApiResponse<string>>(
        `/problems/submissions/submit/${problem.number}`,
        body
      );
      return res.data.data;
    },
    onSuccess: () => {
      setTimeout(() => {
        queryClient.invalidateQueries({
          queryKey: [
            QueryKey.USER,
            QueryKey.PROBLEMS,
            QueryKey.SUBMISSIONS,
            problem.number,
          ],
        });
      }, 3000);
    },
  });
};

export default useSubmitCode;
