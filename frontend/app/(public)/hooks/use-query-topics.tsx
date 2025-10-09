import { QueryKey } from "@/lib/constants";
import publicApi from "@/lib/public-api";
import { TopicItem } from "@/types/problems";
import { useQuery } from "@tanstack/react-query";

export default function useQueryTopics() {
  return useQuery({
    queryKey: [QueryKey.TOPICS],
    queryFn: async () => {
      const response = await publicApi.get<TopicItem[]>(`/problems/topics`);
      return response.data;
    },
  });
}
