import useAuth from "@/hooks/use-auth";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import publicApi from "@/lib/public-api";
import { PaginatedResponse } from "@/types/api-response";
import { UserProblemItem } from "@/types/problems";
import { useQuery } from "@tanstack/react-query";

export default function useQueryProblems() {
  const { user } = useAuth();
  return useQuery({
    queryKey: [QueryKey.USER, QueryKey.PROBLEMS],
    queryFn: async () => {
      const response = await (user ? api : publicApi).get<
        PaginatedResponse<UserProblemItem>
      >(`/problems`);

      return response.data;
    },
  });
}
