"use client";
import useQueryTopics from "@/app/(public)/hooks/use-query-topics";
import useAddTopics from "@/app/admin/_hooks/use-add-topics";
import { Button } from "@/components/ui/button";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { getErrorMessage } from "@/lib/error-handler";
import { cn } from "@/lib/utils";
import { TopicItem } from "@/types/problems";
import { CheckIcon, ChevronsUpDown } from "lucide-react";
import { Dispatch, SetStateAction, useState } from "react";
import { toast } from "sonner";

export default function AddProblemTopic({ number }: { number: number }) {
  const [selectedTopic, setSelectedTopic] = useState<TopicItem | undefined>(
    undefined
  );
  const { mutateAsync } = useAddTopics(number);

  function handleAddTopic() {
    if (!selectedTopic) {
      toast.error("Select a topic");
      return;
    }

    toast.promise(mutateAsync(selectedTopic), {
      loading: "Adding...",
      success: () => {
        setSelectedTopic(undefined);
        return "Added";
      },
      error: getErrorMessage,
    });
  }

  return (
    <div className="p-2">
      <h2 className="text-muted-foreground mb-2 text-base font-semibold">
        Select Topic
      </h2>
      <div className="flex flex-col gap-2 sm:flex-row">
        <TopicSelect
          selectedTopic={selectedTopic}
          setSelectedTopic={setSelectedTopic}
        />
        <Button
          className="w-full sm:w-[200px]"
          onClick={handleAddTopic}
          disabled={!selectedTopic}
        >
          Add
        </Button>
      </div>
    </div>
  );
}

function TopicSelect({
  selectedTopic,
  setSelectedTopic,
}: {
  selectedTopic: TopicItem | undefined;
  setSelectedTopic: Dispatch<SetStateAction<TopicItem | undefined>>;
}) {
  const [open, setOpen] = useState(false);
  const { data } = useQueryTopics();

  const selectedOption: TopicItem | undefined = data?.find(
    (item) => item.slug === selectedTopic?.slug
  );

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button
          variant="outline"
          role="combobox"
          aria-expanded={open}
          className="w-full max-w-sm flex-1 justify-between"
        >
          <span className="truncate">
            {selectedOption?.name || "Select topic..."}
          </span>
          <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
        </Button>
      </PopoverTrigger>

      <PopoverContent className="w-[90vw] p-0 sm:w-sm">
        <Command>
          <CommandInput placeholder="Search..." />
          <CommandEmpty>No Topics</CommandEmpty>
          <CommandList>
            <CommandGroup>
              {data?.map((item) => (
                <CommandItem
                  key={item.slug}
                  value={item.slug}
                  onSelect={() => {
                    setSelectedTopic(item);
                    setOpen(false);
                  }}
                >
                  <CheckIcon
                    className={cn(
                      "mr-2 h-4 w-4",
                      item.slug === selectedTopic?.slug
                        ? "opacity-100"
                        : "opacity-0"
                    )}
                  />
                  {item.name}
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
}
