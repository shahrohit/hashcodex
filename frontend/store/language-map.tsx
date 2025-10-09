import CppIcon from "@/components/icons/cpp-icon";
import JavaIcon from "@/components/icons/java-icon";
import PythonIcon from "@/components/icons/python-icon";
import { LanguageType } from "@/types/language";
import { FC } from "react";

export const LanguageIconMap: Record<
  LanguageType,
  FC<{ className?: string }>
> = {
  cpp: CppIcon,
  java: JavaIcon,
  python: PythonIcon,
};
