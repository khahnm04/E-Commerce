import { ReactNode } from 'react';

export interface ProductListItem {
  id: number | string;
  name: string;
  price: string;
  oldPrice?: string;
  discount?: string;
  rating?: number;
  reviews?: number;
  reviewCount?: number; // Alias for reviews for backward compatibility
  isInstallment?: boolean;
  tags?: string[];
  image: string;
  promoText?: string;
  slug?: string;
}

export interface Brand {
  id: string;
  name: string;
  img?: string;
}

export interface QuickFilter {
  label: string;
  icon?: ReactNode;
  value?: string;
}

export interface SortOption {
  label: string;
  value: string;
}

