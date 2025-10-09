"use client";
import { Button, buttonVariants } from "@/components/ui/button";
import { ArrowLeft, ArrowRight, Home } from "lucide-react";
import Link from "next/link";
import { useRouter } from "next/navigation";

function NotFound() {
  const router = useRouter();

  const handleBack = () => {
    if (window.history.length > 1) {
      router.back();
    } else {
      router.push("/");
    }
  };

  return (
    <div className="relative flex h-svh w-full flex-col items-center justify-center gap-2">
      <h1 className="text-4xl font-bold text-red-500">Page Not Found 🚫</h1>

      <Button variant="outline" onClick={handleBack} className="absolute top-5 left-5">
        <ArrowLeft /> Back
      </Button>

      <Link href="/" replace className={buttonVariants({ variant: "outline" })}>
        <Home /> Go to Home Page <ArrowRight />
      </Link>
    </div>
  );
}

export default NotFound;
