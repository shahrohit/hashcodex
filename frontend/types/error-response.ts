export enum ErrorCode {
  CUSTOM = "CUSTOM",
  VALIDATION_ERROR = "VALIDATION_ERROR",
}

type ErrorResponse = {
  code: string;
  message: string;
  errors: Record<string, string>;
};

export default ErrorResponse;
