import { ProblemDetail, ProblemItem } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { PaginatedResponse } from "@/types/api-response";
import { useMutation, useQueryClient } from "@tanstack/react-query";

type Props = {
  query: string;
  page: number;
  size: number;
};

export default function useUpdateProblemActive(slug: string) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({
      number,
      active,
    }: {
      number: number;
      active: boolean;
    }) => {
      await api.patch(`/admin/problems/${number}/active?active=${active}`);
      return active;
    },

    onSuccess: (active: boolean) => {
      queryClient.setQueryData(
        [QueryKey.ADMIN, QueryKey.PROBLEMS, slug],
        (oldData: ProblemDetail | undefined) => {
          if (!oldData) return oldData;
          return {
            ...oldData,
            active,
          };
        }
      );
    },
  });
}

export function useUpdateProblemActivePage({ query, page, size }: Props) {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({
      number,
      active,
    }: {
      number: number;
      active: boolean;
    }) => {
      await api.patch(`/admin/problems/${number}/active?active=${active}`);
      return { number, active };
    },

    onSuccess: ({ number, active }: { number: number; active: boolean }) => {
      queryClient.setQueryData(
        [
          QueryKey.ADMIN,
          QueryKey.PROBLEMS,
          query.toLocaleLowerCase(),
          page,
          size,
        ],
        (oldData: PaginatedResponse<ProblemItem> | undefined) => {
          if (!oldData) return oldData;
          return {
            ...oldData,
            items: oldData.items.map((problem) =>
              problem.number === number ? { ...problem, active } : problem
            ),
          };
        }
      );
    },
  });
}
