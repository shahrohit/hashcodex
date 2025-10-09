import { ProblemContent } from "@/app/problems/_providers/problem-provider";
import { useContext } from "react";

export function useProblem() {
  const context = useContext(ProblemContent);
  if (!context) throw new Error("Could not find problem!!");
  return context;
}
