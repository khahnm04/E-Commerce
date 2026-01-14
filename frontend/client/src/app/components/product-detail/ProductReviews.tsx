import React from 'react';
import { Star, ChevronRight, CheckCircle2, ThumbsUp, MessageSquare } from 'lucide-react';
import type { Product, Review, FeatureRating, RatingDistribution, ReviewFilter } from './types';

interface ProductReviewsProps {
  product: Product;
  reviews: Review[];
  featureRatings: FeatureRating[];
  ratingDistribution: RatingDistribution[];
  filters: ReviewFilter[];
  onWriteReview?: () => void;
  onFilterChange?: (filter: string) => void;
  onViewAll?: () => void;
}

export function ProductReviews({
  product,
  reviews,
  featureRatings,
  ratingDistribution,
  filters,
  onWriteReview,
  onFilterChange,
  onViewAll
}: ProductReviewsProps) {
  return (
    <article className="product-reviews-container bg-white border border-gray-200 rounded-[10px] p-5 shadow-sm mt-[20px]">
      <h2 className="product-reviews-title text-lg font-bold text-gray-800 mb-4">
        Đánh giá {product.name}
      </h2>

      {/* Reviews Dashboard */}
      <div className="product-reviews-dashboard flex flex-col lg:flex-row border border-gray-200 rounded-[10px] p-6 mb-6 gap-8">

        {/* Rating Summary */}
        <div className="product-reviews-summary w-full lg:w-[200px] flex flex-col items-center justify-center border-b lg:border-b-0 lg:border-r border-gray-200 pb-6 lg:pb-0 lg:pr-6 gap-2 shrink-0">
          <div className="product-reviews-rating flex items-end leading-none text-[#d70018]">
            <span className="text-[42px] font-bold">{product.rating}</span>
            <span className="text-2xl font-bold text-gray-400">/5</span>
          </div>
          <div className="product-reviews-stars flex items-center gap-1 text-yellow-400">
            {Array(5).fill(0).map((_, i) => (
              <Star key={i} size={16} fill="currentColor" />
            ))}
          </div>
          <span className="product-reviews-count text-[13px] text-gray-500">
            {product.reviews} lượt đánh giá
          </span>
          <button
            onClick={onWriteReview}
            className="product-reviews-write-btn bg-[#d70018] text-white px-8 py-2 rounded-md text-sm font-bold hover:bg-red-700 transition-colors mt-2 w-full"
          >
            Viết đánh giá
          </button>
        </div>

        {/* Rating Distribution */}
        <div className="product-reviews-distribution flex-1 flex flex-col justify-center gap-2 border-b lg:border-b-0 lg:border-r border-gray-200 pb-6 lg:pb-0 lg:pr-8">
          {ratingDistribution.map((item) => (
            <div key={item.star} className="product-reviews-distribution-item flex items-center gap-3 text-[13px]">
              <div className="product-reviews-distribution-star flex items-center gap-1 w-[30px]">
                <span className="font-bold">{item.star}</span>
                <Star size={10} fill="currentColor" className="text-gray-400" />
              </div>
              <div className="product-reviews-distribution-bar flex-1 h-1.5 bg-gray-200 rounded-full overflow-hidden">
                <div
                  className="product-reviews-distribution-fill h-full bg-[#d70018]"
                  style={{ width: `${item.percentage}%` }}
                ></div>
              </div>
              <span className="product-reviews-distribution-count text-gray-400 text-xs w-[60px] text-right">
                {item.count > 0 ? `${item.count} đánh giá` : '0 đánh giá'}
              </span>
            </div>
          ))}
        </div>

        {/* Feature Ratings */}
        <div className="product-reviews-features w-full lg:w-[300px] flex flex-col justify-center gap-2 shrink-0">
          <h4 className="product-reviews-features-title text-sm font-bold text-gray-800 mb-1">
            Đánh giá theo trải nghiệm
          </h4>
          {featureRatings.map((feat, idx) => (
            <div key={idx} className="product-reviews-feature flex justify-between items-center text-[13px]">
              <span className="product-reviews-feature-label text-gray-600">{feat.label}</span>
              <div className="product-reviews-feature-rating flex items-center gap-2">
                <div className="product-reviews-feature-stars flex gap-0.5 text-yellow-400">
                  {Array(5).fill(0).map((_, i) => (
                    <Star key={i} size={10} fill="currentColor" />
                  ))}
                </div>
                <span className="product-reviews-feature-score text-gray-500 text-xs">
                  5/5 ({feat.count} đánh giá)
                </span>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Review Filters */}
      <div className="product-reviews-filters flex items-center gap-3 mb-6 flex-wrap pb-4 border-b border-gray-100">
        <span className="product-reviews-filters-label text-sm font-bold text-gray-800 mr-2">
          Lọc đánh giá theo
        </span>
        {filters.map((filter, idx) => (
          <button
            key={idx}
            onClick={() => onFilterChange?.(filter.label)}
            className={`product-reviews-filter-btn px-3 py-1.5 rounded-full border text-[13px] font-medium transition-colors ${filter.active
              ? 'border-blue-500 text-blue-600 bg-blue-50'
              : 'border-gray-200 text-gray-600 hover:border-gray-400'
              }`}
          >
            {filter.label}
          </button>
        ))}
      </div>

      {/* Review List */}
      <div className="product-reviews-list flex flex-col gap-6">
        {reviews.map((review) => (
          <article key={review.id} className="product-review border-b border-gray-100 pb-6 last:border-0 last:pb-0">
            <div className="product-review-content flex gap-4">
              <div className={`product-review-avatar w-10 h-10 ${review.avatarColor} rounded-full flex items-center justify-center text-white text-base font-bold uppercase shrink-0`}>
                {review.name.charAt(0)}
              </div>

              <div className="product-review-body flex-1">
                <div className="product-review-header flex items-center gap-3 mb-1">
                  <p className="product-review-author text-sm font-bold text-gray-900 leading-tight">
                    {review.name}
                  </p>
                  <span className="product-review-time text-xs text-gray-400">{review.time}</span>
                </div>

                <div className="product-review-rating flex items-center gap-2 mb-1">
                  <div className="product-review-stars flex items-center gap-0.5 text-yellow-400 text-xs">
                    {Array(review.rating).fill(0).map((_, i) => (
                      <Star key={i} size={12} fill="currentColor" />
                    ))}
                  </div>
                  <span className="product-review-rating-text text-[#d70018] text-xs">
                    {review.ratingText}
                  </span>
                </div>

                {review.verified && (
                  <div className="product-review-verified flex items-center gap-1 text-[11px] text-green-600 font-medium mb-3">
                    <CheckCircle2 size={12} fill="#008000" className="text-white" />
                    <span>Đã mua tại CellphoneS</span>
                  </div>
                )}

                {review.tags.length > 0 && (
                  <div className="product-review-tags flex flex-wrap gap-2 mb-3">
                    {review.tags.map((tag, tIdx) => (
                      <span key={tIdx} className="product-review-tag bg-gray-100 text-gray-600 text-[11px] px-3 py-1.5 rounded border border-gray-200">
                        {tag}
                      </span>
                    ))}
                  </div>
                )}

                <p className="product-review-text text-[13px] text-gray-800 leading-relaxed mb-3">
                  {review.content}
                </p>

                <div className="product-review-actions flex gap-4 text-xs text-blue-600">
                  <button className="product-review-action flex items-center gap-1 hover:underline">
                    <MessageSquare size={14} /> Thảo luận
                  </button>
                  <button className="product-review-action flex items-center gap-1 hover:underline">
                    <ThumbsUp size={14} /> Hữu ích
                  </button>
                </div>
              </div>
            </div>
          </article>
        ))}
      </div>

      <div className="product-reviews-more mt-6 flex justify-center">
        <button
          onClick={onViewAll}
          className="product-reviews-more-btn text-[13px] text-gray-700 border border-gray-300 bg-gray-50 px-10 py-2 rounded-lg hover:bg-gray-100 transition-colors flex items-center gap-1 font-medium"
        >
          Xem tất cả đánh giá <ChevronRight size={14} />
        </button>
      </div>
    </article>
  );
}

