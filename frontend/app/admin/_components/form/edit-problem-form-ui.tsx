import { UpdateProblemBasicType } from "@/app/admin/_schemas/update-problem-basic-schema";
import ErrorMessage from "@/components/error-message";
import InputWithIcon from "@/components/input-with-icon";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import ProblemDifficulty from "@/types/problem-difficulty";
import { FormProps } from "@/types/utility";
import { Heading, Save } from "lucide-react";

export default function EditProblemBasicFormUI({
  form,
  submitPending,
  onSubmit,
}: FormProps<UpdateProblemBasicType>) {
  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="grid gap-4">
        <FormField
          control={form.control}
          name="title"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Title</FormLabel>
              <FormControl>
                <InputWithIcon
                  Icon={Heading}
                  placeholder="Problem Title"
                  field={field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="difficulty"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Difficulty</FormLabel>
              <Select onValueChange={field.onChange} defaultValue={field.value}>
                <FormControl className="w-full max-w-sm">
                  <SelectTrigger>
                    <SelectValue placeholder="Select Difficulty" />
                  </SelectTrigger>
                </FormControl>
                <SelectContent>
                  <SelectItem value={ProblemDifficulty.EASY}>
                    <span className="text-green-500">Easy</span>
                  </SelectItem>
                  <SelectItem value={ProblemDifficulty.MEDIUM}>
                    <span className="text-yellow-500">Medium</span>
                  </SelectItem>
                  <SelectItem value={ProblemDifficulty.HARD}>
                    <span className="text-destructive">Hard</span>
                  </SelectItem>
                </SelectContent>
              </Select>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="params"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Input Parameters</FormLabel>
              <FormControl>
                <div className="relative w-full">
                  <Textarea
                    className="h-16 resize-none"
                    placeholder="Each input parameters should be in seprate line"
                    {...field}
                  />
                </div>
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <ErrorMessage message={form.formState.errors.root?.message} />

        <Button
          type="submit"
          variant="secondary"
          disabled={submitPending || !form.formState.isDirty}
        >
          Save <Save />
        </Button>
      </form>
    </Form>
  );
}
