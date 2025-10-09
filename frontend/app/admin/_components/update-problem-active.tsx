"use client";
import useUpdateProblemActive from "@/app/admin/_hooks/use-update-problem-active";
import { Label } from "@/components/ui/label";
import { Switch } from "@/components/ui/switch";
import { getErrorMessage } from "@/lib/error-handler";
import { toast } from "sonner";

export default function UpdateProblemActive({
  slug,
  number,
  active,
}: {
  slug: string;
  number: number;
  active: boolean;
}) {
  const updateStatusApi = useUpdateProblemActive(slug);

  const handleUpdateActive = (number: number, active: boolean) => {
    if (window.confirm("Are you sure?")) {
      toast.promise(updateStatusApi.mutateAsync({ number, active }), {
        loading: "Updating...",
        success: active ? "Enabled" : "Disabled",
        error: getErrorMessage,
      });
    }
  };

  return (
    <div className="inline-flex cursor-pointer items-center gap-2 rounded-sm border p-2 *:cursor-pointer">
      <Switch
        checked={active}
        onCheckedChange={(status) => handleUpdateActive(number, status)}
      />
      <Label className="text-sm font-medium">
        {active ? "Active" : "Inactive"}
      </Label>
    </div>
  );
}
