"use client";

import useVerifyAccount from "@/app/auth/_query/use-verify-account";
import ErrorMessage from "@/components/error-message";
import { Button } from "@/components/ui/button";
import { Spinner } from "@/components/ui/spinner";
import { getError } from "@/lib/error-handler";
import { ErrorCode } from "@/types/error-response";
import { useSearchParams } from "next/navigation";
import { useCallback, useState } from "react";
import { toast } from "sonner";

function VerifyAccountDialogMode() {
  const searchParams = useSearchParams();
  const token = searchParams.get("token");
  const publicId = searchParams.get("publicId");

  const [error, setError] = useState<string | null>(
    token && publicId ? null : "Verification link expired or broken"
  );

  const { isPending, mutateAsync, isSuccess } = useVerifyAccount();

  const errorHandler = useCallback((err: Error | undefined) => {
    const error = getError(err);
    if (error.code !== ErrorCode.CUSTOM) setError(error.message);
    return error.message;
  }, []);

  function confirmVerification() {
    if (!token) toast.error("Missing token");
    else if (!publicId) toast.error("Missing Id");
    else
      toast.promise(mutateAsync({ token, publicId }), {
        loading: "Verifying...",
        success: "Verified",
        error: errorHandler,
      });
  }

  if (isPending) return <Spinner />;
  if (error) return <ErrorMessage message={error} />;

  return isSuccess ? (
    <div className="flex flex-col text-center">
      <span className="font-semibold text-green-300">Account Verified</span>
      <span className="text-muted-foreground text-sm">
        You may now close this window
      </span>
    </div>
  ) : (
    <Button onClick={confirmVerification}>Confirm Verification</Button>
  );
}

export default VerifyAccountDialogMode;
