import { TabsList, TabsTrigger } from "@/components/ui/tabs";
import { FileDigitIcon, ListOrderedIcon } from "lucide-react";

export default function ProblemTestcaseTabHeader() {
  return (
    <TabsList className="bg-background rounded-none px-2 *:min-w-fit *:rounded-[8px] *:px-5">
      <TabsTrigger value="testcase">
        <ListOrderedIcon className="-ms-0.5 me-1.5 opacity-60" size={16} aria-hidden="true" />
        Test Cases
      </TabsTrigger>
      <TabsTrigger value="result" className="group">
        <FileDigitIcon className="-ms-0.5 me-1.5 opacity-60" size={16} aria-hidden="true" />
        Test Result
      </TabsTrigger>
    </TabsList>
  );
}
