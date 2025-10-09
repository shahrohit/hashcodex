import DifficultyBadge from "@/components/difficulty-badge";
import Pagination from "@/components/pagination";
import ProblemSolvedStatus from "@/components/problem-solved-status";
import { TableCell, TableRow } from "@/components/ui/table";
import usePagination from "@/hooks/use-pagination";
import { UserProblemItem } from "@/types/problems";
import Link from "next/link";

export default function ProblemListRow({
  problems,
  totalPages,
}: {
  problems: UserProblemItem[];
  totalPages: number;
}) {
  const pageState = usePagination(10);
  return (
    <>
      {problems.map((problem) => (
        <TableRow
          key={problem.number}
          className="*:border-border *:text-center hover:bg-transparent [&>:not(:last-child)]:border-r"
        >
          <TableCell className="max-w-[25px]">
            <div className="flex justify-center">
              <ProblemSolvedStatus status={problem.status} />
            </div>
          </TableCell>
          <TableCell className="min-w-[250px] !text-left">
            <Link
              href={`/problems/${problem.slug}`}
              className=" text-left font-semibold sm:text-base hover:text-primary"
            >
              {problem.number}. {problem.title}
            </Link>
          </TableCell>
          <TableCell className="min-w-[200px]">
            <DifficultyBadge difficulty={problem.difficulty} />
          </TableCell>
        </TableRow>
      ))}
      <TableRow>
        <TableCell colSpan={3} className="text-center">
          <Pagination pageState={pageState} totalPages={totalPages} />
        </TableCell>
      </TableRow>
    </>
  );
}
