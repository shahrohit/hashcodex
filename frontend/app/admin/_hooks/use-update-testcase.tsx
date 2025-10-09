import { ProblemTestcaseItem } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { PaginatedResponse } from "@/types/api-response";
import { useMutation, useQueryClient } from "@tanstack/react-query";

type Props = {
  number: number;
  page: number;
  size: number;
  content: string;
  testcaseId: number;
  contentType: "INPUT" | "OUTPUT";
};

export default function useUpdateTestcase(
  number: number,
  page: number,
  size: number
) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({
      testcaseId,
      content,
      contentType,
    }: {
      testcaseId: number;
      content: string;
      contentType: "INPUT" | "OUTPUT";
    }) => {
      await api.patch(
        `/admin/problems/testcases/${number}/${testcaseId}/${contentType.toLocaleLowerCase()}`,
        { content }
      );
      return { number, testcaseId, page, size, content, contentType };
    },

    onSuccess: ({
      number,
      testcaseId,
      page,
      size,
      content,
      contentType,
    }: Props) => {
      queryClient.setQueryData(
        [
          QueryKey.ADMIN,
          QueryKey.PROBLEMS,
          QueryKey.TESTCASES,
          number,
          page,
          size,
        ],
        (oldData: PaginatedResponse<ProblemTestcaseItem> | undefined) => {
          if (!oldData || !Array.isArray(oldData.items)) return oldData;

          return {
            ...oldData,
            items: oldData.items.map((item) => {
              if (item.id === testcaseId) {
                if (contentType === "INPUT") item.input = content;
                else if (contentType === "OUTPUT") item.output = content;
              }
              return item;
            }),
          };
        }
      );
    },
  });
}
