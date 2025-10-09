import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { ProblemDetail } from "@/types/problems";
import { useQuery } from "@tanstack/react-query";

export default function useQueryProblems(slug: string) {
  return useQuery({
    queryKey: [QueryKey.USER, QueryKey.PROBLEMS, slug],
    queryFn: async () => {
      const response = await api.get<ProblemDetail>(`/problems/${slug}`);
      return response.data;
    },
  });
}
