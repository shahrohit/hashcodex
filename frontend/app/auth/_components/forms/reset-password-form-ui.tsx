import PasswordField from "@/app/auth/_components/ui/password-field";
import { ResetPasswordType } from "@/app/auth/_schemas/reset-password-schema";
import ErrorMessage from "@/components/error-message";
import { Button } from "@/components/ui/button";
import { Form, FormField } from "@/components/ui/form";
import { FormProps } from "@/types/utility";

export default function ResetPasswordFormUi({
  form,
  onSubmit,
  submitPending,
}: FormProps<ResetPasswordType>) {
  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
        <FormField
          control={form.control}
          name="password"
          render={({ field }) => <PasswordField field={field} />}
        />

        <FormField
          control={form.control}
          name="confirmPassword"
          render={({ field }) => (
            <PasswordField field={field} label="Confirm Password" />
          )}
        />

        <ErrorMessage message={form.formState.errors.root?.message} />
        <Button type="submit" disabled={submitPending} className="w-full">
          Reset
        </Button>
      </form>
    </Form>
  );
}
