import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import Helpers from "@/lib/helpers";
import { cn } from "@/lib/utils";
import { SubmissionResult, TestcaseResult } from "@/types/problems";

export default function SubmissionResultTab({
  result,
}: {
  result: SubmissionResult | null;
}) {
  if (!result) return null;

  return (
    <div className="space-y-2 px-4 py-2">
      <div className="flex flex-wrap gap-2">
        <h3
          className={cn(
            "mb-2 w-full text-xl font-semibold",
            result.status === "SOLVED"
              ? "text-green-500/80"
              : "text-destructive"
          )}
        >
          {Helpers.getSubmisionMessage(result.status)}
        </h3>
        {result.status === "CTE" ? (
          <CodeError message={result.compileError} />
        ) : (
          <div
            className={cn(
              "w-full rounded-sm border p-2 text-center text-lg",
              result.status === "SOLVED" ? "text-green-500" : "text-destructive"
            )}
          >
            <span>Testcases Passed : </span>
            {result.passed} / {result.total}
          </div>
        )}
        {result.cases && result.cases[0] && <Display data={result.cases[0]} />}
      </div>
    </div>
  );
}

function Display({ data }: { data: TestcaseResult }) {
  return (
    <div className="w-full">
      {data.error ? (
        <CodeError message={data.error} />
      ) : (
        <div className="">
          {data.input?.split("\n").map((input, idx) => {
            return (
              <div key={idx}>
                <Label className="text-muted-foreground">Param {idx + 1}</Label>
                <Input
                  className="rounded-[8px] !ring-0"
                  disabled
                  value={input}
                />
              </div>
            );
          })}

          <div>
            <Label className="text-destructive">Your Output</Label>
            <Input
              className="border-destructive rounded-[8px] !ring-0"
              disabled
              value={data.output ?? ""}
            />
          </div>

          <div>
            <Label className="text-muted-foreground">Expected Output</Label>
            <Input
              className="rounded-[8px] !ring-0"
              disabled
              value={data.expected ?? ""}
            />
          </div>
        </div>
      )}
    </div>
  );
}

function CodeError({ message }: { message: string | null }) {
  return (
    <div className="text-destructive w-full">
      <pre className="text-wrap">{message}</pre>
    </div>
  );
}
