import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useDeleteTestcase(number: number) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (testcaseId: number) => {
      await api.delete(`/admin/problems/testcases/${number}/${testcaseId}`);
    },

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [
          QueryKey.ADMIN,
          QueryKey.PROBLEMS,
          QueryKey.TESTCASES,
          number,
        ],
        exact: false,
      });
    },
  });
}
