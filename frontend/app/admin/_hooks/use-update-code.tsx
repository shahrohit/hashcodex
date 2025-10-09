import { ProblemCode } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import { LanguageType } from "@/types/language";
import { useMutation, useQueryClient } from "@tanstack/react-query";

type CodeType = "DRIVER" | "USER" | "SOLUTION";

export default function useUpdateCode(number: number) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({
      codeType,
      body,
    }: {
      codeType: CodeType;
      body: ProblemCode;
    }) => {
      let code = null;
      if (codeType === "DRIVER") code = body.driverCode;
      else if (codeType === "USER") code = body.userCode;
      else if (codeType === "SOLUTION") code = body.solutionCode;

      await api.patch(
        `/admin/problems/codes/${number}/${body.id}/${codeType.toLowerCase()}`,
        {
          language: body.language,
          code,
        }
      );

      return { language: body.language, codeType, code };
    },

    onSuccess: ({
      language,
      codeType,
      code,
    }: {
      language: LanguageType;
      codeType: CodeType;
      code: string | null;
    }) => {
      if (code == null) return;
      queryClient.setQueryData(
        [QueryKey.ADMIN, QueryKey.PROBLEMS, QueryKey.CODES, number],
        (oldData: ProblemCode[] | undefined) => {
          if (!oldData) return oldData;
          return oldData.map((item) => {
            if (item.language === language) {
              if (codeType === "DRIVER") item.driverCode = code;
              else if (codeType === "USER") item.userCode = code;
              else if (codeType === "SOLUTION") item.solutionCode = code;
              return item;
            }
            return item;
          });
        }
      );
    },
  });
}
