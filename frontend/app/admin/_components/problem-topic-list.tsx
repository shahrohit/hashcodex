"use client";
import useDeleteProblemTopic from "@/app/admin/_hooks/use-delete-problem-topic";
import useQueryProblemTopics from "@/app/admin/_hooks/use-query-problem-topics";
import { Button, buttonVariants } from "@/components/ui/button";
import { getErrorMessage } from "@/lib/error-handler";
import { XIcon } from "lucide-react";
import { toast } from "sonner";

export default function ProblemTopicList({ number }: { number: number }) {
  const api = useQueryProblemTopics(number);
  const deleteApi = useDeleteProblemTopic(number);

  function handleRemove(topicSlug: string) {
    if (window.confirm("Are you sure?")) {
      toast.promise(deleteApi.mutateAsync(topicSlug), {
        loading: "Removing...",
        success: "Removed",
        error: getErrorMessage,
      });
    }
  }

  if (
    api.isPending ||
    api.isError ||
    !api.data ||
    !Array.isArray(api.data) ||
    api.data.length === 0
  )
    return null;

  return (
    <div className="py-4">
      <h2 className="text-muted-foreground mb-2 text-base font-semibold">
        Topic
      </h2>
      <div className="flex flex-wrap gap-2">
        {api.data.map((topic) => {
          return (
            <div
              key={topic.slug}
              className={buttonVariants({
                variant: "outline",
                className: "relative !cursor-default",
              })}
            >
              <span>{topic.name}</span>
              <Button
                onClick={() => handleRemove(topic.slug)}
                className="!bg-destructive/20 text-destructive absolute -top-1 -right-2 size-4"
              >
                <XIcon />
              </Button>
            </div>
          );
        })}
      </div>
    </div>
  );
}
