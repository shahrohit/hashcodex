import { z } from "zod";

const createTopicSchema = z.object({
  slug: z
    .string()
    .trim()
    .nonempty({ error: "Slug is Required" })
    .min(2, { error: "Minimum 2 characters" })
    .max(50, { error: "Maximum 50 characters" }),

  name: z
    .string()
    .trim()
    .nonempty({ error: "Name is Required" })
    .min(2, { error: "Minimum 2 characters" })
    .max(50, { error: "Maximum 50 characters" }),
});

export type CreateTopicType = z.infer<typeof createTopicSchema>;
export default createTopicSchema;
