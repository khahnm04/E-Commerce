import { BaseResponse } from "@/models/base.model";

export enum CategoryStatus {
  ACTIVE = 'active',
  INACTIVE = 'inactive',
}

export interface CategoryResponse extends BaseResponse<number> {
  name: string;
  slug: string;
  description?: string;
  image?: string;
  parentId?: number | null;
  position?: number;
  path?: string;
  status: CategoryStatus;
}

export interface CategoryRequest {
  name: string;
  slug: string;
  description?: string;
  status: CategoryStatus | string;
  parentId?: number | null;
  position?: number;
}

export interface UpdateStatusRequest {
  status: CategoryStatus | string;
}

export interface AssignProductToCategoryRequest {
  productIds: number[];
}

export interface CategorySearchParams {
  page?: number;
  size?: number;
  keyword?: string;
  status?: CategoryStatus;
  sort?: string;
}

export interface CategoryBulkActionRequest {
  categoryIds: number[];
  action: 'ACTIVE' | 'INACTIVE' | 'DELETE' | string;
}