"use client";
import AddProblemTopic from "@/app/admin/_components/form/add-problem-topic";
import ProblemTopicList from "@/app/admin/_components/problem-topic-list";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

import { ListIcon } from "lucide-react";

export default function EditProblemTopicTab({ number }: { number: number }) {
  return (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <ListIcon />
          Problem Topics
        </CardTitle>
        <CardDescription>Update the problem description here</CardDescription>
      </CardHeader>
      <CardContent className="p-0 sm:p-4">
        <AddProblemTopic number={number} />
        <ProblemTopicList number={number} />
      </CardContent>
    </Card>
  );
}
