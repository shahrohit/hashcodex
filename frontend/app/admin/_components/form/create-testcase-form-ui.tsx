import { CreateTestcaseType } from "@/app/admin/_schemas/create-testcase-schema";
import ErrorMessage from "@/components/error-message";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Spinner } from "@/components/ui/spinner";
import { Switch } from "@/components/ui/switch";
import { Textarea } from "@/components/ui/textarea";
import { FormProps } from "@/types/utility";

export default function CreateTestcaseFormUI({
  form,
  onSubmit,
  submitPending,
}: FormProps<CreateTestcaseType>) {
  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-3">
        <FormField
          control={form.control}
          name="input"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Input Testcases</FormLabel>
              <FormControl>
                <div className="relative w-full">
                  <Textarea
                    className="h-48 resize-none"
                    placeholder="Each input paramters should be seprated by two lines"
                    {...field}
                  />
                </div>
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="output"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Expected Output</FormLabel>
              <FormControl>
                <div className="relative w-full">
                  <Textarea
                    className="h-20 resize-none"
                    placeholder="Expected Output of the Testcase"
                    {...field}
                  />
                </div>
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="sample"
          render={({ field }) => (
            <FormItem className="bg-background flex flex-row items-center justify-between rounded-lg border p-3 shadow-sm">
              <div className="space-y-0.5">
                <FormLabel>Sample Testcases</FormLabel>
                <FormDescription>
                  Make this testcase to visible to user
                </FormDescription>
              </div>
              <FormControl>
                <Switch
                  checked={field.value}
                  onCheckedChange={field.onChange}
                />
              </FormControl>
            </FormItem>
          )}
        />

        <ErrorMessage message={form.formState.errors.root?.message} />

        <Button
          type="submit"
          disabled={submitPending || !form.formState.isDirty}
          className="w-full"
        >
          {submitPending ? <Spinner /> : "Create"}
        </Button>
      </form>
    </Form>
  );
}
