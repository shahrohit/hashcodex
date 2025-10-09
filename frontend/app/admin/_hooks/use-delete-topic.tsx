import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { TopicItem } from "@/types/problems";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useDeleteTopic() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (topicSlug: string) => {
      await api.delete(`/admin/topics/${topicSlug}`);
      return topicSlug;
    },

    onSuccess: (topicSlug: string) => {
      queryClient.setQueryData(
        [QueryKey.TOPICS],
        (oldData: TopicItem[] | undefined) => {
          if (!oldData || !Array.isArray(oldData)) return oldData;
          return oldData.filter((data) => {
            return data.slug !== topicSlug;
          });
        }
      );

      queryClient.invalidateQueries({
        queryKey: [QueryKey.ADMIN, QueryKey.PROBLEMS, QueryKey.TOPICS],
        exact: false,
      });
    },
  });
}
