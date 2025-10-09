import { CreateProblemType } from "@/app/admin/_schemas/create-problem-schema";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import Helpers from "@/lib/helpers";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useCreateProblem() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (body: CreateProblemType) => {
      await Helpers.sleep();
      await api.post("/admin/problems", body);
    },

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QueryKey.ADMIN, QueryKey.PROBLEMS],
      });
    },
  });
}
