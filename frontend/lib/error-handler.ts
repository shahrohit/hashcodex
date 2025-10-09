import ErrorResponse, { ErrorCode } from "@/types/error-response";
import { AxiosError } from "axios";
import { FieldValues, Path, UseFormReturn } from "react-hook-form";

export function getErrorMessage(err: Error | undefined) {
  if (!(err instanceof AxiosError)) return "Something Went Wrong";

  if (!err.response) {
    if (err.code === "ERR_CONNECT") return "Please check your internet connection.";
    return "Something went wrong";
  }
  const data = err.response.data as ErrorResponse;
  return data.message;
}

export function getError(err: Error | undefined): ErrorResponse {
  if (!(err instanceof AxiosError))
    return { code: ErrorCode.CUSTOM, message: "Something Went Wrong", errors: {} };

  if (!err.response) {
    let message = "Something Went Wrong";
    if (err.code === "ERR_CONNECT") message = "Please check your internet connection.";
    return { code: ErrorCode.CUSTOM, message, errors: {} };
  }

  return err.response.data as ErrorResponse;
}

export function handleFormError<T extends FieldValues>(
  form: UseFormReturn<T>,
  err: Error | undefined,
): string {
  const error = getError(err);
  if (error.code === ErrorCode.CUSTOM) return error.message;

  if (error.code === ErrorCode.VALIDATION_ERROR && error.errors) {
    for (const [field, message] of Object.entries(error.errors)) {
      form.setError(field as Path<T>, {
        type: "server",
        message,
      });
    }
    return "Please check the form fields.";
  }

  form.setError("root", { type: "server", message: error.message });
  return error.message;
}
