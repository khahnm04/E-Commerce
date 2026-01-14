/* eslint-disable @typescript-eslint/no-explicit-any */
import axiosClient from "@/lib/axiosClient";
import { ApiResponse } from "@/models/base.model";
import {
  CategoryRequest,
  CategoryResponse,
  CategorySearchParams,
  UpdateStatusRequest,
  CategoryStatus,
  CategoryBulkActionRequest
} from "@/models/category.model";

const BASE_URL = "/admin/categories";

export const categoryService = {

  getAll: async (params?: CategorySearchParams): Promise<ApiResponse<CategoryResponse[]>> => {
    return axiosClient.get(BASE_URL, { params });
  },

  getAllDeleted: async (params?: CategorySearchParams): Promise<ApiResponse<CategoryResponse[]>> => {
    return axiosClient.get(`${BASE_URL}/deleted`, { params });
  },

  getById: async (id: number): Promise<ApiResponse<CategoryResponse>> => {
    return axiosClient.get(`${BASE_URL}/${id}`);
  },

  getBySlug: async (slug: string): Promise<ApiResponse<CategoryResponse>> => {
    return axiosClient.get(`${BASE_URL}/slug/${slug}`);
  },

  create: async (data: CategoryRequest, imageFile: File): Promise<ApiResponse<CategoryResponse>> => {
    const formData = new FormData();

    const jsonBlob = new Blob([JSON.stringify(data)], {
      type: 'application/json',
    });
    formData.append("data", jsonBlob);
    formData.append("image", imageFile);

    return axiosClient.post(BASE_URL, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },

  update: async (id: number, data: CategoryRequest, imageFile?: File): Promise<ApiResponse<CategoryResponse>> => {
    const formData = new FormData();

    const jsonBlob = new Blob([JSON.stringify(data)], {
      type: 'application/json',
    });
    formData.append("data", jsonBlob);

    if (imageFile) {
      formData.append("image", imageFile);
    }

    return axiosClient.put(`${BASE_URL}/${id}`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },

  updateStatus: async (id: number, status: CategoryStatus | string): Promise<ApiResponse<void>> => {
    const payload: UpdateStatusRequest = { status };
    return axiosClient.patch(`${BASE_URL}/${id}/status`, payload);
  },

  softDelete: async (id: number): Promise<ApiResponse<void>> => {
    return axiosClient.delete(`${BASE_URL}/${id}/soft-delete`);
  },

  restore: async (id: number): Promise<ApiResponse<void>> => {
    return axiosClient.patch(`${BASE_URL}/${id}/restore`);
  },

  bulkAction: async (data: CategoryBulkActionRequest): Promise<any> => {
    try {
      const response = await axiosClient.patch(`${BASE_URL}/bulk`, data);
      return response;
    } catch (error: any) {
      throw error.response?.data || error;
    }
  },

};
