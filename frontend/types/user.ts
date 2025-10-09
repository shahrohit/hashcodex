import { z } from "zod";
import { Nullable } from "@/types/utility";

export const Role = {
  USER: "USER",
  ADMIN: "ADMIN",
} as const;

export const RoleEnum = z.enum([...Object.values(Role)]);
export type RoleType = z.infer<typeof RoleEnum>;

type User = {
  publicId: string;
  name: string;
  email: string;
  profilePicture: Nullable<string>;
  role: RoleType;
  emailVerified: boolean;
  enabled: boolean;
};

export default User;
