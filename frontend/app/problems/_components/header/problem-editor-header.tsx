import { LanguageSelect } from "@/app/problems/_components/header/language-select";
import { useCode } from "@/app/problems/_providers/use-code";
import { Button } from "@/components/ui/button";
import { CircleAlertIcon, RotateCcwIcon } from "lucide-react";

import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";

export default function ProblemEditorHeader() {
  const { resetFn } = useCode();
  return (
    <div className="flex h-10 items-center justify-between px-2 pb-1">
      <LanguageSelect />
      <AlertDialog>
        <AlertDialogTrigger asChild>
          <Button variant="ghost" size="icon">
            <RotateCcwIcon className="size-4" />
          </Button>
        </AlertDialogTrigger>
        <AlertDialogContent>
          <div className="flex flex-col gap-2 max-sm:items-center sm:flex-row sm:gap-4">
            <div
              className="text-destructivetext-destructive bg-destructive/20 flex size-9 shrink-0 items-center justify-center rounded-full"
              aria-hidden="true"
            >
              <CircleAlertIcon
                className="text-destructive opacity-80"
                size={16}
              />
            </div>
            <AlertDialogHeader>
              <AlertDialogTitle>Are you sure?</AlertDialogTitle>
              <AlertDialogDescription>
                All the changes will be loss!
              </AlertDialogDescription>
            </AlertDialogHeader>
          </div>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancel</AlertDialogCancel>
            <AlertDialogAction onClick={resetFn}>Confirm</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
}
