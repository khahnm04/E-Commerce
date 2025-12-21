import React from 'react';
import { ProductDetails } from '@/app/components/product-detail/ProductDetails';
import {
  PRODUCT,
  VERSIONS,
  COLORS,
  COMMITMENTS,
  SPECS,
  DESCRIPTION_CONTENT,
  RELATED_NEWS,
  SIMILAR_PRODUCTS,
  REVIEWS,
  FEATURE_RATINGS,
  RATING_DISTRIBUTION,
  REVIEW_FILTERS,
  QNA_QUESTIONS
} from '@/app/components/product-detail/constants';

export default function ProductDetailPage({ params }: { params: { slug: string } }) {
  // TODO: Fetch product data based on params.slug
  console.log('Product slug:', params.slug);

  return (
    <ProductDetails
      product={PRODUCT}
      versions={VERSIONS}
      colors={COLORS}
      commitments={COMMITMENTS}
      specs={SPECS}
      descriptionContent={DESCRIPTION_CONTENT}
      relatedNews={RELATED_NEWS}
      similarProducts={SIMILAR_PRODUCTS}
      reviews={REVIEWS}
      featureRatings={FEATURE_RATINGS}
      ratingDistribution={RATING_DISTRIBUTION}
      reviewFilters={REVIEW_FILTERS}
      qnaQuestions={QNA_QUESTIONS}
    />
  );
}