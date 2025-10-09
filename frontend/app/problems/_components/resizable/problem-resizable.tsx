"use client";
import ProblemDescriptionPanel from "@/app/problems/_components/resizable/problem-description-panel";
import ProblemEditorPanel from "@/app/problems/_components/resizable/problem-editor-panel";
import ProblemTestcasePanel from "@/app/problems/_components/resizable/problem-testcase-panel";
import {
  ResizableHandle,
  ResizablePanel,
  ResizablePanelGroup,
} from "@/components/ui/resizable";
import { useIsMobile } from "@/hooks/use-mobile";

export function ProblemResizable() {
  const isMobile = useIsMobile();

  return isMobile ? <ProblemResizableMobile /> : <ProblemResizableDesktop />;
}

function ProblemResizableMobile() {
  return (
    <ResizablePanelGroup
      direction="horizontal"
      className="w-full md:min-w-[450px]"
    >
      <ResizablePanel defaultSize={100}>
        <ProblemDescriptionPanel />
      </ResizablePanel>

      <ResizableHandle
        withHandle
        className="hover:bg-primary active:bg-primary w-0.5 bg-oreground/20"
      />

      <ResizablePanel defaultSize={0}>
        <ResizablePanelGroup direction="vertical">
          <ResizablePanel defaultSize={94} minSize={6}>
            <ProblemEditorPanel />
          </ResizablePanel>
          <ResizableHandle
            withHandle
            className="hover:bg-primary active:bg-primary border-secondary/50 bg-foreground/20 p-[1px]"
          />
          <ResizablePanel defaultSize={6} minSize={6}>
            <ProblemTestcasePanel />
          </ResizablePanel>
        </ResizablePanelGroup>
      </ResizablePanel>
    </ResizablePanelGroup>
  );
}

function ProblemResizableDesktop() {
  return (
    <ResizablePanelGroup direction="horizontal" className="w-full">
      <ResizablePanel defaultSize={41}>
        <ResizablePanelGroup direction="vertical">
          <ResizablePanel
            defaultSize={68}
            minSize={6}
            className="border-t-2 border-l-2 border-foreground/20"
          >
            <ProblemDescriptionPanel />
          </ResizablePanel>
          <ResizableHandle
            withHandle
            className="hover:bg-primary active:bg-primary border-secondary/50 bg-foreground/20 p-[1px]"
          />
          <ResizablePanel
            defaultSize={32}
            minSize={6}
            className="border-b-2 border-l-2 border-foreground/20"
          >
            <ProblemTestcasePanel />
          </ResizablePanel>
        </ResizablePanelGroup>
      </ResizablePanel>
      <ResizableHandle
        withHandle
        className="hover:bg-primary active:bg-primary w-0.5 bg-foreground/20"
      />
      <ResizablePanel
        defaultSize={59}
        className="border-t-2 border-r-2 border-b-2 border-foreground/20"
      >
        <ProblemEditorPanel />
      </ResizablePanel>
    </ResizablePanelGroup>
  );
}
