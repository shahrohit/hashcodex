import { LanguageType } from "@/types/language";

export type ProblemItem = {
  number: number;
  slug: string;
  title: string;
  difficulty: string;
  active: boolean;
  updatedAt: string;
};

export type ProblemDetail = {
  number: number;
  title: string;
  slug: string;
  description: string;
  difficulty: string;
  active: boolean;
  params: string;
  createdAt: string;
  updatedAt: string;
};

export type ProblemCode = {
  id: number | null;
  language: LanguageType;
  driverCode: string;
  userCode: string;
  solutionCode: string;
};

export type ProblemTestcaseItem = {
  id: number;
  input: string;
  output: string;
  sample: boolean;
};

export type ProblemCodeRecord = Record<LanguageType, ProblemCode>;
