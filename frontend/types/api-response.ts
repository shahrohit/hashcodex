export enum ResponseCode {
  SENT = "SENT",
}

export interface PaginatedResponse<T> {
  items: T[];
  totalPages: number;
  totalItems: number;
  currentPage: number;
  pageSize: number;
}

export interface ApiResponse<T> {
  code: string;
  data: T;
}
