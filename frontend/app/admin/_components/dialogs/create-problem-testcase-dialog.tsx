"use client";
import CreateProblemTestcaseForm from "@/app/admin/_components/form/create-problem-testcase-form";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { PlusIcon } from "lucide-react";
import { useState } from "react";

export default function CreateProblemTestcaseDialog({
  number,
}: {
  number: number;
}) {
  const [open, setOpen] = useState(false);
  const closeDialog = () => setOpen(false);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button variant="outline">
          Add
          <PlusIcon />
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Create Testcase</DialogTitle>
          <DialogDescription>
            Enter input and output testcases
          </DialogDescription>
        </DialogHeader>
        <CreateProblemTestcaseForm number={number} closeDialog={closeDialog} />
      </DialogContent>
    </Dialog>
  );
}
