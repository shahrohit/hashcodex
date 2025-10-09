import { ProblemItem } from "@/app/admin/_types/problems";
import DifficultyBadge from "@/components/difficulty-badge";
import NoData from "@/components/no-data";
import { buttonVariants } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Spinner } from "@/components/ui/spinner";
import { Switch } from "@/components/ui/switch";
import { TableCell, TableRow } from "@/components/ui/table";
import { parseDateOnly } from "@/lib/date-time-util";
import { PaginatedResponse } from "@/types/api-response";
import { ExternalLink } from "lucide-react";
import Link from "next/link";

type Props = {
  isPending: boolean;
  isError: boolean;
  data: PaginatedResponse<ProblemItem> | undefined;
  updateActiveCallback: (number: number, active: boolean) => void;
};

export default function ProblemListRow(props: Props) {
  function handleChange(number: number, active: boolean) {
    props.updateActiveCallback(number, active);
  }

  if (props.isPending) {
    return (
      <TableRow>
        <TableCell colSpan={5} className="text-center">
          <Spinner />
        </TableCell>
      </TableRow>
    );
  }

  if (props.isError || !props.data) {
    return (
      <TableRow>
        <TableCell colSpan={5} className="text-center">
          <NoData message="Failed to Load data" />
        </TableCell>
      </TableRow>
    );
  }

  if (props.data?.items?.length == 0) {
    return (
      <TableRow>
        <TableCell colSpan={5} className="text-center">
          <NoData message="No Problems" />
        </TableCell>
      </TableRow>
    );
  }

  return props.data?.items.map((item) => (
    <TableRow
      key={item.number}
      className="*:border-border *:text-center hover:bg-transparent [&>:not(:last-child)]:border-r"
    >
      <TableCell>{item.number}</TableCell>
      <TableCell className="min-w-[250px]">
        <div className="flex items-center justify-between">
          <span className="text-left font-semibold sm:text-base">
            {item.title}
          </span>
          <Link
            href={`/admin/problems/${item.slug}`}
            className={buttonVariants({
              variant: "ghost",
              size: "sm",
              className: "!bg-blue-500/20 !text-blue-500",
            })}
          >
            <ExternalLink />
          </Link>
        </div>
      </TableCell>
      <TableCell className="min-w-[200px]">
        <DifficultyBadge difficulty={item.difficulty} />
      </TableCell>
      <TableCell>
        <div className="inline-flex cursor-pointer items-center gap-2 *:cursor-pointer">
          <Switch
            checked={item.active}
            onCheckedChange={(status) => handleChange(item.number, status)}
          />
          <Label className="text-sm font-medium">
            {item.active ? "Enabled" : "Disabled"}
          </Label>
        </div>
      </TableCell>
      <TableCell className="min-w-[150px]">
        {parseDateOnly(item.updatedAt)}
      </TableCell>
    </TableRow>
  ));
}
