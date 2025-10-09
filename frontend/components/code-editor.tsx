"use client";

import { Spinner } from "@/components/ui/spinner";
import { BeforeMount, Monaco } from "@monaco-editor/react";
import { editor } from "monaco-editor";
import { useTheme } from "next-themes";
import dynamic from "next/dynamic";
import { useEffect, useState } from "react";
const Editor = dynamic(() => import("@monaco-editor/react"), { ssr: false });

const beforeMount: BeforeMount = (monaco) => {
  monaco.editor.defineTheme("my-light", {
    base: "vs",
    inherit: true,
    rules: [
      { token: "comment", foreground: "6a737d" },
      { token: "string", foreground: "032f62" },
      { token: "keyword", foreground: "d73a49" },
    ],
    colors: {
      "editor.background": "#ffffff",
      "editor.foreground": "#24292e",
      "editorLineNumber.foreground": "#a0a1a7",
      "editorLineNumber.activeForeground": "#2f363d",
      "editorCursor.foreground": "#0969da",
      "editorIndentGuide.background": "#e5e7eb",
      "editorIndentGuide.activeBackground": "#d1d5db",
    },
  });

  monaco.editor.defineTheme("my-dark", {
    base: "vs-dark",
    inherit: true,
    rules: [
      { token: "comment", foreground: "8b949e", fontStyle: "italic" },
      { token: "string", foreground: "a5d6ff" },
      { token: "keyword", foreground: "ff7b72" },
    ],
    colors: {
      "editor.background": "#000000",
      "editor.foreground": "#c9d1d9",
      "editorLineNumber.foreground": "#6e7681",
      "editorLineNumber.activeForeground": "#c9d1d9",
      "editorCursor.foreground": "#58a6ff",
      "editorIndentGuide.background": "#2f353e",
      "editorIndentGuide.activeBackground": "#3b4251",
      "minimap.background": "#0d1117",
    },
  });
};

const onMount = async (
  editor: editor.IStandaloneCodeEditor,
  monaco: Monaco
) => {
  editor.addCommand(monaco.KeyMod.CtrlCmd | monaco.KeyCode.Delete, () => {
    editor.trigger("keyboard", "deleteWordRight", null);
  });
};

export default function CodeEditor({
  code,
  language,
  setCodeFn,
}: {
  code: string;
  language: string;
  setCodeFn: (code: string | undefined) => void;
}) {
  const { resolvedTheme } = useTheme();
  const [mounted, setMounted] = useState(false);
  useEffect(() => setMounted(true), []);
  const monacoTheme = resolvedTheme === "dark" ? "my-dark" : "my-light";

  return (
    <div className="h-full overflow-hidden rounded-sm">
      {mounted && (
        <Editor
          keepCurrentModel={true}
          height="100%"
          language={language}
          theme={monacoTheme}
          beforeMount={beforeMount}
          onMount={onMount}
          value={code}
          onChange={setCodeFn}
          loading={<Spinner />}
          options={{
            contextmenu: false,
            fontLigatures: true,
            fontFamily: '"JetBrains Mono", "Fira Code", monospace',
            cursorBlinking: "expand",
            cursorSmoothCaretAnimation: "on",
            roundedSelection: true,
            minimap: { enabled: false },
            autoClosingBrackets: "always",
            fontSize: 18,
            tabSize: 4,
            scrollBeyondLastLine: false,
            smoothScrolling: true,
            automaticLayout: true, // auto-resize on container changes
            renderLineHighlight: "none",
            scrollbar: {
              verticalScrollbarSize: 10,
              horizontalScrollbarSize: 10,
            },
          }}
        />
      )}
    </div>
  );
}
