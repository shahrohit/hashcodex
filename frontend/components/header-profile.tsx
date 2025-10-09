"use client";

import AuthComponent from "@/app/auth/_components/ui/auth-component";
import LogoutComponent from "@/app/auth/_components/ui/logout-component";
import { buttonVariants } from "@/components/ui/button";
import { Spinner } from "@/components/ui/spinner";
import useAuth from "@/hooks/use-auth";
import { UserIcon } from "lucide-react";
import Link from "next/link";

export default function HeaderProfile() {
  const { user, isPending } = useAuth();
  if (isPending) return <Spinner />;
  return user ? (
    <div className="flex gap-2 items-center">
      <div
        className={buttonVariants({
          variant: "outline",
          className: "h-10 max-w-42",
        })}
      >
        <Link href={user.role === "ADMIN" ? "/admin" : "/"}>
          <UserIcon />
        </Link>
        <span className="truncate">{user.name}</span>
        <LogoutComponent />
      </div>
    </div>
  ) : (
    <AuthComponent />
  );
}
