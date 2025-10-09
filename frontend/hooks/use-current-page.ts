import { usePathname } from "next/navigation";

export default function useCurrentPage(index: number = 1) {
  return usePathname().split("/").filter(Boolean)[index - 1] ?? "";
}
