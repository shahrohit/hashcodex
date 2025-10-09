import { z } from "zod";

const Language = {
  CPP: "cpp",
  JAVA: "java",
  PYTHON: "python",
} as const;

export const LanguageEnum = z.enum([...Object.values(Language)]);
export type LanguageType = z.infer<typeof LanguageEnum>;
export default Language;
