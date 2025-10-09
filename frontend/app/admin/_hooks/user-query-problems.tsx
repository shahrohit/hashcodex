import { ProblemItem } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { PaginatedResponse } from "@/types/api-response";
import { useQuery } from "@tanstack/react-query";

export default function useQueryProblems(
  query: string,
  page: number,
  size: number
) {
  return useQuery({
    queryKey: [
      QueryKey.ADMIN,
      QueryKey.PROBLEMS,
      query.toLocaleLowerCase(),
      page,
      size,
    ],
    queryFn: async () => {
      const response = await api.get<PaginatedResponse<ProblemItem>>(
        `/admin/problems`,
        {
          params: { query: query.toLocaleLowerCase(), page, size },
        }
      );
      return response.data;
    },
  });
}
