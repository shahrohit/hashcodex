import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { PaginatedResponse } from "@/types/api-response";
import { UserProblemItem } from "@/types/problems";
import { useQuery } from "@tanstack/react-query";

export default function useQueryProblems() {
  return useQuery({
    queryKey: [QueryKey.USER, QueryKey.PROBLEMS],
    queryFn: async () => {
      const response = await api.get<PaginatedResponse<UserProblemItem>>(
        `/problems`
      );
      return response.data;
    },
  });
}
