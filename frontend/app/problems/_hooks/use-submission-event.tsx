"use client";

import Helpers from "@/lib/helpers";
import { SubmissionResult } from "@/types/problems";
import { useEffect, useRef, useState } from "react";

export function useSubmissionEvent(correlationId: string | null) {
  const [result, setResult] = useState<SubmissionResult | null>(null);
  const esRef = useRef<EventSource | null>(null);

  useEffect(() => {
    if (!correlationId) return;

    // Close any previous stream before opening a new one
    esRef.current?.close();

    const es = new EventSource(
      `${Helpers.generateBackendURL()}/problems/submissions/events/${correlationId}`,
      { withCredentials: true }
    );

    // Final verdict
    const onVerdict = (e: Event) => {
      const event = e as Event & { data: string };
      const payload: SubmissionResult = JSON.parse(event.data);
      console.log(payload);
      setResult(payload);
      esRef.current?.close();
    };

    // es.addEventListener("snapshot", onSnapshot);
    es.addEventListener("verdict", onVerdict);

    es.onerror = () => {
      // optional: show "reconnecting…" UI; the polyfill retries by default
    };

    esRef.current = es;
    return () => es.close();
  }, [correlationId]); // re-open if token changes

  return result;
}
