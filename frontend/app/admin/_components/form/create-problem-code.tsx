import useAddCode from "@/app/admin/_hooks/use-add-code";
import useUpdateCode from "@/app/admin/_hooks/use-update-code";
import { ProblemCode } from "@/app/admin/_types/problems";
import CodeEditor from "@/components/code-editor";
import { Button } from "@/components/ui/button";
import { getErrorMessage } from "@/lib/error-handler";
import { LanguageType } from "@/types/language";
import { Save } from "lucide-react";
import { Dispatch, SetStateAction, useCallback, useState } from "react";
import { toast } from "sonner";

export default function CreateProblemCode({
  number,
  language,
  data,
}: {
  number: number;
  language: LanguageType;
  data: ProblemCode;
}) {
  const { mutateAsync: addCodeAsync } = useAddCode(number);
  const { mutateAsync: updateCodeAsync } = useUpdateCode(number);
  const [driverCode, setDriverCode] = useState(data.driverCode);
  const [userCode, setUserCode] = useState(data.userCode);
  const [solutionCode, setSolutionCode] = useState(data.solutionCode);

  const handleAddCode = useCallback(
    (code: ProblemCode) => {
      let message = null;
      if (!code.driverCode) message = "Driver code is missing";
      else if (!code.userCode) message = "User code is missing";
      else if (!code.solutionCode) message = "Solution code is missing";

      if (message) {
        toast.warning(message);
        return;
      }

      if (window.confirm("Are you sure?")) {
        toast.promise(addCodeAsync(code), {
          loading: "Creating...",
          success: "Created",
          error: getErrorMessage,
        });
      }
    },
    [addCodeAsync]
  );

  const handleUpdateCode = useCallback(
    (type: "DRIVER" | "USER" | "SOLUTION", code: ProblemCode) => {
      let message = null;
      if (type === "DRIVER" && !code.driverCode)
        message = "Driver code is missing";
      else if (type === "USER" && !code.userCode)
        message = "User code is missing";
      else if (type === "SOLUTION" && !code.solutionCode)
        message = "Solution code is missing";

      if (message) {
        toast.warning(message);
        return;
      }

      if (window.confirm("Are you sure?")) {
        toast.promise(updateCodeAsync({ codeType: type, body: code }), {
          loading: "Saving...",
          success: "Saved",
          error: getErrorMessage,
        });
      }
    },
    [updateCodeAsync]
  );

  return (
    <div className="flex flex-col items-center gap-5 px-1 *:mb-10">
      <CodeSection
        name="DRIVER"
        language={language}
        code={driverCode}
        newEntry={!data.id}
        disabled={data.driverCode === driverCode}
        onSave={() =>
          handleUpdateCode("DRIVER", {
            id: data.id,
            driverCode,
            language,
            solutionCode,
            userCode,
          })
        }
        setCode={setDriverCode}
      />

      <CodeSection
        name="USER"
        language={language}
        code={userCode}
        newEntry={!data.id}
        disabled={data.userCode === userCode}
        onSave={() =>
          handleUpdateCode("USER", {
            id: data.id,
            driverCode,
            language,
            solutionCode,
            userCode,
          })
        }
        setCode={setUserCode}
      />

      <CodeSection
        name="SOLUTION"
        language={language}
        code={solutionCode}
        newEntry={!data.id}
        disabled={data.solutionCode === solutionCode}
        setCode={setSolutionCode}
        onSave={() =>
          handleUpdateCode("SOLUTION", {
            id: data.id,
            driverCode,
            language,
            solutionCode,
            userCode,
          })
        }
      />
      {!data.id && (
        <Button
          className="w-full max-w-sm"
          onClick={() =>
            handleAddCode({
              driverCode,
              language,
              solutionCode,
              userCode,
              id: null,
            })
          }
        >
          Upload
        </Button>
      )}
    </div>
  );
}

function CodeSection({
  name,
  language,
  code,
  newEntry,
  disabled,
  onSave,
  setCode,
}: {
  name: string;
  language: LanguageType;
  code: string;
  disabled: boolean;
  newEntry: boolean;
  onSave: () => void;
  setCode: Dispatch<SetStateAction<string>>;
}) {
  return (
    <div className="h-[400px] w-full max-w-4xl">
      <div className="mb-2 flex items-center justify-center gap-2">
        <h2 className="text-lg font-semibold">{name} CODE</h2>

        {newEntry === false && (
          <Button variant="outline" onClick={onSave} disabled={disabled}>
            Save <Save />
          </Button>
        )}
      </div>
      <CodeEditor
        language={language}
        code={code}
        setCodeFn={(code) => setCode(code ?? "")}
      />
    </div>
  );
}
