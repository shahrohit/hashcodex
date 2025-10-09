export default function parseDate(date: string) {
  return new Date(date).toLocaleString("en-US", {
    dateStyle: "medium",
    timeStyle: "short",
  });
}

export function parseDateOnly(date: string) {
  return new Date(date).toLocaleString("en-US", {
    dateStyle: "medium",
  });
}
