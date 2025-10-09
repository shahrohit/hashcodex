"use client";

import { useProblem } from "@/app/problems/_providers/use-problem";
import {
  createContext,
  Dispatch,
  SetStateAction,
  useCallback,
  useEffect,
  useState,
} from "react";

type ErrorType = { id: number; paramId: number };
type TestcaseContextType = {
  id: number;
  setId: Dispatch<SetStateAction<number>>;
  params: string[];
  testCases: string[][];
  setTestCases: Dispatch<SetStateAction<string[][]>>;
  resetFn: () => void;
  error: ErrorType | null;
  deserializeFn: () => string[] | null;
};

export const TestcaseContent = createContext<TestcaseContextType | null>(null);

export default function TestcaseProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [error, setError] = useState<ErrorType | null>(null);
  const { problem } = useProblem();
  const [id, setId] = useState(0);
  const params = problem?.params.split("\n") ?? [];

  const initilaValue = useCallback(() => {
    return (
      problem?.testcases.map((testcase) => {
        return testcase.split("\n");
      }) ?? []
    );
  }, [problem]);

  const [testCases, setTestCases] = useState<string[][]>(initilaValue());

  useEffect(() => {
    setTestCases(initilaValue());
  }, [initilaValue]);

  function resetFn() {
    setTestCases(initilaValue);
    setId(0);
  }

  const deserializeFn = useCallback(() => {
    for (let i = 0; i < testCases.length; i++) {
      const idx = testCases[i].findIndex((testcase) => !testcase);
      if (idx != -1) {
        setError({ id: i, paramId: idx });
        return null;
      }
    }
    setError(null);

    return testCases.map((testcase) => {
      return testcase.join("\n");
    });
  }, [testCases]);

  return (
    <TestcaseContent.Provider
      value={{
        testCases,
        setTestCases,
        params,
        id,
        setId,
        resetFn,
        error,
        deserializeFn,
      }}
    >
      {children}
    </TestcaseContent.Provider>
  );
}
