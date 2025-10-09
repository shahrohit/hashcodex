import DifficultyBadge from "@/components/difficulty-badge";
import ProblemSolvedStatus from "@/components/problem-solved-status";
import { UserProblemItem } from "@/types/problems";
import Link from "next/link";

export default function SidebarProblemList({
  problems,
}: {
  problems: UserProblemItem[];
}) {
  return (
    <section>
      <div className="flex flex-col gap-4">
        {problems.map((problem) => {
          return (
            <div
              key={problem.number}
              className="relative flex justify-between border p-2 rounded-[8px]"
            >
              <span className="absolute -top-3 -left-2">
                <ProblemSolvedStatus status={problem.status} />
              </span>
              <Link
                href={`/problems/${problem.slug}`}
                className="font-semibold text-base hover:text-primary"
              >
                {problem.number}. {problem.title}
              </Link>
              <span>
                <DifficultyBadge difficulty={problem.difficulty} />
              </span>
            </div>
          );
        })}
      </div>
    </section>
  );
}
