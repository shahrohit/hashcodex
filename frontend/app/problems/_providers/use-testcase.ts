import { TestcaseContent } from "@/app/problems/_providers/testcase-provider";
import { useContext } from "react";

export function useTestcase() {
  const context = useContext(TestcaseContent);
  if (!context) throw new Error("Could not find problem!!");
  return context;
}
