"use client";

import useQuerySubmissions from "@/app/problems/_hooks/use-query-submissions";
import { useProblem } from "@/app/problems/_providers/use-problem";
import NoData from "@/components/no-data";
import { Spinner } from "@/components/ui/spinner";
import { parseDateOnly } from "@/lib/date-time-util";
import Helpers from "@/lib/helpers";
import { cn } from "@/lib/utils";
import { LanguageIconMap } from "@/store/language-map";
import { LanguageType } from "@/types/language";
import { BadgeCheckIcon, XCircle } from "lucide-react";

export default function ProblemSubmissionTab() {
  const { problem } = useProblem();
  const { isPending, data, isError } = useQuerySubmissions(problem.number);
  if (isPending) return <Spinner />;
  if (!data || isError || !Array.isArray(data))
    return <NoData message="Failed to load submission" />;

  if (data.length === 0) return <NoData message="No submissions" />;

  return (
    <div className="flex max-w-xl flex-col gap-2 p-2">
      {data.map((sub, idx) => {
        const Icon = LanguageIconMap[sub.language as LanguageType];
        return (
          <div
            key={idx}
            className="flex flex-wrap items-center justify-between rounded-[8px] border p-3"
          >
            <div
              className={cn(
                "flex w-[200px] gap-2 text-base font-semibold",

                sub.status === "SOLVED" ? "text-green-500" : "text-destructive"
              )}
            >
              {sub.status === "SOLVED" ? <BadgeCheckIcon /> : <XCircle />}

              {Helpers.getSubmisionMessage(sub.status)}
            </div>
            <div className="bg-muted flex gap-2 rounded-[8px] p-2">
              <Icon className="size-5" />
              <span className="text-sm">{sub.language}</span>
            </div>
            <span className="text-muted-foreground text-xs font-semibold">
              {parseDateOnly(sub.submittedAt)}
            </span>
          </div>
        );
      })}
    </div>
  );
}
