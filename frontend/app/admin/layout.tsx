import AdminUser from "@/app/admin/_components/admin-user";
import { PropsWithChildren } from "react";

export default function AdminLayout({ children }: PropsWithChildren) {
  return (
    <AdminUser>
      <main className="container mx-auto my-5 border-2 rounded-sm p-5">
        {children}
      </main>
    </AdminUser>
  );
}
