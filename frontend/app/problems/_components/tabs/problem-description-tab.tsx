"use client";

import { useProblem } from "@/app/problems/_providers/use-problem";
import DifficultyBadge from "@/components/difficulty-badge";
import MarkDownRender from "@/components/markdown-render";
import ProblemSolvedStatus from "@/components/problem-solved-status";

export default function ProblemDescriptionTab() {
  const { problem } = useProblem();

  return (
    <div className="flex flex-col gap-2 p-2 sm:p-4">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold">
          {problem.number}. {problem.title}
        </h1>
        <span>
          <ProblemSolvedStatus status={problem.status} />
        </span>
      </div>
      <DifficultyBadge difficulty={problem.difficulty} />

      <MarkDownRender content={problem.description} />
    </div>
  );
}
