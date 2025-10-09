import ProblemSidebar from "@/app/problems/_components/sidebar/problem-sidebar";
import { SidebarInset } from "@/components/ui/sidebar";
import React from "react";

export default function ProblemSidebarWrapper({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
      <ProblemSidebar />
      <SidebarInset>{children}</SidebarInset>
    </>
  );
}
