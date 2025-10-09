import { CodeContent } from "@/app/problems/_providers/code-provider";
import { useContext } from "react";

export function useCode() {
  const context = useContext(CodeContent);
  if (!context) throw new Error("Could not find problem!!");
  return context;
}
