import EditProblemBasicForm from "@/app/admin/_components/form/edit-problem-basic-form";
import { ProblemDetail } from "@/app/admin/_types/problems";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { LetterText } from "lucide-react";

export default function EditProblemBasicTab({
  problem,
}: {
  problem: ProblemDetail;
}) {
  return (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <LetterText /> Problem Basic Details
        </CardTitle>
        <CardDescription>Update the problem basic details here</CardDescription>
      </CardHeader>
      <CardContent>
        <EditProblemBasicForm problem={problem} />
      </CardContent>
    </Card>
  );
}
