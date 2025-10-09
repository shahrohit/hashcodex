"use client";

import NotFound from "@/app/not-found";
import { Spinner } from "@/components/ui/spinner";
import useAuth from "@/hooks/use-auth";
import { Role } from "@/types/user";
import { memo, PropsWithChildren } from "react";

function AdminUser({ children }: PropsWithChildren) {
  const { user, isPending } = useAuth();

  if (isPending) return <Spinner />;
  if (user && user.role === Role.ADMIN) return children;
  return <NotFound />;
}

export default memo(AdminUser);
