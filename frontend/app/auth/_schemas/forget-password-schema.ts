import { z } from "zod";

const forgetPasswordSchema = z.object({
  email: z.email({ message: "Invalid email address" }).toLowerCase(),
});

export type ForgetPasswordType = z.infer<typeof forgetPasswordSchema>;
export default forgetPasswordSchema;
