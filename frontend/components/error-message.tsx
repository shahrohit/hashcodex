export default function ErrorMessage({ message }: { message: string | undefined | null }) {
  return message && <p className="mb-1 text-sm font-medium text-red-500">{message}</p>;
}
