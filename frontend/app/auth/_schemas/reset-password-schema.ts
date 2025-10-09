import { z } from "zod";

const resetPasswordSchema = z
  .object({
    password: z
      .string()
      .trim()
      .nonempty({ error: "Password is Required" })
      .min(6, { message: "Password should be at least 6 characters" }),
    confirmPassword: z.string().trim().nonempty({ error: "Confirm Password is Required" }),
  })
  .refine((data) => data.password === data.confirmPassword, {
    error: "Password should match",
    path: ["confirmPassword"],
  });

export type ResetPasswordType = z.infer<typeof resetPasswordSchema>;
export default resetPasswordSchema;
