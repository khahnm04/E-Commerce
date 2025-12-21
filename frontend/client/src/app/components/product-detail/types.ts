import { ReactNode } from 'react';

export interface Product {
  name: string;
  rating: number;
  reviews: number;
  price: string;
  oldPrice: string;
  image: string;
  thumbnails: Thumbnail[];
}

export interface Thumbnail {
  type: 'video' | 'feature' | 'img';
  icon?: ReactNode;
  label?: string;
  src?: string;
}

export interface ProductVersion {
  name: string;
}

export interface ProductColor {
  name: string;
  price: string;
  image?: string;
}

export interface Commitment {
  icon: ReactNode;
  desc: ReactNode;
}

export interface Spec {
  k: string;
  v: string;
}

export interface DescriptionBlock {
  type: 'text' | 'image' | 'header';
  content?: string;
  src?: string;
  alt?: string;
}

export interface Review {
  id: number;
  name: string;
  avatarColor: string;
  rating: number;
  ratingText: string;
  time: string;
  verified: boolean;
  content: string;
  tags: string[];
}

export interface FeatureRating {
  label: string;
  score: number;
  count: number;
}

export interface RatingDistribution {
  star: number;
  percentage: number;
  count: number;
}

export interface ReviewFilter {
  label: string;
  active: boolean;
}

export interface QnAQuestion {
  user: string;
  userInitial: string;
  question: string;
  time: string;
  admin: string | null;
  adminInitial: string | null;
  answer: string | null;
}

export interface RelatedNews {
  img: string;
  title: string;
}

export interface SimilarProduct {
  n: string;
  p: string;
}

export interface Promotion {
  number: number;
  content: ReactNode;
}

