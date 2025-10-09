import { z } from "zod";

const ProblemSolvedStatus = {
  NONE: "NONE",
  SOLVED: "SOLVED",
  ATTEMPTED: "ATTEMPTED",
} as const;

export const ProblemSolvedStatusEnum = z.enum(
  [...Object.values(ProblemSolvedStatus)],
  {
    error: "Invalid Value",
  }
);
export type ProblemSolvedStatusType = z.infer<typeof ProblemSolvedStatusEnum>;
export default ProblemSolvedStatus;
