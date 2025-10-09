"use client";
import { useCode } from "@/app/problems/_providers/use-code";
import CppIcon from "@/components/icons/cpp-icon";
import JavaIcon from "@/components/icons/java-icon";
import PythonIcon from "@/components/icons/python-icon";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import Language from "@/types/language";

export function LanguageSelect() {
  const { lang, setLangFn } = useCode();
  return (
    <Select defaultValue="java" value={lang} onValueChange={setLangFn}>
      <SelectTrigger className="!h-8 w-[120px] rounded-[8px] !border-0 !ring-0">
        <SelectValue className="!text-muted-foreground !text-sm" />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectLabel>Language</SelectLabel>
          <SelectItem value={Language.CPP}>
            <CppIcon /> C++
          </SelectItem>
          <SelectItem value={Language.JAVA}>
            <JavaIcon />
            Java
          </SelectItem>
          <SelectItem value={Language.PYTHON}>
            <PythonIcon /> Python
          </SelectItem>
        </SelectGroup>
      </SelectContent>
    </Select>
  );
}
