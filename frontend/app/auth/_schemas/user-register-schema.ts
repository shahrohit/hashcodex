import { z } from "zod";

const registerSchema = z.object({
  name: z
    .string()
    .trim()
    .nonempty({ message: "Name is Required" })
    .min(2, { message: "Minimum 2 characters" })
    .max(50, { message: "Maximum 50 characters" }),

  email: z.email({ message: "Invalid email address" }).toLowerCase(),

  password: z.string().trim().min(6, { message: "Password should be at least 6 characters" }),
});

export type RegisterType = z.infer<typeof registerSchema>;
export default registerSchema;
