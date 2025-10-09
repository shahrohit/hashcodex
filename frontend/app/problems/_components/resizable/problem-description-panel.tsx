"use client";
import ProblemDescriptionTab from "@/app/problems/_components/tabs/problem-description-tab";
import ProblemDescriptionTabHeader from "@/app/problems/_components/tabs/problem-description-tab-header";
import ProblemSubmissionTab from "@/app/problems/_components/tabs/problem-submission-tab";
import SubmissionResultTab from "@/app/problems/_components/tabs/submission-result-tab";
import { useSubmissionResult } from "@/app/problems/_providers/use-submission-result";
import { ScrollArea, ScrollBar } from "@/components/ui/scroll-area";
import { Tabs, TabsContent } from "@/components/ui/tabs";
import Helpers from "@/lib/helpers";
import { TabInfo } from "@/types/problems";
import { useEffect, useRef, useState } from "react";

export default function ProblemDescriptionPanel() {
  const { submitResult } = useSubmissionResult();
  const [value, setValue] = useState("description");
  const [tabState, setTabState] = useState<TabInfo[]>([]);
  const tabRefs = useRef<Record<string, HTMLButtonElement | null>>({});

  useEffect(() => {
    if (submitResult) {
      const value = crypto.randomUUID();
      const name = Helpers.getSubmisionMessage(submitResult.status);
      setTabState((oldValue) => [
        ...oldValue,
        { value, name, result: submitResult },
      ]);
      setValue(value);
    }
  }, [submitResult, setTabState]);

  useEffect(() => {
    const node = tabRefs.current[value];
    if (node) {
      node.scrollIntoView({
        behavior: "smooth",
        inline: "center",
        block: "nearest",
      });
    }
  }, [value]);

  function deleteTab(delValue: string) {
    setTabState(tabState.filter((tab) => tab.value != delValue));
    if (delValue === value) {
      setValue("description");
    }
  }

  return (
    <Tabs
      defaultValue="description"
      value={value}
      onValueChange={setValue}
      className="flex size-full flex-col gap-0 overflow-hidden"
    >
      <ScrollArea className="mt-0.5 h-10 border-b">
        <ProblemDescriptionTabHeader
          tabState={tabState}
          tabRefs={tabRefs}
          deleteTab={deleteTab}
        />
        <ScrollBar orientation="horizontal" className="hidden" />
      </ScrollArea>

      <div
        className="w-full min-w-xs overflow-y-scroll"
        style={{ height: "calc(100% - 3rem)" }}
      >
        <TabsContent value="description" className="">
          <ProblemDescriptionTab />
        </TabsContent>
        <TabsContent value="submission">
          <ProblemSubmissionTab />
        </TabsContent>

        {tabState.map((tab) => {
          return (
            <TabsContent key={tab.value} value={tab.value}>
              <SubmissionResultTab result={tab.result} />
            </TabsContent>
          );
        })}
      </div>
    </Tabs>
  );
}
