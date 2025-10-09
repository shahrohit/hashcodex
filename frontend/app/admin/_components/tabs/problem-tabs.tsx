import EditProblemBasicTab from "@/app/admin/_components/tabs/problem-basic-tab";
import EditProblemCodeTab from "@/app/admin/_components/tabs/problem-code-tab";
import EditProblemDescriptionTab from "@/app/admin/_components/tabs/problem-description-tab";
import EditProblemTestcaseTab from "@/app/admin/_components/tabs/problem-testcase-tab";
import EditProblemTopicTab from "@/app/admin/_components/tabs/problem-topic-tab";
import { ProblemDetail } from "@/app/admin/_types/problems";
import TabListContainer from "@/components/tab-list-container";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { ArrowDown01, Code2Icon, LetterText, List, Text } from "lucide-react";

export default function ProblemTabs({ problem }: { problem: ProblemDetail }) {
  return (
    <Tabs defaultValue="basic">
      <TabListContainer>
        <ProblemTabList />
      </TabListContainer>

      <TabsContent value="basic">
        <EditProblemBasicTab problem={problem} />
      </TabsContent>

      <TabsContent value="description">
        <EditProblemDescriptionTab
          slug={problem.slug}
          number={problem.number}
          description={problem.description}
        />
      </TabsContent>

      <TabsContent value="topics">
        <EditProblemTopicTab number={problem.number} />
      </TabsContent>

      <TabsContent value="codes">
        <EditProblemCodeTab number={problem.number} />
      </TabsContent>

      <TabsContent value="testcases">
        <EditProblemTestcaseTab number={problem.number} />
      </TabsContent>
    </Tabs>
  );
}

function ProblemTabList() {
  return (
    <TabsList>
      <TabsTrigger value="basic">
        <LetterText />
        Basic Details
      </TabsTrigger>
      <TabsTrigger value="description">
        <Text />
        Description
      </TabsTrigger>
      <TabsTrigger value="topics">
        <List />
        Topics
      </TabsTrigger>
      <TabsTrigger value="codes">
        <Code2Icon />
        Codes
      </TabsTrigger>

      <TabsTrigger value="testcases">
        <ArrowDown01 />
        Testcases
      </TabsTrigger>
    </TabsList>
  );
}
