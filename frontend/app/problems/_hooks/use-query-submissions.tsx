import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { SubmissionItem } from "@/types/problems";
import { useQuery } from "@tanstack/react-query";

export default function useQuerySubmissions(number: number) {
  return useQuery({
    queryKey: [QueryKey.USER, QueryKey.PROBLEMS, QueryKey.SUBMISSIONS, number],
    queryFn: async () => {
      const response = await api.get<SubmissionItem[]>(
        `/problems/submissions/all/${number}`
      );
      return response.data;
    },
  });
}
