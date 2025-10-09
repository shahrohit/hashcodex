import { ProblemTestcaseItem } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { PaginatedResponse } from "@/types/api-response";
import { useMutation, useQueryClient } from "@tanstack/react-query";

type Props = {
  number: number;
  page: number;
  size: number;
  testcaseId: number;
  sample: boolean;
};

export default function useUpdateTestcaseSample(
  number: number,
  page: number,
  size: number
) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({
      testcaseId,
      sample,
    }: {
      testcaseId: number;
      sample: boolean;
    }) => {
      await api.patch(
        `/admin/problems/testcases/${number}/${testcaseId}/sample?sample=${sample}`
      );
      return { number, testcaseId, page, size, sample };
    },

    onSuccess: ({ number, testcaseId, page, size, sample }: Props) => {
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
                return {
                  ...item,
                  sample,
                };
              } else return item;
            }),
          };
        }
      );
    },
  });
}
