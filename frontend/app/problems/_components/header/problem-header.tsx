"use client";
import ProblemRun from "@/app/problems/_components/header/problem-run";
import ProblemSubmit from "@/app/problems/_components/header/problem-submit";
import HeaderProfile from "@/components/header-profile";
import useAuth from "@/hooks/use-auth";
import Logo from "@/public/logo.svg";
import Image from "next/image";
import Link from "next/link";

export default function ProblemHeader() {
  const { user } = useAuth();
  return (
    <div className="flex h-full w-full items-center justify-between gap-1">
      <Link href="/" className="h-8">
        <Image src={Logo} alt="Engineerg" className="size-full" />
      </Link>
      {user && (
        <div className="flex gap-1">
          <ProblemRun />
          <ProblemSubmit />
        </div>
      )}
      <div className="">
        <HeaderProfile />
      </div>
    </div>
  );
}
