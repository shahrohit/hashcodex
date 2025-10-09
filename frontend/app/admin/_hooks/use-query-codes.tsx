import { ProblemCode, ProblemCodeRecord } from "@/app/admin/_types/problems";
import api from "@/lib/api";
import { QueryKey } from "@/lib/constants";
import Language, { LanguageType } from "@/types/language";
import { useQuery } from "@tanstack/react-query";

export default function useQueryCodes(number: number) {
  return useQuery({
    queryKey: [QueryKey.ADMIN, QueryKey.PROBLEMS, QueryKey.CODES, number],
    queryFn: async () => {
      const response = await api.get<ProblemCode[]>(
        `/admin/problems/codes/${number}`
      );
      const data = response.data;

      const responseData: ProblemCodeRecord = {
        cpp: {
          id: null,
          driverCode: "",
          language: Language.CPP,
          solutionCode: "",
          userCode: "",
        },
        java: {
          id: null,
          driverCode: "",
          language: Language.JAVA,
          solutionCode: "",
          userCode: "",
        },
        python: {
          id: null,
          driverCode: "",
          language: Language.PYTHON,
          solutionCode: "",
          userCode: "",
        },
      };

      data.forEach((code) => {
        responseData[code.language as LanguageType] = code;
      });

      return data;
    },
  });
}
