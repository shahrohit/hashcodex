import { CreateTopicType } from "@/app/admin/_schemas/create-topic-schema";
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
import { Spinner } from "@/components/ui/spinner";
import { FormProps } from "@/types/utility";
import { Heading, Link } from "lucide-react";

export default function CreateTopicFormUI({
  form,
  onSubmit,
  submitPending,
}: FormProps<CreateTopicType>) {
  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-3">
        <FormField
          control={form.control}
          name="name"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Title</FormLabel>
              <FormControl>
                <InputWithIcon
                  Icon={Heading}
                  placeholder="Topic Name"
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
                  placeholder="Topic Slug"
                  field={field}
                />
              </FormControl>
              <FormMessage />
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
