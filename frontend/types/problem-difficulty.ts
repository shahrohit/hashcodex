import { z } from "zod";

const ProblemDifficulty = {
  EASY: "EASY",
  MEDIUM: "MEDIUM",
  HARD: "HARD",
} as const;

export const ProblemDifficultyEnum = z.enum([...Object.values(ProblemDifficulty)], {
  error: "Invalid Value",
});
export type ProblemDifficultyType = z.infer<typeof ProblemDifficultyEnum>;
export default ProblemDifficulty;
