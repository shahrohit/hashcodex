"use client";
import UpdateProblemSlugDialog from "@/app/admin/_components/dialogs/update-problem-slug-dialog";
import ProblemTabs from "@/app/admin/_components/tabs/problem-tabs";
import UpdateProblemActive from "@/app/admin/_components/update-problem-active";
import useQueryProblem from "@/app/admin/_hooks/use-query-problem";
import NotFound from "@/app/not-found";
import NoData from "@/components/no-data";
import { Badge } from "@/components/ui/badge";
import { buttonVariants } from "@/components/ui/button";
import { Spinner } from "@/components/ui/spinner";
import parseDate from "@/lib/date-time-util";
import { ArrowLeft } from "lucide-react";
import Link from "next/link";

export default function ProblemDetailComponent({ slug }: { slug: string }) {
  const { data, isPending, isError } = useQueryProblem(slug);

  if (isPending) return <Spinner />;
  if (isError) return <NotFound />;
  if (!data) return <NoData message="No Problems" />;

  return (
    <div>
      <div>
        <Link href="/admin" className={buttonVariants({ variant: "default" })}>
          <ArrowLeft /> All Problems
        </Link>
      </div>
      <div
        title={data.title}
        className="flex items-center justify-between gap-2"
      >
        <h1 className="text-3xl font-semibold">
          {data.number}. {data.title}
        </h1>
        <div>
          <Badge variant="outline" className="hidden md:flex">
            {parseDate(data.updatedAt)}
          </Badge>

          <UpdateProblemSlugDialog number={data.number} slug={slug} />
          <UpdateProblemActive
            number={data.number}
            active={data.active}
            slug={slug}
          />
        </div>
      </div>

      <ProblemTabs problem={data} />
    </div>
  );
}
