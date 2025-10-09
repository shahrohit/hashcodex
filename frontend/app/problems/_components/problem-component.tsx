"use client";
import { ProblemResizable } from "@/app/problems/_components/resizable/problem-resizable";

export default function ProblemComponent() {
  return (
    <div
      className="absolute top-12 w-full overflow-hidden"
      style={{ height: "calc(100% - 50px)" }}
    >
      <ProblemResizable />
    </div>
  );
}
