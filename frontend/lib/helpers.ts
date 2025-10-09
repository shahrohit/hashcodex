import Constant from "@/lib/constants";
import { env } from "@/lib/env";

function generateBackendURL() {
  return env.NEXT_PUBLIC_BACKEND_URL + Constant.API_URL;
}

async function sleep(ms: number = 1000) {
  return new Promise((res) => setTimeout(res, ms));
}

function getSubmisionMessage(status: string) {
  if (status === "CTE") return "Compile-Time Error";
  if (status === "RTE") return "Run-Time Error";
  if (status === "TLE") return "Time Limit Exceed";
  if (status === "MLE") return "Memory Limit Exceed";
  if (status === "SOLVED") return "Accepted";
  if (status === "WA") return "Wrong Answer";
  if (status === "SERVER_ERROR") return "Server Error";
  return "";
}
const Helpers = {
  generateBackendURL,
  sleep,
  getSubmisionMessage,
};

export default Helpers;
