import { z } from "zod";

const createTestcaseSchema = z.object({
  input: z.string().trim().nonempty({ error: "Input is Required" }),
  output: z.string().trim().nonempty({ error: "Output is Required" }),
  sample: z.boolean({ error: "Invalid Value" }),
});

export type CreateTestcaseType = z.infer<typeof createTestcaseSchema>;
export default createTestcaseSchema;
