import { Button } from "@/components/ui/button";
import { PaginationState } from "@/hooks/use-pagination";
import {
  ChevronFirstIcon,
  ChevronLastIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
} from "lucide-react";

export default function Pagination({
  pageState,
  totalPages,
}: {
  pageState: PaginationState;
  totalPages: number;
}) {
  return (
    <div className="flex items-center justify-end gap-1 p-0.5 *:rounded-[8px]">
      <Button
        variant="outline"
        size="sm"
        disabled={pageState.page == 0}
        onClick={pageState.firstPage}
      >
        <ChevronFirstIcon size={16} aria-hidden="true" />
      </Button>
      <Button
        variant="outline"
        size="sm"
        disabled={pageState.page == 0}
        onClick={pageState.prevPage}
      >
        <ChevronLeftIcon size={16} aria-hidden="true" />
      </Button>
      <Button
        variant="outline"
        size="sm"
        disabled={pageState.page >= totalPages - 1}
        onClick={pageState.nextPage}
      >
        <ChevronRightIcon size={16} aria-hidden="true" />
      </Button>
      <Button
        variant="outline"
        size="sm"
        disabled={pageState.page >= totalPages - 1}
        onClick={() => pageState.jumpPage(totalPages - 1)}
      >
        <ChevronLastIcon size={16} aria-hidden="true" />
      </Button>
    </div>
  );
}
