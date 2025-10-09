import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { TopicItem } from "@/types/problems";
import { useQuery } from "@tanstack/react-query";

export default function useQueryProblemTopics(number: number) {
  return useQuery({
    queryKey: [QueryKey.ADMIN, QueryKey.PROBLEMS, QueryKey.TOPICS, number],
    queryFn: async () => {
      const response = await api.get<TopicItem[]>(
        `/admin/problems/${number}/topics`
      );
      return response.data;
    },
  });
}
