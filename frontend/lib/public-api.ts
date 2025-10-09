import axios from "axios";
import { env } from "@/lib/env";

const publicApi = axios.create({
  baseURL: `${env.NEXT_PUBLIC_BACKEND_URL}/api`,
  withCredentials: false,
});

export default publicApi;
