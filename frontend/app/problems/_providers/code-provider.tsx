"use client";

import { useProblem } from "@/app/problems/_providers/use-problem";
import { LanguageType } from "@/types/language";
import { createContext, useEffect, useState } from "react";

type CodeContextType = {
  lang: LanguageType;
  setCodeFn: (code: string | undefined) => void;
  code: Record<LanguageType, string>;
  setLangFn: (lang: LanguageType) => void;
  resetFn: () => void;
  getLanguageAndCode: () => { language: string; code: string };
};

export const CodeContent = createContext<CodeContextType | null>(null);

export default function CodeProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const { problem } = useProblem();

  const [lang, setLang] = useState<LanguageType>("java");
  const initialValue = problem?.code ?? {
    cpp: "",
    java: "",
    python: "",
  };
  const [code, setCode] = useState<Record<LanguageType, string>>(initialValue);

  function setLangFn(lang: LanguageType) {
    console.log(code);
    setLang(lang);
  }

  function setCodeFn(currCode: string | undefined) {
    setCode((old) => {
      return {
        ...old,
        [lang]: currCode ?? "",
      };
    });
  }

  function resetFn() {
    setCode((old) => {
      return {
        ...old,
        [lang]: initialValue[lang] ?? "",
      };
    });
  }

  function getLanguageAndCode() {
    return { language: lang, code: code[lang] };
  }

  useEffect(() => {
    if (problem && problem.code) {
      setCode(problem.code);
    }
  }, [problem]);

  return (
    <CodeContent.Provider
      value={{ lang, setLangFn, resetFn, setCodeFn, code, getLanguageAndCode }}
    >
      {children}
    </CodeContent.Provider>
  );
}
