import PasswordField from "@/app/auth/_components/ui/password-field";
import { RegisterType } from "@/app/auth/_schemas/user-register-schema";
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
import { FormProps } from "@/types/utility";
import { Mail, User } from "lucide-react";

export default function RegisterFormUi({
  form,
  onSubmit,
  submitPending,
}: FormProps<RegisterType>) {
  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="flex flex-col gap-4"
      >
        <FormField
          control={form.control}
          name="name"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Name</FormLabel>
              <FormControl>
                <InputWithIcon
                  Icon={User}
                  field={{
                    ...field,
                    spellCheck: false,
                    autoCapitalize: "words",
                  }}
                  placeholder="Full Name"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <InputWithIcon
                  Icon={Mail}
                  placeholder="me@example.com"
                  field={field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="password"
          render={({ field }) => <PasswordField field={field} />}
        />

        <ErrorMessage message={form.formState.errors.root?.message} />

        <Button
          type="submit"
          disabled={submitPending || !form.formState.isDirty}
        >
          Create Account
        </Button>
      </form>
    </Form>
  );
}
