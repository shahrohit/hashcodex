import { Button } from "@/components/ui/button";
import { TabsList, TabsTrigger } from "@/components/ui/tabs";
import Helpers from "@/lib/helpers";
import { TabInfo } from "@/types/problems";
import {
  BadgeCheckIcon,
  BoxIcon,
  FileTextIcon,
  XCircleIcon,
  XIcon,
} from "lucide-react";
import { RefObject } from "react";

export default function ProblemDescriptionTabHeader({
  tabState,
  tabRefs,
  deleteTab,
}: {
  tabState: TabInfo[];
  tabRefs: RefObject<Record<string, HTMLButtonElement | null>>;
  deleteTab: (value: string) => void;
}) {
  return (
    <TabsList className="bg-background rounded-none px-2 *:min-w-fit *:rounded-[8px] *:px-5">
      <TabsTrigger
        value="description"
        ref={(el) => {
          tabRefs.current["description"] = el;
        }}
      >
        <FileTextIcon
          className="-ms-0.5 me-1.5 opacity-60"
          size={16}
          aria-hidden="true"
        />
        Description
      </TabsTrigger>

      <TabsTrigger
        value="submission"
        className="group"
        ref={(el) => {
          tabRefs.current["submission"] = el;
        }}
      >
        <BoxIcon
          className="-ms-0.5 me-1.5 opacity-60"
          size={16}
          aria-hidden="true"
        />
        Submission
      </TabsTrigger>

      {tabState.map((tab) => {
        return (
          <div key={tab.value} className="relative">
            <TabsTrigger
              value={tab.value}
              className="group"
              ref={(el) => {
                tabRefs.current[tab.value] = el;
              }}
            >
              {tab.result.status === "SOLVED" ? (
                <BadgeCheckIcon className="-ms-0.5 me-1.5 text-green-500 opacity-60" />
              ) : (
                <XCircleIcon className="text-destructive" />
              )}
              {Helpers.getSubmisionMessage(tab.result.status)}
            </TabsTrigger>
            <Button
              variant="destructive"
              onClick={() => deleteTab(tab.value)}
              className="absolute top-0 right-3 size-4"
            >
              <XIcon />
            </Button>
          </div>
        );
      })}
    </TabsList>
  );
}
