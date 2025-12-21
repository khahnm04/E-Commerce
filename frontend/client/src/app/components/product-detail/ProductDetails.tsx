"use client"
import React, { useState } from 'react';
import {
  ProductHeader,
  ProductGallery,
  ProductCommitments,
  ProductSpecs,
  ProductDescription,
  ProductPrice,
  ProductVersionSelector,
  ProductColorSelector,
  ProductShipping,
  ProductPromotions,
  ProductActions,
  ProductRelatedNews,
  ProductSimilar,
  ProductReviews,
  ProductQnA,
} from '.';
import type {
  Product,
  ProductVersion,
  ProductColor,
  Commitment,
  Spec,
  DescriptionBlock,
  Review,
  FeatureRating,
  RatingDistribution,
  ReviewFilter,
  QnAQuestion,
  RelatedNews,
  SimilarProduct
} from './types';

interface ProductDetailsProps {
  product: Product;
  versions: ProductVersion[];
  colors: ProductColor[];
  commitments: Commitment[];
  specs: Spec[];
  descriptionContent: DescriptionBlock[];
  relatedNews: RelatedNews[];
  similarProducts: SimilarProduct[];
  reviews: Review[];
  featureRatings: FeatureRating[];
  ratingDistribution: RatingDistribution[];
  reviewFilters: ReviewFilter[];
  qnaQuestions: QnAQuestion[];
}

export function ProductDetails({
  product,
  versions,
  colors,
  commitments,
  specs,
  descriptionContent,
  relatedNews,
  similarProducts,
  reviews,
  featureRatings,
  ratingDistribution,
  reviewFilters,
  qnaQuestions
}: ProductDetailsProps) {
  const [selectedVersion, setSelectedVersion] = useState("Fit 4");
  const [selectedColor, setSelectedColor] = useState("Đen");

  const handleBuyNow = () => {
    console.log('Buy now:', { version: selectedVersion, color: selectedColor });
    // TODO: Implement buy now logic
  };

  const handleAddToCart = () => {
    console.log('Add to cart:', { version: selectedVersion, color: selectedColor });
    // TODO: Implement add to cart logic
  };

  const handleInstallment = () => {
    console.log('Installment clicked');
    // TODO: Implement installment logic
  };

  const handleWriteReview = () => {
    console.log('Write review clicked');
    // TODO: Implement write review logic
  };

  const handleFilterChange = (filter: string) => {
    console.log('Filter changed:', filter);
    // TODO: Implement filter logic
  };

  const handleViewAllReviews = () => {
    console.log('View all reviews');
    // TODO: Implement view all reviews logic
  };

  const handleSubmitQuestion = (question: string) => {
    console.log('Question submitted:', question);
    // TODO: Implement submit question logic
  };

  const handleViewMoreQnA = () => {
    console.log('View more QnA');
    // TODO: Implement view more QnA logic
  };

  return (
    <div className="bg-gray-100 py-4">
      <div className="max-w-[1232px] mx-auto px-4">
        <ProductHeader product={product} />

        <main className="product-detail-main mt-4">
          <div className="grid grid-cols-1 lg:grid-cols-12 lg:gap-[20px]">

            {/* === SỬA TẠI ĐÂY === */}
            {/* GỘP CỘT TRÁI: Bao gồm Gallery VÀ Các thông tin chi tiết bên dưới */}
            <div className="lg:col-span-7 flex flex-col gap-[15px]">

              {/* 1. Gallery nằm ở đầu */}
              <ProductGallery product={product} />

              {/* 2. Các phần thông tin chi tiết nối tiếp ngay bên dưới */}
              <div>
                <ProductCommitments commitments={commitments} />
              </div>
              <ProductSpecs specs={specs} initialVisibleSpecs={5} />
              <ProductDescription content={descriptionContent} />
            </div>

            {/* Cột Phải - Giữ nguyên */}
            <aside className="lg:col-span-5 flex flex-col gap-[15px]">
              <ProductPrice product={product} />
              <div className="bg-white p-4 rounded-lg shadow-sm">
                <ProductVersionSelector
                  versions={versions}
                  selectedVersion={selectedVersion}
                  onVersionChange={setSelectedVersion}
                />
                <ProductColorSelector
                  colors={colors}
                  selectedColor={selectedColor}
                  productImage={product.image}
                  onColorChange={setSelectedColor}
                />
              </div>
              <ProductShipping />
              <ProductPromotions />
              <ProductActions
                onBuyNow={handleBuyNow}
                onAddToCart={handleAddToCart}
                onInstallment={handleInstallment}
              />
              <ProductRelatedNews news={relatedNews} />
              <ProductSimilar products={similarProducts} productImage={product.image} />
            </aside>

          </div>
        </main>
        <ProductReviews
          product={product}
          reviews={reviews}
          featureRatings={featureRatings}
          ratingDistribution={ratingDistribution}
          filters={reviewFilters}
          onWriteReview={handleWriteReview}
          onFilterChange={handleFilterChange}
          onViewAll={handleViewAllReviews}
        />
        <ProductQnA
          product={product}
          questions={qnaQuestions}
          onSubmitQuestion={handleSubmitQuestion}
          onViewMore={handleViewMoreQnA}
        />
      </div>
    </div>
  );
}
