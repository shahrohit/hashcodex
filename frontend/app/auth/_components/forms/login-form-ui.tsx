import PasswordField from "@/app/auth/_components/ui/password-field";
import { LoginType } from "@/app/auth/_schemas/user-login-schema";
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
import { Mail } from "lucide-react";

export default function LoginFormUi({
  form,
  onSubmit,
  submitPending,
  onForgetPassword,
}: FormProps<LoginType, { onForgetPassword: () => void }>) {
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
                <InputWithIcon
                  Icon={Mail}
                  field={field}
                  placeholder="me@example.com"
                  type="email"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <div className="flex flex-col">
          <FormField
            control={form.control}
            name="password"
            render={({ field }) => <PasswordField field={field} />}
          />

          <Button
            type="button"
            className="cursor-pointer self-end"
            variant="link"
            onClick={onForgetPassword}
          >
            Forget Password?
          </Button>
        </div>
        <ErrorMessage message={form.formState.errors.root?.message} />
        <Button
          type="submit"
          disabled={submitPending || !form.formState.isDirty}
        >
          Login
        </Button>
      </form>
    </Form>
  );
}
