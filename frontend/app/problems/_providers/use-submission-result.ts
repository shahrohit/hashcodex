import { SubmissionContent } from "@/app/problems/_providers/submission-provider";
import { useContext } from "react";

export function useSubmissionResult() {
  const context = useContext(SubmissionContent);
  if (!context) throw new Error("Could not find problem!!");
  return context;
}
