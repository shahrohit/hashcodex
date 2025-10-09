"use client";
import ProblemTestcaseResult from "@/app/problems/_components/tabs/problem-testcase-result";
import ProblemTestcaseTab from "@/app/problems/_components/tabs/problem-testcase-tab";
import ProblemTestcaseTabHeader from "@/app/problems/_components/tabs/problem-testcase-tab-header";
import { useSubmissionResult } from "@/app/problems/_providers/use-submission-result";
import { ScrollArea, ScrollBar } from "@/components/ui/scroll-area";
import { Tabs, TabsContent } from "@/components/ui/tabs";
import { useEffect, useState } from "react";

export default function ProblemTestcasePanel() {
  const { runResult } = useSubmissionResult();
  const [value, setValue] = useState("testcase");

  useEffect(() => {
    if (runResult) {
      setValue("result");
    }
  }, [runResult]);

  return (
    <Tabs
      defaultValue="testcase"
      value={value}
      onValueChange={setValue}
      className="flex size-full flex-col gap-0"
    >
      <ScrollArea className="mt-0.5 h-10 border-b">
        <ProblemTestcaseTabHeader />
        <ScrollBar orientation="horizontal" className="hidden" />
      </ScrollArea>

      <ScrollArea
        className="w-full min-w-xs"
        style={{ height: "calc(100% - 3rem)" }}
      >
        <TabsContent value="testcase">
          <ProblemTestcaseTab />
        </TabsContent>
        <TabsContent value="result">
          <ProblemTestcaseResult result={runResult} />
        </TabsContent>
        <ScrollBar orientation="vertical" />
      </ScrollArea>
    </Tabs>
  );
}
