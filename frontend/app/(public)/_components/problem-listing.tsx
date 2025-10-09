import FetchProblems from "@/app/(public)/_components/fetch-problems";
import {
  Table,
  TableBody,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

export default function ProblemListing() {
  return (
    <section className="">
      <div className="my-2 flex flex-col justify-between gap-2 sm:flex-row">
        <h1 className="relative mb-2 w-fit text-2xl font-semibold">
          <span>Problems</span>
        </h1>
      </div>
      <div className="bg-background overflow-hidden rounded-[8px] border">
        <Table>
          <TableHeader>
            <TableRow className="bg-muted/50 *:font-semibold text-base *:border-border *:text-center [&>:not(:last-child)]:border-r">
              <TableHead></TableHead>
              <TableHead>Title</TableHead>
              <TableHead>Difficulty</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody className="[&_td:first-child]:rounded-l-lg [&_td:last-child]:rounded-r-lg">
            <FetchProblems />
          </TableBody>
        </Table>
      </div>
    </section>
  );
}
