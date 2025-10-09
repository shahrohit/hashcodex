import { PropsWithChildren } from "react";

function TabListContainer({ children }: PropsWithChildren) {
  return <div className="hide-scrollbar overflow-scroll rounded-md">{children}</div>;
}

export default TabListContainer;
