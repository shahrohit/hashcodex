import CreateTopicFormUI from "@/app/admin/_components/form/create-topic-form-ui";
import useCreateTopic from "@/app/admin/_hooks/use-create-topic";
import createTopicSchema, {
  CreateTopicType,
} from "@/app/admin/_schemas/create-topic-schema";
import { handleFormError } from "@/lib/error-handler";
import { TopicItem } from "@/types/problems";
import { zodResolver } from "@hookform/resolvers/zod";
import { useCallback, useEffect } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";

export default function CreateTopicForm({
  closeDialog,
  topic,
}: {
  closeDialog: () => void;
  topic?: TopicItem | undefined;
}) {
  const form = useForm<CreateTopicType>({
    resolver: zodResolver(createTopicSchema),
    defaultValues: topic || {
      name: "",
      slug: "",
    },
  });

  const title = form.watch("name");

  useEffect(() => {
    const generatedSlug = title.trim().toLocaleLowerCase().replaceAll(" ", "-");
    form.setValue("slug", generatedSlug);
  }, [title, form]);

  const { isPending, mutateAsync } = useCreateTopic();

  const successHandler = useCallback((): string => {
    closeDialog();
    return "Created";
  }, [closeDialog]);

  function onSubmit(values: CreateTopicType) {
    toast.promise(mutateAsync({ oldSlug: topic?.slug, body: values }), {
      loading: "Creating...",
      success: successHandler,
      error: (error) => handleFormError(form, error),
    });
  }

  return (
    <CreateTopicFormUI
      form={form}
      submitPending={isPending}
      onSubmit={onSubmit}
    />
  );
}
