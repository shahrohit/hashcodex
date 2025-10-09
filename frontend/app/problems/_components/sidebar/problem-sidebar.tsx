"use client";
import useQueryProblems from "@/app/(public)/hooks/use-query-problems";
import SidebarProblemList from "@/app/problems/_components/sidebar/sidebar-problem-list";
import NoData from "@/components/no-data";
import { Sidebar, SidebarContent } from "@/components/ui/sidebar";
import { Spinner } from "@/components/ui/spinner";

export default function ProblemSidebar({
  ...props
}: React.ComponentProps<typeof Sidebar>) {
  return (
    <Sidebar {...props} variant="sidebar" className="">
      <SidebarContent className="bg-background p-2">
        <h2 className="text-center text-lg font-semibold">Problems</h2>
        <Component />
      </SidebarContent>
    </Sidebar>
  );
}

function Component() {
  const { isPending, data, isError } = useQueryProblems();
  if (isPending) return <Spinner />;
  if (isError || !data || !Array.isArray(data.items))
    return <NoData message="Failed to load problems" />;

  const problems = data.items;
  if (problems.length == 0) return <NoData message="No Problems" />;

  return <SidebarProblemList problems={problems} />;
}
