import ProblemDetailComponent from "@/app/admin/_components/problem-detail-component";

type Props = {
  params: Promise<{ slug: string }>;
};

export default async function ProblemDetailPage({ params }: Props) {
  const { slug } = await params;
  return <ProblemDetailComponent slug={slug} />;
}
