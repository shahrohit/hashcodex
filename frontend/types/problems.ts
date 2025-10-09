import { ProblemDifficultyType } from "@/types/problem-difficulty";
import { ProblemSolvedStatusType } from "@/types/problem-solved-status";

export type TopicItem = {
  slug: string;
  name: string;
};

export type UserProblemItem = {
  number: number;
  title: string;
  slug: string;
  difficulty: ProblemDifficultyType;
  status: ProblemSolvedStatusType;
};

export type ProblemDetail = {
  number: number;
  title: string;
  slug: string;
  difficulty: string;
  description: string;
  params: string;
  status: string;
  topics: string[];
  testcases: string[];
  code: Record<string, string>;
};

export type TestcaseResult = {
  input: string | null;
  output: string | null;
  expected: string | null;
  error: string | null;
  status: string;
};

export type SubmissionResult = {
  total: number;
  passed: number;
  status: string;
  timeMs: number;
  compileError: string | null;
  errorMessage: string | null;
  cases: TestcaseResult[] | null;
  submissionType: string;
};

export type TabInfo = { value: string; name: string; result: SubmissionResult };

export type SubmissionItem = {
  status: string;
  language: string;
  submittedAt: string;
};
