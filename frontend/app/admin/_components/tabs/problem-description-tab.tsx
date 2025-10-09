"use client";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useCallback, useState } from "react";

import useUpdateProblemDescription from "@/app/admin/_hooks/use-update-problem-description";
import ErrorMessage from "@/components/error-message";
import MarkDownEditor from "@/components/markdown-editor";
import { Button } from "@/components/ui/button";
import { getError } from "@/lib/error-handler";
import { Nullable } from "@/types/utility";
import { Save, Text } from "lucide-react";
import { toast } from "sonner";

export default function EditProblemDescriptionTab({
  slug,
  number,
  description,
}: {
  slug: string;
  number: number;
  description: string;
}) {
  const [error, setError] = useState<Nullable<string>>(null);
  const [value, setValue] = useState<string>(description);
  const { mutateAsync, isPending } = useUpdateProblemDescription(slug, number);

  const handleSubmit = useCallback(
    (value: string) => {
      if (!value.trim()) {
        setError("Description is Required");
        return;
      }
      setError(null);
      toast.promise(mutateAsync({ description: value }), {
        loading: "Updating...",
        success: (data) => {
          setValue(data);
          return "Updated";
        },
        error: (error) => {
          const data = getError(error);
          if (data.errors?.description) setError(data.errors?.description);
          return data.message;
        },
      });
    },
    [mutateAsync]
  );

  return (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <Text />
          Problem Description
        </CardTitle>
        <CardDescription>Update the problem description here</CardDescription>
        <CardAction>
          <Button
            variant="secondary"
            onClick={() => handleSubmit(value)}
            disabled={isPending || description === value}
          >
            Save
            <Save />
          </Button>
        </CardAction>
      </CardHeader>
      <CardContent className="p-0 sm:p-4">
        <MarkDownEditor
          height={500}
          value={value}
          setValue={(val) => setValue(val ?? "")}
        />
        <ErrorMessage message={error} />
      </CardContent>
    </Card>
  );
}
