"use client";
import { useTestcase } from "@/app/problems/_providers/use-testcase";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { cn } from "@/lib/utils";
import { PlusIcon, RotateCcwIcon, XIcon } from "lucide-react";
import { useEffect } from "react";

export default function ProblemTestcaseTab() {
  const { error, params, testCases, setTestCases, id, setId, resetFn } =
    useTestcase();
  const hasMin = testCases.length === 1;
  const hasMax = testCases.length === 10;

  useEffect(() => {
    if (error) {
      setId(error.id);
    }
  }, [error, setId]);

  function addNewCase() {
    if (hasMax) return;
    setId(testCases.length);
    setTestCases([...testCases, [...testCases[id]]]);
  }

  function deleteCase(deleteIdx: number) {
    if (hasMin) return;
    if (id >= deleteIdx) {
      setId(Math.min(testCases.length - 2, id));
    }
    setTestCases((old) => {
      return old.filter((_, oldIdx) => oldIdx !== deleteIdx);
    });
  }

  function updateCase(paramId: number, value: string) {
    testCases[id][paramId] = value;
    setTestCases([...testCases]);
  }
  return (
    <div className="space-y-2 px-4 py-2">
      <div className="flex flex-wrap gap-2">
        {testCases.map((_, idx) => {
          return (
            <div className="relative" key={idx}>
              <Button
                variant={id === idx ? "default" : "outline"}
                className="rounded-[8px] border-none"
                onClick={() => setId(idx)}
              >
                Case {idx + 1}
              </Button>
              {!hasMin && (
                <Button
                  variant="ghost"
                  onClick={() => deleteCase(idx)}
                  className="absolute -top-1.5 -right-2.5 !size-4"
                >
                  <XIcon className="size-3" />
                </Button>
              )}
            </div>
          );
        })}
        {!hasMax && (
          <div>
            <Button
              variant="outline"
              className="rounded-[8px] border-none"
              onClick={addNewCase}
            >
              <PlusIcon />
            </Button>
          </div>
        )}

        <Button
          variant="destructive"
          size="sm"
          className="rounded-[8px]"
          onClick={resetFn}
        >
          <RotateCcwIcon />
        </Button>
      </div>

      <div className="flex flex-col gap-4">
        {testCases[id]?.map((currentCase, idx) => {
          return (
            <div key={idx} className="space-y-2">
              <Label className="text-muted-foreground">{params[idx]}=</Label>
              <Input
                className={cn(
                  "rounded-[8px] !ring-0",
                  error?.id === id && error.paramId === idx
                    ? "border-foreground/40"
                    : "border-border"
                )}
                value={currentCase}
                onChange={(e) => updateCase(idx, e.target.value)}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
}
