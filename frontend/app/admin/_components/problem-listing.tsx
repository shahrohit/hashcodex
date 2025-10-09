"use client";
import ProblemListRow from "@/app/admin/_components/problem-list-row";
import { useUpdateProblemActivePage } from "@/app/admin/_hooks/use-update-problem-active";
import useQueryProblems from "@/app/admin/_hooks/user-query-problems";
import DebounceSearch from "@/components/debounce-search";
import Pagination from "@/components/pagination";
import TotalDataBadge from "@/components/total-data-badge";
import {
  Table,
  TableBody,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import usePagination from "@/hooks/use-pagination";
import { getErrorMessage } from "@/lib/error-handler";
import { useState } from "react";
import { toast } from "sonner";

export default function ProblemListing() {
  const [query, setQuery] = useState("");
  const pageState = usePagination(10);

  const api = useQueryProblems(query, pageState.page, pageState.pageSize);
  const updateStatusApi = useUpdateProblemActivePage({
    query,
    page: pageState.page,
    size: pageState.pageSize,
  });

  const handleUpdateActive = (number: number, active: boolean) => {
    if (window.confirm("Are you sure?")) {
      toast.promise(updateStatusApi.mutateAsync({ number, active }), {
        loading: "Updating...",
        success: active ? "Enabled" : "Disabled",
        error: getErrorMessage,
      });
    }
  };

  return (
    <section>
      <div className="my-2 flex flex-col justify-between gap-2 sm:flex-row">
        <h1 className="relative mb-2 w-fit text-2xl font-semibold">
          <span>Problems</span>
          <TotalDataBadge count={api.data?.totalItems ?? 0} />
        </h1>
        <div className="flex gap-2">
          <DebounceSearch onSearch={setQuery} placeholder="Search Problems" />
        </div>
      </div>
      <div className="bg-background overflow-hidden rounded-md border">
        <Table className="border-b">
          <TableHeader>
            <TableRow className="bg-muted/50 *:border-border *:text-center [&>:not(:last-child)]:border-r">
              <TableHead>Problem</TableHead>
              <TableHead>Title</TableHead>
              <TableHead>Difficulty</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>Last Updated At</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody className="[&_td:first-child]:rounded-l-lg [&_td:last-child]:rounded-r-lg">
            <ProblemListRow
              updateActiveCallback={handleUpdateActive}
              isPending={api.isPending}
              isError={api.isError}
              data={api.data}
            />
          </TableBody>
        </Table>
        {api?.data && (
          <Pagination pageState={pageState} totalPages={api.data.totalPages} />
        )}
      </div>
    </section>
  );
}
