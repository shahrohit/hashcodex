"use client";
import CreateProblemForm from "@/app/admin/_components/form/create-problem-form";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Code2 } from "lucide-react";
import { useState } from "react";

function setPreventDefault(event: Event) {
  event.preventDefault();
}

export default function CreateProblemDialog() {
  const [open, setOpen] = useState(false);
  const closeDialog = () => setOpen(false);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button variant="outline">
          Create
          <Code2 className="size-4" />
        </Button>
      </DialogTrigger>
      <DialogContent
        className="max-h-[90%] overflow-y-scroll sm:max-w-[700px]"
        onEscapeKeyDown={setPreventDefault}
        onInteractOutside={setPreventDefault}
      >
        <DialogHeader>
          <DialogTitle className="flex items-center gap-2">
            <Code2 />
            Create Problems
          </DialogTitle>
          <DialogDescription>
            Please provider the problem details
          </DialogDescription>
        </DialogHeader>
        <CreateProblemForm closeDialog={closeDialog} />
      </DialogContent>
    </Dialog>
  );
}
