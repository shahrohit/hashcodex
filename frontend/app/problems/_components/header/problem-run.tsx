"use client";
import useRunCode from "@/app/problems/_hooks/use-run-code";
import { useSubmissionEvent } from "@/app/problems/_hooks/use-submission-event";
import { useCode } from "@/app/problems/_providers/use-code";
import { useSubmissionResult } from "@/app/problems/_providers/use-submission-result";
import { useTestcase } from "@/app/problems/_providers/use-testcase";
import { Button } from "@/components/ui/button";
import { Spinner } from "@/components/ui/spinner";
import { getErrorMessage } from "@/lib/error-handler";
import { PlayIcon } from "lucide-react";
import { useEffect, useState } from "react";
import { toast } from "sonner";

export default function ProblemRun() {
  const { deserializeFn } = useTestcase();
  const { getLanguageAndCode } = useCode();
  const { isPending, mutateAsync } = useRunCode();
  const [corrId, setCorrId] = useState<string | null>(null);
  const { setRunResult } = useSubmissionResult();
  const [cooldown, setCooldown] = useState<number>(0);
  const result = useSubmissionEvent(corrId);

  function runCode() {
    if (cooldown > 0) {
      toast.warning("Please wait sometime to run your code");
      return;
    }
    const testcases = deserializeFn();
    if (!testcases) {
      toast.warning("Missing testcase");
      return;
    }

    const languageCode = getLanguageAndCode();
    if (!languageCode) return;
    if (!languageCode.language) {
      toast.warning("Please select a language");
      return;
    } else if (!languageCode.code) {
      toast.warning("Empty code");
      return;
    }

    toast.promise(mutateAsync({ ...languageCode, testcases }), {
      loading: "Wait...",
      success: (correlationId) => {
        if (!correlationId) {
          return { message: "Failed to run", type: "error" };
        }
        setCooldown(5);
        setCorrId(correlationId);

        return { message: "Judging", type: "info" };
      },
      error: getErrorMessage,
    });
  }

  useEffect(() => {
    if (result) {
      if (result.submissionType === "RUN") {
        setRunResult(result);
      }
    }
  }, [result, setRunResult]);

  useEffect(() => {
    if (cooldown <= 0) return;
    const t = setInterval(() => setCooldown((c) => c - 1), 1000);
    return () => clearInterval(t);
  }, [cooldown]);

  return (
    <Button
      variant="outline"
      className="rounded-[5px] border-none font-semibold"
      onClick={runCode}
      disabled={isPending}
    >
      {isPending ? (
        <Spinner />
      ) : (
        <>
          <PlayIcon />
          <span className="hidden sm:inline">Run</span>
        </>
      )}
    </Button>
  );
}
