import axios from "axios";
import { env } from "@/lib/env";

const api = axios.create({
  baseURL: `${env.NEXT_PUBLIC_BACKEND_URL}/api`,
  withCredentials: true,
});

export default api;
