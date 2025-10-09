import { ProblemDifficultyEnum } from "@/types/problem-difficulty";
import { z } from "zod";

const updateProblemBasicSchema = z.object({
  title: z
    .string()
    .trim()
    .nonempty({ error: "Title is Required" })
    .min(2, { error: "Minimum 2 characters" })
    .max(100, { error: "Maximum 100 characters" }),

  difficulty: ProblemDifficultyEnum,

  params: z
    .string()
    .trim()
    .nonempty({ error: "Parameters is Required" })
    .max(100, { error: "Maximum 100 characters" }),
});

export type UpdateProblemBasicType = z.output<typeof updateProblemBasicSchema>;
export default updateProblemBasicSchema;
