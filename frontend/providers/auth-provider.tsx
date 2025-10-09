"use client";

import api from "@/lib/api";
import User from "@/types/user";
import { Nullable } from "@/types/utility";
import { usePathname } from "next/navigation";
import {
  createContext,
  Dispatch,
  PropsWithChildren,
  SetStateAction,
  useEffect,
  useState,
} from "react";

type AuthContextType = {
  user: Nullable<User>;
  isPending: boolean;
  setUser: Dispatch<SetStateAction<Nullable<User>>>;
};

export const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: PropsWithChildren) {
  const [user, setUser] = useState<Nullable<User>>(null);
  const [isPending, setIsPending] = useState(true);
  const pathname = usePathname();

  useEffect(() => {
    if (pathname.startsWith("/auth")) {
      setIsPending(false);
      return;
    }

    void (async () => {
      try {
        const res = await api.get<User>("/users/me");

        setUser(res.data);
      } catch {
        setUser(null);
      } finally {
        setIsPending(false);
      }
    })();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <AuthContext.Provider value={{ user, isPending, setUser }}>
      {children}
    </AuthContext.Provider>
  );
}
