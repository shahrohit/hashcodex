function NoData(props: { message: string }) {
  return (
    <div className="flex h-full w-full items-center justify-center">
      <span className="text-muted-foreground text-lg font-medium">{props.message}</span>
    </div>
  );
}

export default NoData;
