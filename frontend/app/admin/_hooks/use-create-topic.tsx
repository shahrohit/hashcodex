import { CreateTopicType } from "@/app/admin/_schemas/create-topic-schema";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { TopicItem } from "@/types/problems";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useCreateTopic() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (params: {
      oldSlug: string | undefined;
      body: CreateTopicType;
    }) => {
      if (params.oldSlug)
        await api.patch(`/admin/topics/${params.oldSlug}`, params.body);
      else await api.post("/admin/topics", params.body);
      return params;
    },

    onSuccess: (params: {
      oldSlug: string | undefined;
      body: CreateTopicType;
    }) => {
      queryClient.setQueryData(
        [QueryKey.TOPICS],
        (oldData: TopicItem[] | undefined) => {
          if (!oldData) return oldData;
          if (!params.oldSlug) return [...oldData, params.body];

          return oldData.map((data) => {
            if (data.slug === params.oldSlug) return params.body;
            return data;
          });
        }
      );
    },
  });
}
