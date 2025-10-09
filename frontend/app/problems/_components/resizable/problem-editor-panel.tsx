"use client";
import ProblemEditorHeader from "@/app/problems/_components/header/problem-editor-header";
import { useCode } from "@/app/problems/_providers/use-code";
import CodeEditor from "@/components/code-editor";

export default function ProblemEditorPanel() {
  const { lang, code, setCodeFn } = useCode();
  return (
    <section className="flex size-full flex-col gap-0">
      <div className="mt-0.5 h-10 w-full border-b">
        <ProblemEditorHeader />
      </div>

      <div className="w-full min-w-xs" style={{ height: "calc(100% - 3rem)" }}>
        <CodeEditor language={lang} code={code[lang]} setCodeFn={setCodeFn} />
      </div>
    </section>
  );
}
