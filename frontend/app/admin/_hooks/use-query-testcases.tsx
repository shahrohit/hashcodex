import { ProblemTestcaseItem } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { PaginatedResponse } from "@/types/api-response";
import { useQuery } from "@tanstack/react-query";

export default function useQueryTestcases(
  number: number,
  page: number,
  size: number
) {
  return useQuery({
    queryKey: [
      QueryKey.ADMIN,
      QueryKey.PROBLEMS,
      QueryKey.TESTCASES,
      number,
      page,
      size,
    ],
    queryFn: async () => {
      const response = await api.get<PaginatedResponse<ProblemTestcaseItem>>(
        `/admin/problems/testcases/${number}`,
        { params: { page, size } }
      );
      return response.data;
    },
  });
}
