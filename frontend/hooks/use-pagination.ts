import { useState } from "react";

export type PaginationState = {
  page: number;
  pageSize: number;
  nextPage: () => void;
  prevPage: () => void;
  firstPage: () => void;
  jumpPage: (newPage: number) => void;
  setPageSize: (newSize: string | number) => void;
};
export default function usePagination(defaultSize: number = 5): PaginationState {
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(defaultSize);

  const nextPage = () => setPage(page + 1);
  const prevPage = () => setPage(page && page - 1);
  const firstPage = () => setPage(0);
  const jumpPage = (newPage: number) => setPage(newPage);
  const setPageSize = (newSize: string | number) => {
    newSize = isNaN(+newSize) ? size : +newSize;
    setSize(newSize);
    if (newSize !== size) setPage(0);
  };

  return { page, pageSize: size, nextPage, prevPage, firstPage, jumpPage, setPageSize };
}
