"use client";

import ProblemListRow from "@/app/(public)/_components/problem-list-row";
import useQueryProblems from "@/app/(public)/hooks/use-query-problems";
import NoData from "@/components/no-data";
import { Spinner } from "@/components/ui/spinner";
import { TableCell, TableRow } from "@/components/ui/table";

export default function FetchProblems() {
  const { isPending, isError, data } = useQueryProblems();
  if (isPending)
    return (
      <MessageComponent>
        <Spinner />
      </MessageComponent>
    );

  if (isError || !data) {
    return (
      <MessageComponent>
        <NoData message="Failed to Load data" />
      </MessageComponent>
    );
  }

  if (data.items?.length == 0) {
    return (
      <MessageComponent>
        <NoData message="No Problems" />
      </MessageComponent>
    );
  }

  return <ProblemListRow problems={data.items} totalPages={data.totalItems} />;
}

function MessageComponent({ children }: { children: React.ReactNode }) {
  return (
    <TableRow>
      <TableCell colSpan={3} className="text-center">
        {children}
      </TableCell>
    </TableRow>
  );
}
