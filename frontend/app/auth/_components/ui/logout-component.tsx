"use client";
import useLogout from "@/app/auth/_query/use-logout";
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
import { Button } from "@/components/ui/button";
import { getErrorMessage } from "@/lib/error-handler";
import { CircleAlertIcon, LogOut } from "lucide-react";
import { useRouter } from "next/navigation";
import { useCallback } from "react";
import { toast } from "sonner";

function LogoutComponent({ redirect = false }: { redirect?: boolean }) {
  const router = useRouter();
  const { isPending, mutateAsync } = useLogout();

  const successHandler = useCallback(() => {
    if (redirect) router.replace("/");
    return "Successfully logged out";
  }, [redirect, router]);

  const handleLogout = useCallback(() => {
    toast.promise(mutateAsync(), {
      loading: "Logging out...",
      success: successHandler,
      error: getErrorMessage,
    });
  }, [mutateAsync, successHandler]);

  return (
    <AlertDialog>
      <AlertDialogTrigger asChild>
        <Button
          variant="destructive"
          disabled={isPending}
          className="w-full size-8"
        >
          <LogOut />
        </Button>
      </AlertDialogTrigger>
      <AlertDialogContent>
        <div className="flex flex-col gap-2 max-sm:items-center sm:flex-row sm:gap-4">
          <div
            className="bg-destructive/20 flex size-9 shrink-0 items-center justify-center rounded-full"
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
              Are you sure you want to logout?
            </AlertDialogDescription>
          </AlertDialogHeader>
        </div>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancel</AlertDialogCancel>
          <AlertDialogAction onClick={handleLogout}>Confirm</AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}

export default LogoutComponent;
