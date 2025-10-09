import { ProblemSolvedStatusType } from "@/types/problem-solved-status";
import { BadgeCheck, CircleDotDashed } from "lucide-react";

export default function ProblemSolvedStatus({
  status,
}: {
  status: string | ProblemSolvedStatusType;
}) {
  if (status === "SOLVED")
    return (
      <BadgeCheck className="size-7 rounded-full bg-green-500/20 p-1 text-green-500" />
    );
  else if (status === "ATTEMPTED")
    return (
      <CircleDotDashed className="size-7 rounded-full bg-yellow-500/20 p-1 text-yellow-500" />
    );
  return null;
}
