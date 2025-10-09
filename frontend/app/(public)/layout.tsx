import NavBar from "@/components/nav-bar";
import { ReactNode } from "react";

export default function PublicLayout({ children }: { children: ReactNode }) {
  return (
    <>
      <NavBar />
      <main className="py-5 container mx-auto">{children}</main>
    </>
  );
}
