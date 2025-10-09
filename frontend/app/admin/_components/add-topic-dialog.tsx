"use client";
import CreateTopicForm from "@/app/admin/_components/form/create-topic-form";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { ListIcon, PlusIcon } from "lucide-react";
import { useState } from "react";

export default function AddTopicDialog() {
  const [open, setOpen] = useState(false);
  const closeDialog = () => setOpen(false);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button variant="outline">
          Add
          <PlusIcon className="size-4" />
        </Button>
      </DialogTrigger>
      <DialogContent className="max-h-[90%] max-w-sm overflow-y-scroll">
        <DialogHeader>
          <DialogTitle className="flex items-center gap-2">
            <ListIcon />
            Add Topic
          </DialogTitle>
          <DialogDescription>Please provider the Topic</DialogDescription>
        </DialogHeader>
        <CreateTopicForm closeDialog={closeDialog} />
      </DialogContent>
    </Dialog>
  );
}
