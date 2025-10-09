import { ProblemDetail } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { useQuery } from "@tanstack/react-query";

export default function useQueryProblem(slug: string) {
  return useQuery({
    queryKey: [QueryKey.ADMIN, QueryKey.PROBLEMS, slug],
    queryFn: async () => {
      const response = await api.get<ProblemDetail>("/admin/problems/" + slug);
      return response.data;
    },
  });
}
