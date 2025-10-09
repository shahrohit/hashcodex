import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { TopicItem } from "@/types/problems";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useAddTopics(number: number) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (body: TopicItem) => {
      await api.post(`/admin/problems/${number}/topics/${body.slug}`);
      return body;
    },

    onSuccess: (data: TopicItem) => {
      queryClient.setQueryData(
        [QueryKey.ADMIN, QueryKey.PROBLEMS, QueryKey.TOPICS, number],
        (oldData: TopicItem[] | undefined) => {
          if (!oldData) return oldData;
          return [...oldData, data];
        }
      );
    },
  });
}
