import { Badge } from "@/components/ui/badge";

export default function TotalDataBadge(props: { count: number | string }) {
  return (
    <Badge className="absolute top-0 -right-8 bg-green-500/20 font-semibold text-green-500">
      {props.count}
    </Badge>
  );
}
