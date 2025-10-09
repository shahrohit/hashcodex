"use client";

import NotFound from "@/app/not-found";
import useQueryProblems from "@/app/problems/_hooks/use-query-problem";
import { Spinner } from "@/components/ui/spinner";
import { ProblemDetail } from "@/types/problems";
import { createContext } from "react";

type ProblemContextType = {
  slug: string;
  problem: ProblemDetail;
};

export const ProblemContent = createContext<ProblemContextType | null>(null);

export default function ProblemProvider({
  slug,
  children,
}: {
  slug: string;
  children: React.ReactNode;
}) {
  const { isPending, data, isError } = useQueryProblems(slug);
  if (isPending) return <Spinner />;
  if (isError || !data) return <NotFound />;

  return (
    <ProblemContent.Provider value={{ problem: data, slug }}>
      {children}
    </ProblemContent.Provider>
  );
}
