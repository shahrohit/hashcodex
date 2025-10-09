"use client";
import CreateTopicForm from "@/app/admin/_components/form/create-topic-form";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { TopicItem } from "@/types/problems";
import { ListIcon } from "lucide-react";
import { Dispatch, SetStateAction } from "react";

export default function UpdateTopicDialog({
  topic,
  setTopic,
}: {
  topic: TopicItem | undefined;
  setTopic: Dispatch<SetStateAction<TopicItem | undefined>>;
}) {
  const closeDialog = () => setTopic(undefined);

  return (
    <Dialog open={!!topic} onOpenChange={closeDialog}>
      <DialogContent className="max-h-[90%] max-w-sm overflow-y-scroll">
        <DialogHeader>
          <DialogTitle className="flex items-center gap-2">
            <ListIcon />
            Add Topic
          </DialogTitle>
          <DialogDescription>Please provider the Topic</DialogDescription>
        </DialogHeader>
        <CreateTopicForm closeDialog={closeDialog} topic={topic} />
      </DialogContent>
    </Dialog>
  );
}
