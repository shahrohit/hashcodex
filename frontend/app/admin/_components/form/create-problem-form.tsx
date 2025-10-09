import CreateProblemFormUI from "@/app/admin/_components/form/create-problem-form-ui";
import useCreateProblem from "@/app/admin/_hooks/use-create-problem";
import createProblemSchema, {
  CreateProblemType,
} from "@/app/admin/_schemas/create-problem-schema";
import { handleFormError } from "@/lib/error-handler";
import { zodResolver } from "@hookform/resolvers/zod";
import { useCallback, useEffect } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";

export default function CreateProblemForm({
  closeDialog,
}: {
  closeDialog: () => void;
}) {
  const form = useForm<CreateProblemType>({
    resolver: zodResolver(createProblemSchema),
    defaultValues: {
      title: "",
      slug: "",
      description: "",
      params: "",
      difficulty: undefined,
    },
  });

  const title = form.watch("title");

  useEffect(() => {
    const generatedSlug = title.trim().toLocaleLowerCase().replaceAll(" ", "-");
    form.setValue("slug", generatedSlug);
  }, [title, form]);

  const { isPending, mutateAsync } = useCreateProblem();

  const successHandler = useCallback((): string => {
    closeDialog();
    return "Created";
  }, [closeDialog]);

  function onSubmit(values: CreateProblemType) {
    toast.promise(mutateAsync(values), {
      loading: "Creating...",
      success: successHandler,
      error: (error) => handleFormError(form, error),
    });
  }

  return (
    <CreateProblemFormUI
      form={form}
      submitPending={isPending}
      onSubmit={onSubmit}
    />
  );
}
