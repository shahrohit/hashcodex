"use client";

import CreateTestcaseFormUI from "@/app/admin/_components/form/create-testcase-form-ui";
import useCreateTestcase from "@/app/admin/_hooks/use-create-testcase";
import createTestcaseSchema, {
  CreateTestcaseType,
} from "@/app/admin/_schemas/create-testcase-schema";
import { handleFormError } from "@/lib/error-handler";
import { zodResolver } from "@hookform/resolvers/zod";
import { useCallback } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";

export default function CreateProblemTestcaseForm({
  number,
  closeDialog,
}: {
  number: number;
  closeDialog: () => void;
}) {
  const form = useForm<CreateTestcaseType>({
    resolver: zodResolver(createTestcaseSchema),
    defaultValues: {
      input: "",
      output: "",
      sample: false,
    },
  });

  const { isPending, mutateAsync } = useCreateTestcase(number);

  const successHandler = useCallback((): string => {
    closeDialog();
    return "Created";
  }, [closeDialog]);

  function onSubmit(values: CreateTestcaseType) {
    toast.promise(mutateAsync(values), {
      loading: "Creating...",
      success: successHandler,
      error: (error) => handleFormError(form, error),
    });
  }

  return (
    <CreateTestcaseFormUI
      form={form}
      submitPending={isPending}
      onSubmit={onSubmit}
    />
  );
}
