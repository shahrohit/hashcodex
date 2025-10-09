import CreateProblemDialog from "@/app/admin/_components/dialogs/create-problem-dialog";
import ProblemListing from "@/app/admin/_components/problem-listing";
import TopicListing from "@/app/admin/_components/topic-listing";
import { buttonVariants } from "@/components/ui/button";
import { Home } from "lucide-react";
import Link from "next/link";

export default function AdminDashboardPage() {
  return (
    <section className="flex flex-col gap-2">
      <section className="flex justify-between">
        <Link href="/" className={buttonVariants({ variant: "default" })}>
          <Home /> Got to Home Page
        </Link>
        <CreateProblemDialog />
      </section>

      <ProblemListing />
      <TopicListing />
    </section>
  );
}
