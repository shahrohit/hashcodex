"use client";

import useQueryTopics from "@/app/(public)/hooks/use-query-topics";
import NoData from "@/components/no-data";
import { buttonVariants } from "@/components/ui/button";
import { Spinner } from "@/components/ui/spinner";
import { TopicItem } from "@/types/problems";
import Link from "next/link";

export default function TopicListing() {
  return (
    <section className="border rounded-sm">
      <h1 className="text-xl font-semibold border-b p-2 text-center">Topics</h1>
      <div className="flex flex-wrap gap-2 p-2">
        <FetchTopicCompnent />
      </div>
    </section>
  );
}

function FetchTopicCompnent() {
  const { isPending, isError, data } = useQueryTopics();
  if (isPending) return <Spinner />;
  if (isError || !data || !Array.isArray(data)) return null;
  if (data.length === 0) return <NoData message="No Topics" />;
  return <TopicListComponent topics={data} />;
}

function TopicListComponent({ topics }: { topics: TopicItem[] }) {
  return topics.map((topic) => {
    return (
      <div key={topic.slug} className="relative flex-1">
        <Link
          href={`/topics/${topic.slug}`}
          className={buttonVariants({
            variant: "outline",
            className: "w-full",
          })}
        >
          {topic.name}
        </Link>
      </div>
    );
  });
}
