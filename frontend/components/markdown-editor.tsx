"use client";
import "@uiw/react-markdown-preview/markdown.css";
import "@uiw/react-md-editor/markdown-editor.css";
import dynamic from "next/dynamic";

const MDEditor = dynamic(() => import("@uiw/react-md-editor"), { ssr: false });

type Prop = {
  value: string;
  setValue: (val?: string) => void;
  height?: number | undefined;
};

function MarkDownEditor({ value, setValue, height }: Prop) {
  return (
    <div className="w-full">
      <MDEditor autoFocus={true} value={value} height={height} onChange={setValue} />
    </div>
  );
}

export default MarkDownEditor;
