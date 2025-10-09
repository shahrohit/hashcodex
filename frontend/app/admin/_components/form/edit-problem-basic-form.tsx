"use client";

import EditProblemBasicFormUI from "@/app/admin/_components/form/edit-problem-form-ui";
import useUpdateProblemBasic from "@/app/admin/_hooks/use-update-problem-basic";
import updateProblemBasicSchema, {
  UpdateProblemBasicType,
} from "@/app/admin/_schemas/update-problem-basic-schema";
import { ProblemDetail } from "@/app/admin/_types/problems";
import { handleFormError } from "@/lib/error-handler";
import { ProblemDifficultyType } from "@/types/problem-difficulty";
import { zodResolver } from "@hookform/resolvers/zod";
import { useCallback } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";

export default function EditProblemBasicForm({
  problem,
}: {
  problem: ProblemDetail;
}) {
  const { mutateAsync, isPending } = useUpdateProblemBasic(
    problem.slug,
    problem.number
  );

  const form = useForm<UpdateProblemBasicType>({
    resolver: zodResolver(updateProblemBasicSchema),
    defaultValues: {
      title: problem.title,
      difficulty: problem.difficulty as ProblemDifficultyType,
      params: problem.params,
    },
  });

  const onSubmit = useCallback(
    (values: UpdateProblemBasicType) => {
      toast.promise(mutateAsync(values), {
        loading: "Updating...",
        success: (data) => {
          form.reset(data);
          return "Updated";
        },
        error: (error) => handleFormError(form, error),
      });
    },
    [form, mutateAsync]
  );

  return (
    <EditProblemBasicFormUI
      form={form}
      submitPending={isPending}
      onSubmit={onSubmit}
    />
  );
}
