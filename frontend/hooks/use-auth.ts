import { useContext } from "react";
import { AuthContext } from "@/providers/auth-provider";

const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error("Something Went Wrong!!");
  return context;
};

export default useAuth;
