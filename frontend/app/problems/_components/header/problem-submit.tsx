"use client";
import { useSubmissionEvent } from "@/app/problems/_hooks/use-submission-event";
import useSubmitCode from "@/app/problems/_hooks/use-submit-code";
import { useCode } from "@/app/problems/_providers/use-code";
import { useSubmissionResult } from "@/app/problems/_providers/use-submission-result";
import { Button } from "@/components/ui/button";
import { getErrorMessage } from "@/lib/error-handler";
import { CloudUploadIcon } from "lucide-react";
import { useEffect, useState } from "react";
import { toast } from "sonner";

export default function ProblemSubmit() {
  const { getLanguageAndCode } = useCode();
  const { isPending, mutateAsync } = useSubmitCode();
  const [corrId, setCorrId] = useState<string | null>(null);
  const [cooldown, setCooldown] = useState<number>(0);

  const { setSubmitResult } = useSubmissionResult();

  const result = useSubmissionEvent(corrId);

  useEffect(() => {
    if (result) {
      if (result.submissionType === "SUBMIT") {
        setSubmitResult(result);
      }
    }
  }, [result, setSubmitResult]);

  useEffect(() => {
    if (cooldown <= 0) return;
    const t = setInterval(() => setCooldown((c) => c - 1), 1000);
    return () => clearInterval(t);
  }, [cooldown]);

  function submitCode() {
    if (cooldown > 0) {
      toast.warning("Please wait sometime to submit your code");
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

    toast.promise(mutateAsync(languageCode), {
      loading: "Wait...",
      success: (correlationId) => {
        if (!correlationId) {
          return { message: "Failed to run", type: "error" };
        }

        setCorrId(correlationId);
        setCooldown(5);

        return { message: "Judging", type: "info" };
      },
      error: getErrorMessage,
    });
  }
  return (
    <Button
      variant="outline"
      disabled={isPending}
      onClick={submitCode}
      className="rounded-[5px] border-none font-semibold !text-green-500"
    >
      <CloudUploadIcon />
      Submit
    </Button>
  );
}
