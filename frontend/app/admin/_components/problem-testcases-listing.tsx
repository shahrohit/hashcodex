"use client";
import useDeleteTestcase from "@/app/admin/_hooks/use-delete-testcase";
import useQueryTestcases from "@/app/admin/_hooks/use-query-testcases";
import useUpdateTestcase from "@/app/admin/_hooks/use-update-testcase";
import useUpdateTestcaseSample from "@/app/admin/_hooks/use-update-testcase-sample";
import { ProblemTestcaseItem } from "@/app/admin/_types/problems";
import NoData from "@/components/no-data";
import Pagination from "@/components/pagination";
import TotalDataBadge from "@/components/total-data-badge";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Spinner } from "@/components/ui/spinner";
import { Switch } from "@/components/ui/switch";
import { Textarea } from "@/components/ui/textarea";
import usePagination from "@/hooks/use-pagination";
import { getErrorMessage } from "@/lib/error-handler";
import { Save, Trash2Icon } from "lucide-react";
import { useCallback, useState } from "react";
import { toast } from "sonner";

type ContentType = "INPUT" | "OUTPUT";

export default function ProblemTestcasesListing({
  number,
}: {
  number: number;
}) {
  const pageState = usePagination(2);
  const { isPending, data, isError } = useQueryTestcases(
    number,
    pageState.page,
    pageState.pageSize
  );
  const updateSampleApi = useUpdateTestcaseSample(
    number,
    pageState.page,
    pageState.pageSize
  );
  const updateTestcaseApi = useUpdateTestcase(
    number,
    pageState.page,
    pageState.pageSize
  );
  const deleteTestcaseApi = useDeleteTestcase(number);

  const handleUpdateSample = useCallback(
    (testcaseId: number, sample: boolean) => {
      if (window.confirm("Are you sure?")) {
        toast.promise(updateSampleApi.mutateAsync({ testcaseId, sample }), {
          loading: "Updating...",
          success: "Updated",
          error: getErrorMessage,
        });
      }
    },
    [updateSampleApi]
  );

  const handleUpdateTestcase = useCallback(
    (testcaseId: number, content: string, contentType: ContentType) => {
      if (window.confirm("Are you sure?")) {
        toast.promise(
          updateTestcaseApi.mutateAsync({ testcaseId, content, contentType }),
          {
            loading: "Updating...",
            success: "Updated",
            error: getErrorMessage,
          }
        );
      }
    },
    [updateTestcaseApi]
  );

  const handleDeleteTestcase = useCallback(
    (testcaseId: number) => {
      if (window.confirm("Are you sure?")) {
        toast.promise(deleteTestcaseApi.mutateAsync(testcaseId), {
          loading: "Deleting...",
          success: "Deleted",
          error: getErrorMessage,
        });
      }
    },
    [deleteTestcaseApi]
  );

  if (isPending) return <Spinner />;
  if (isError || !data || !data.items || !Array.isArray(data.items))
    return <NoData message="Failed to load data" />;
  if (data.items.length === 0) return <NoData message="No Testcases" />;

  const start = (data.currentPage ?? 0) * (data.pageSize ?? 0) + 1;
  return (
    <section>
      <div className="relative my-4 w-fit">
        <h1 className="text-lg font-bold">Testcases</h1>
        <TotalDataBadge count={data?.totalItems} />
      </div>

      <div className="flex flex-col gap-5">
        {data.items.map((item, idx) => (
          <div key={item.id} className="relative">
            <TestcaseCard
              testcase={item}
              onUpdateSample={(sample: boolean) =>
                handleUpdateSample(item.id, sample)
              }
              handleUpdateTestcase={(
                content: string,
                contentType: ContentType
              ) => handleUpdateTestcase(item.id, content, contentType)}
            />
            <Button
              size="sm"
              className="!bg-destructive/70 absolute -top-2 right-0"
              onClick={() => handleDeleteTestcase(item.id)}
            >
              <span className="hidden sm:inline">Delete Testcase</span>
              <Trash2Icon />
            </Button>
            <div className="absolute -top-1.5 left-0">
              <Badge>{start + idx}</Badge>
            </div>
          </div>
        ))}
      </div>

      <Pagination pageState={pageState} totalPages={data.totalPages} />
    </section>
  );
}

function TestcaseCard({
  testcase,
  onUpdateSample,
  handleUpdateTestcase,
}: {
  testcase: ProblemTestcaseItem;
  onUpdateSample: (sample: boolean) => void;
  handleUpdateTestcase: (content: string, contentType: ContentType) => void;
}) {
  const [input, setInput] = useState(testcase.input);
  const [output, setOutput] = useState(testcase.output);

  return (
    <Card className="border-foreground/50 w-full py-0">
      <CardContent className="flex flex-col gap-4 p-1 sm:p-4">
        <div className="relative w-full">
          <p className="text-muted-foreground text-center font-semibold">
            Input testcase
          </p>
          <Textarea
            className="h-32 resize-none"
            placeholder="Each input paramters should be seprated by two lines"
            value={input}
            onChange={(e) => setInput(e.target.value)}
          />
          <Button
            variant="secondary"
            disabled={!input || input === testcase.input}
            className="absolute right-1 bottom-1 mt-1"
            onClick={() => handleUpdateTestcase(input, "INPUT")}
          >
            <Save />
          </Button>
        </div>

        <div className="relative w-full">
          <p className="text-muted-foreground text-center font-semibold">
            Expected output
          </p>

          <Textarea
            className="h-20 resize-none"
            placeholder="Each input paramters should be seprated by two lines"
            value={output}
            onChange={(e) => setOutput(e.target.value)}
          />
          <Button
            variant="secondary"
            disabled={output === testcase.output}
            className="absolute right-1 bottom-1 mt-1"
            onClick={() => handleUpdateTestcase(output, "OUTPUT")}
          >
            <Save />
          </Button>
        </div>

        <div className="relative flex w-full items-start gap-2 rounded-md border p-4 shadow-xs outline-none">
          <Switch
            checked={testcase.sample}
            className="order-1 h-4 w-6 cursor-pointer after:absolute after:inset-0 [&_span]:size-3 data-[state=checked]:[&_span]:translate-x-2 data-[state=checked]:[&_span]:rtl:-translate-x-2"
            onCheckedChange={onUpdateSample}
          />
          <div className="grid grow gap-2">
            <Label>Sample Testcases</Label>
            <p className="text-muted-foreground text-xs">
              Make this testcase to visible to user
            </p>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
