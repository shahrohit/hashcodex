import ProblemListing from "@/app/(public)/_components/problem-listing";
import TopicListing from "@/app/(public)/_components/topic-listing";

export default function Home() {
  return (
    <>
      <TopicListing />
      <ProblemListing />
    </>
  );
}
