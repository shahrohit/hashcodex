"use client";

import useUpdateSlug from "@/app/admin/_hooks/use-update-slug";
import InputWithIcon from "@/components/input-with-icon";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Spinner } from "@/components/ui/spinner";
import { getErrorMessage } from "@/lib/error-handler";
import { ApiResponse } from "@/types/api-response";
import { Link } from "lucide-react";
import { useRouter } from "next/navigation";
import React, { useCallback, useState } from "react";
import { toast } from "sonner";

export default function UpdateProblemSlugDialog({
  number,
  slug,
}: {
  number: number;
  slug: string;
}) {
  const router = useRouter();
  const [open, setOpen] = useState(false);
  const [newSlug, setNewSlug] = useState(slug);
  const { mutateAsync, isPending } = useUpdateSlug(number);

  function handleOpenChange(open: boolean) {
    setOpen(open);
    if (open) setNewSlug(slug);
  }
  function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
    setNewSlug(
      e.target.value.toLocaleLowerCase().replace(" ", "-").toLocaleLowerCase()
    );
  }

  const successHandler = useCallback(
    (data: ApiResponse<string>): string => {
      router.replace(`/admin/problems/${data.data}`);
      return "Slug Updated";
    },
    [router]
  );

  function handleUpdate() {
    toast.promise(mutateAsync({ slug: newSlug }), {
      loading: "Updating...",
      success: successHandler,
      error: getErrorMessage,
    });
  }

  return (
    <Dialog open={open} onOpenChange={handleOpenChange}>
      <DialogTrigger asChild>
        <Button variant="outline" size="icon" className="text-primary">
          {isPending ? <Spinner /> : <Link />}
        </Button>
      </DialogTrigger>

      <DialogContent onInteractOutside={(event) => event.preventDefault()}>
        <DialogHeader>
          <DialogTitle>Edit Slug</DialogTitle>
          <DialogDescription>Update your problem slug URL</DialogDescription>
        </DialogHeader>
        <section className="flex flex-row gap-2">
          <InputWithIcon
            Icon={Link}
            placeholder={slug}
            field={{ value: newSlug, onChange: handleChange }}
          />

          <Button
            onClick={handleUpdate}
            size="sm"
            className=""
            variant="outline"
            disabled={isPending || !newSlug || slug === newSlug}
          >
            Update
          </Button>
        </section>
      </DialogContent>
    </Dialog>
  );
}
