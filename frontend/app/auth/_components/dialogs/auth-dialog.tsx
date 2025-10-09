"use client";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import authDialogComponentMap, {
  authDialogHeaderMap,
} from "@/store/auth-dialog-map";
import { AuthDialogProps } from "@/types/auth-dialog-mode";
import { useCallback, useState } from "react";

function AuthDialog({
  open = false,
  mode,
  children,
  permanent = false,
}: AuthDialogProps) {
  const [currentOpen, setOpen] = useState(open);
  const [currentMode, setCurrentMode] = useState(mode);

  function handleOpenChange(open: boolean) {
    setOpen(open);
    if (open) setCurrentMode(mode);
  }

  const handleEscKeyDown = useCallback(
    (e: KeyboardEvent) => {
      if (permanent) e.preventDefault();
    },
    [permanent]
  );

  const handleInteractOutside = useCallback(
    (e: CustomEvent<{ originalEvent: PointerEvent | FocusEvent }>) => {
      if (permanent) e.preventDefault();
    },
    [permanent]
  );

  const modelHeader = authDialogHeaderMap[currentMode];
  const ModelComponent = authDialogComponentMap[currentMode];

  return (
    <Dialog open={currentOpen} onOpenChange={handleOpenChange}>
      <DialogTrigger asChild>{children}</DialogTrigger>
      <DialogContent
        showCloseButton={!permanent}
        onEscapeKeyDown={handleEscKeyDown}
        className="sm:max-w-[425px]"
        onInteractOutside={handleInteractOutside}
      >
        <DialogHeader>
          <DialogTitle>{modelHeader.title}</DialogTitle>
          <DialogDescription>{modelHeader.description}</DialogDescription>
        </DialogHeader>
        <ModelComponent setAuthDialogMode={setCurrentMode} />
      </DialogContent>
    </Dialog>
  );
}

export default AuthDialog;
