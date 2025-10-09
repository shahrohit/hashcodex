import { ProblemDifficultyEnum } from "@/types/problem-difficulty";
import { z } from "zod";

const createProblemSchema = z.object({
  slug: z
    .string()
    .trim()
    .nonempty({ error: "Slug is Required" })
    .min(2, { error: "Minimum 2 characters" })
    .max(100, { error: "Maximum 100 characters" }),

  title: z
    .string()
    .trim()
    .nonempty({ error: "Title is Required" })
    .min(2, { error: "Minimum 2 characters" })
    .max(100, { error: "Maximum 100 characters" }),

  difficulty: ProblemDifficultyEnum,

  description: z.string().nonempty({ error: "Description is Required" }),

  params: z
    .string()
    .trim()
    .nonempty({ error: "Parameters is Required" })
    .max(100, { error: "Maximum 100 characters" }),
});

export type CreateProblemType = z.infer<typeof createProblemSchema>;
export default createProblemSchema;
