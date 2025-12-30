export interface PaginationMeta {
  pageNo: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  isFirst?: boolean;
  isLast?: boolean;
  isEmpty?: boolean;
}

export interface ApiResponse<T> {
  timestamp: string;
  success: boolean;
  message: string;
  data: T;
  meta?: PaginationMeta;
}

export interface BaseResponse<T = number> {
  id: T;
  createdAt?: string;
  createdBy?: number;
  updatedAt?: string;
  updatedBy?: number;
  deletedAt?: string;
}

export interface SelectOption {
  value: string | number;
  label: string;
}

export interface FieldError {
  field: string;
  message: string;
}

export interface ErrorResponse {
  timestamp: string;
  success: boolean;
  code: number;
  message: string;
  path: string;
  method: string;
  errors?: FieldError[];
}