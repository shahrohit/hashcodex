"use client";

import { useProblem } from "@/app/problems/_providers/use-problem";
import api from "@/lib/api";
import { ApiResponse } from "@/types/api-response";
import { useMutation } from "@tanstack/react-query";

const useRunCode = () => {
  const { problem } = useProblem();
  return useMutation({
    mutationFn: async (body: {
      language: string;
      code: string;
      testcases: string[];
    }) => {
      const res = await api.post<ApiResponse<string>>(
        `/problems/submissions/run/${problem.number}`,
        body
      );
      return res.data.data;
    },
  });
};

export default useRunCode;
