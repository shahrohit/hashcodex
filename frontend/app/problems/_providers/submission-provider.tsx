"use client";

import { useProblem } from "@/app/problems/_providers/use-problem";
import { SubmissionResult } from "@/types/problems";
import { createContext, Dispatch, SetStateAction, useState } from "react";

type submissionContextType = {
  params: string[];
  runResult: SubmissionResult | null;
  submitResult: SubmissionResult | null;
  setRunResult: Dispatch<SetStateAction<SubmissionResult | null>>;
  setSubmitResult: Dispatch<SetStateAction<SubmissionResult | null>>;
};

export const SubmissionContent = createContext<submissionContextType | null>(
  null
);

export default function SubmissionProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [runResult, setRunResult] = useState<SubmissionResult | null>(null);
  const [submitResult, setSubmitResult] = useState<SubmissionResult | null>(
    null
  );
  const { problem } = useProblem();
  const params = problem?.params.split("\n") ?? [];

  return (
    <SubmissionContent.Provider
      value={{
        params,
        runResult,
        setRunResult,
        submitResult,
        setSubmitResult,
      }}
    >
      {children}
    </SubmissionContent.Provider>
  );
}
