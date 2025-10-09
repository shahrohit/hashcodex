"use client";
import CreateProblemTestcaseDialog from "@/app/admin/_components/dialogs/create-problem-testcase-dialog";
import ProblemTestcasesListing from "@/app/admin/_components/problem-testcases-listing";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

import { ArrowDown01Icon } from "lucide-react";

export default function EditProblemTestcaseTab({ number }: { number: number }) {
  return (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <ArrowDown01Icon />
          Testcases
        </CardTitle>
        <CardDescription>Update the testcases for the problem</CardDescription>
        <CardAction>
          <CreateProblemTestcaseDialog number={number} />
        </CardAction>
      </CardHeader>
      <CardContent className="p-0 sm:p-4">
        <ProblemTestcasesListing number={number} />
      </CardContent>
    </Card>
  );
}
