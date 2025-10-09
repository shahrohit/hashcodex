"use client";
import useQueryTopics from "@/app/(public)/hooks/use-query-topics";
import AddTopicDialog from "@/app/admin/_components/add-topic-dialog";
import UpdateTopicDialog from "@/app/admin/_components/update-topic-dialog";
import useDeleteTopic from "@/app/admin/_hooks/use-delete-topic";
import NoData from "@/components/no-data";
import TotalDataBadge from "@/components/total-data-badge";
import { Button } from "@/components/ui/button";
import { Spinner } from "@/components/ui/spinner";
import { getErrorMessage } from "@/lib/error-handler";
import { TopicItem } from "@/types/problems";
import { XIcon } from "lucide-react";
import { useState } from "react";
import { toast } from "sonner";

export default function TopicListing() {
  const api = useQueryTopics();
  if (api.isPending) return <Spinner />;
  // if (api.isError || !api.data || !Array.isArray(api.data)) return "null";

  return (
    <section className="my-5">
      <div className="my-2 flex justify-between gap-2">
        <h1 className="relative mb-2 w-fit text-2xl font-semibold">
          <span>Topics</span>
          <TotalDataBadge count={api.data?.length ?? 0} />
        </h1>
        <div className="flex gap-2">
          <AddTopicDialog />
        </div>
      </div>

      <div>
        {!api.data || api.data.length === 0 ? (
          <NoData message="No Topics" />
        ) : (
          <TopicList topics={api.data} />
        )}
      </div>
    </section>
  );
}

function TopicList({ topics }: { topics: TopicItem[] }) {
  const [topic, setTopic] = useState<TopicItem | undefined>(undefined);
  const api = useDeleteTopic();

  function handleDeleteTopic(topicSlug: string) {
    if (window.confirm("Are you sure?")) {
      toast.promise(api.mutateAsync(topicSlug), {
        loading: "Deleting...",
        success: "Deleted",
        error: getErrorMessage,
      });
    }
  }
  return (
    <div>
      <UpdateTopicDialog topic={topic} setTopic={setTopic} />
      <div className="flex flex-wrap gap-2">
        {topics.map((topic) => {
          return (
            <div key={topic.slug} className="relative flex-1">
              <Button
                variant="outline"
                className="w-full"
                onClick={() => setTopic(topic)}
              >
                {topic.name}
              </Button>
              <Button
                onClick={() => handleDeleteTopic(topic.slug)}
                className="!bg-destructive/20 text-destructive absolute -top-1 -right-1 size-4"
              >
                <XIcon className="size-4" />
              </Button>
            </div>
          );
        })}
      </div>
    </div>
  );
}
