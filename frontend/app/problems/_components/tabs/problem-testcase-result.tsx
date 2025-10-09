import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import Helpers from "@/lib/helpers";
import { cn } from "@/lib/utils";
import { SubmissionResult, TestcaseResult } from "@/types/problems";
import { BadgeCheck, XCircle } from "lucide-react";
import { useState } from "react";

export default function ProblemTestcaseResult({
  result,
}: {
  result: SubmissionResult | null;
}) {
  const [id, setId] = useState(0);
  if (!result) {
    return (
      <div className="text-muted-foreground my-4 text-center text-lg font-semibold">
        Please run the testcases
      </div>
    );
  }

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

        {result.status === "CTE" && <CodeError message={result.compileError} />}
        {result.errorMessage && <CodeError message={result.errorMessage} />}

        {result.cases?.map((tcase, idx) => {
          return (
            <div className="relative" key={idx}>
              <Button
                variant={id === idx ? "secondary" : "outline"}
                className={cn(
                  "rounded-[8px] border font-semibold",

                  //   tcase.status === "SOLVED" ? "!bg-green-500/50" : "!bg-destructive",
                  id === idx && ""
                )}
                onClick={() => setId(idx)}
              >
                {tcase.status === "SOLVED" ? (
                  <BadgeCheck className="text-green-500" />
                ) : (
                  <XCircle className="text-destructive" />
                )}
                Case {idx + 1}
              </Button>
            </div>
          );
        })}
      </div>

      {result.cases && result.cases[id] && <Display data={result.cases[id]} />}
    </div>
  );
}

function Display({ data }: { data: TestcaseResult }) {
  return (
    <div>
      {data.error ? (
        <CodeError message={data.error} />
      ) : (
        <div>
          {data.input?.split("\n").map((input, idx) => {
            return (
              <div key={idx} className="space-y-2">
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
            <Label className="text-muted-foreground">Your Output</Label>
            <Input
              className="rounded-[8px] !ring-0"
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
