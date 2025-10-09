import { Badge } from "@/components/ui/badge";

export default function DifficultyBadge({ difficulty }: { difficulty: string }) {
  if (difficulty === "EASY") return <Badge className="bg-green-500/20 text-green-500">Easy</Badge>;
  else if (difficulty === "MEDIUM")
    return <Badge className="bg-yellow-500/20 text-yellow-500">Medium</Badge>;
  else if (difficulty === "HARD")
    return <Badge className="bg-destructive/20 text-destructive">Hard</Badge>;
  return null;
}
