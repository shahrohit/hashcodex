import { CreateProblemType } from "@/app/admin/_schemas/create-problem-schema";
import ErrorMessage from "@/components/error-message";
import InputWithIcon from "@/components/input-with-icon";
import MarkDownEditor from "@/components/markdown-editor";
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
import { Spinner } from "@/components/ui/spinner";
import { Textarea } from "@/components/ui/textarea";
import ProblemDifficulty from "@/types/problem-difficulty";
import { FormProps } from "@/types/utility";
import { Heading, Link } from "lucide-react";

export default function CreateProblemFormUI({
  form,
  onSubmit,
  submitPending,
}: FormProps<CreateProblemType>) {
  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-3">
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
          name="slug"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Slug</FormLabel>
              <FormControl>
                <InputWithIcon
                  Icon={Link}
                  placeholder="Problem Slug"
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
                <FormControl>
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
          name="description"
          render={({ field }) => (
            <FormItem className="">
              <FormLabel>Description</FormLabel>
              <FormControl>
                <MarkDownEditor
                  value={field.value}
                  setValue={(text?: string) => field.onChange(text)}
                />
              </FormControl>
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

        <Button type="submit" disabled={submitPending} className="w-full">
          {submitPending ? <Spinner /> : "Create"}
        </Button>
      </form>
    </Form>
  );
}
