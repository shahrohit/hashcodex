import { z } from "zod";

const loginSchema = z.object({
  email: z.email({ message: "Invalid email address" }).toLowerCase(),
  password: z.string().trim().nonempty({ message: "Password is required" }),
});

export type LoginType = z.infer<typeof loginSchema>;
export default loginSchema;
