import { ForgetPasswordType } from "@/app/auth/_schemas/forget-password-schema";
import ErrorMessage from "@/components/error-message";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Spinner } from "@/components/ui/spinner";
import { FormProps } from "@/types/utility";
import { Mail, Send } from "lucide-react";

function ForgetPasswordFormUi({
  form,
  onSubmit,
  submitPending,
  isSuccess,
}: FormProps<ForgetPasswordType, { isSuccess: boolean }>) {
  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="flex flex-col gap-2"
      >
        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <div className="relative w-full max-w-sm">
                  <Mail
                    className="text-muted-foreground absolute top-1/2 left-3 -translate-y-1/2"
                    size={16}
                  />
                  <Input
                    id="email"
                    type="email"
                    className="pl-8"
                    placeholder="me@example.com"
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
          disabled={submitPending || isSuccess}
          className="cursor-pointer"
        >
          {submitPending ? (
            <Spinner />
          ) : (
            <>
              <span>Send</span>
              <Send />
            </>
          )}
        </Button>
      </form>
    </Form>
  );
}

export default ForgetPasswordFormUi;
