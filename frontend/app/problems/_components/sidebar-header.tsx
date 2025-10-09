import { Separator } from "@/components/ui/separator";
import { SidebarTrigger } from "@/components/ui/sidebar";
import React from "react";

export default function USidebarHeader({
  children,
}: {
  tooltip?: string | undefined;
  children?: React.ReactNode;
}) {
  return (
    <header className="bg-background sticky top-0 flex h-12 shrink-0 items-center gap-2 rounded-t-sm border-b px-4">
      <SidebarTrigger className="-ml-1" />
      <Separator
        orientation="vertical"
        className="mr-2 data-[orientation=vertical]:h-4"
      />
      {children}
    </header>
  );
}
