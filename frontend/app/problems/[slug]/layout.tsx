import ProblemHeader from "@/app/problems/_components/header/problem-header";
import USidebarHeader from "@/app/problems/_components/sidebar-header";
import ProblemSidebarWrapper from "@/app/problems/_components/sidebar/problem-sidebar-warpper";
import CodeProvider from "@/app/problems/_providers/code-provider";
import ProblemProvider from "@/app/problems/_providers/problem-provider";
import SubmissionProvider from "@/app/problems/_providers/submission-provider";
import TestcaseProvider from "@/app/problems/_providers/testcase-provider";
import { SidebarProvider } from "@/components/ui/sidebar";

type Props = {
  children: React.ReactNode;
  params: Promise<{ slug: string }>;
};

export default async function Problemlayout({ children, params }: Props) {
  const { slug } = await params;
  return (
    <SidebarProvider
      defaultOpen={false}
      style={
        {
          "--sidebar-width": "20rem",
          backgroundColor: "Background",
        } as React.CSSProperties
      }
    >
      <ProblemProvider slug={slug}>
        <ProblemSidebarWrapper>
          <TestcaseProvider>
            <CodeProvider>
              <SubmissionProvider>
                <USidebarHeader>
                  <ProblemHeader />
                </USidebarHeader>
                <main className="h-full overflow-hidden">{children}</main>
              </SubmissionProvider>
            </CodeProvider>
          </TestcaseProvider>
        </ProblemSidebarWrapper>
      </ProblemProvider>
    </SidebarProvider>
  );
}
