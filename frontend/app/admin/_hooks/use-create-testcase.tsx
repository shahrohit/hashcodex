import { CreateTestcaseType } from "@/app/admin/_schemas/create-testcase-schema";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useCreateTestcase(number: number) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (body: CreateTestcaseType) => {
      await api.post(`/admin/problems/testcases/${number}`, body);
    },

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [
          QueryKey.ADMIN,
          QueryKey.PROBLEMS,
          QueryKey.TESTCASES,
          number,
        ],
      });
    },
  });
}
