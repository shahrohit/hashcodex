"use client";

import InputWithIcon from "@/components/input-with-icon";
import { Search } from "lucide-react";
import { useEffect, useState } from "react";

type Props = {
  onSearch: (query: string) => void;
  placeholder: string;
};

export default function DebounceSearch({ onSearch, placeholder }: Props) {
  const [input, setInput] = useState("");
  const [debouncedValue, setDebouncedValue] = useState("");

  useEffect(() => {
    const timeout = setTimeout(() => {
      setDebouncedValue(input);
    }, 250);

    return () => clearTimeout(timeout);
  }, [input]);

  useEffect(() => {
    onSearch(debouncedValue);
  }, [debouncedValue, onSearch]);

  return (
    <InputWithIcon
      Icon={Search}
      placeholder={placeholder}
      field={{
        value: input,
        onChange: (e) => setInput(e.target.value),
      }}
    />
  );
}
