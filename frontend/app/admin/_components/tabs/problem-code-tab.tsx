"use client";
import CreateProblemCode from "@/app/admin/_components/form/create-problem-code";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import Language, { LanguageType } from "@/types/language";

import useQueryCodes from "@/app/admin/_hooks/use-query-codes";
import { ProblemCodeRecord } from "@/app/admin/_types/problems";
import CppIcon from "@/components/icons/cpp-icon";
import JavaIcon from "@/components/icons/java-icon";
import PythonIcon from "@/components/icons/python-icon";
import NoData from "@/components/no-data";
import TabListContainer from "@/components/tab-list-container";
import { Spinner } from "@/components/ui/spinner";
import { Code2Icon } from "lucide-react";
import { useEffect, useState } from "react";

export default function EditProblemCodeTab({ number }: { number: number }) {
  const [code, setCode] = useState<ProblemCodeRecord | null>(null);
  const { isPending, data, isError } = useQueryCodes(number);

  useEffect(() => {
    if (data) {
      const responseData: ProblemCodeRecord = {
        cpp: {
          id: null,
          driverCode: "",
          language: Language.CPP,
          solutionCode: "",
          userCode: "",
        },
        java: {
          id: null,
          driverCode: "",
          language: Language.JAVA,
          solutionCode: "",
          userCode: "",
        },
        python: {
          id: null,
          driverCode: "",
          language: Language.PYTHON,
          solutionCode: "",
          userCode: "",
        },
      };

      data.forEach((code) => {
        responseData[code.language as LanguageType] = code;
      });

      setCode(responseData);
    }
  }, [data]);

  if (isPending) return <Spinner />;
  if (isError || !data || !Array.isArray(data))
    return <NoData message="Failed to Load data" />;

  return (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <Code2Icon />
          Code
        </CardTitle>
        <CardDescription>Update the code for the problem</CardDescription>
      </CardHeader>
      <CardContent className="flex flex-col gap-4 p-0 sm:p-4">
        <Tabs defaultValue={Language.CPP}>
          <TabListContainer>
            <TabsList className="bg-background">
              <TabsTrigger value={Language.CPP}>
                <CppIcon />
                C++
              </TabsTrigger>
              <TabsTrigger value={Language.JAVA}>
                <JavaIcon />
                Java
              </TabsTrigger>
              <TabsTrigger value={Language.PYTHON}>
                <PythonIcon />
                Python
              </TabsTrigger>
            </TabsList>
          </TabListContainer>

          <TabsContent value={Language.CPP}>
            {code && (
              <CreateProblemCode
                number={number}
                language={Language.CPP}
                data={code.cpp}
              />
            )}
          </TabsContent>

          <TabsContent value={Language.JAVA}>
            {code && (
              <CreateProblemCode
                number={number}
                language={Language.JAVA}
                data={code.java}
              />
            )}
          </TabsContent>

          <TabsContent value={Language.PYTHON}>
            {code && (
              <CreateProblemCode
                number={number}
                language={Language.PYTHON}
                data={code.python}
              />
            )}
          </TabsContent>
        </Tabs>
      </CardContent>
    </Card>
  );
}
