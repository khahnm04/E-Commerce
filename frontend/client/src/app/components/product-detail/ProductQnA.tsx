import React, { useState } from 'react';
import { ChevronDown } from 'lucide-react';
import type { Product, QnAQuestion } from './types';

interface ProductQnAProps {
  product: Product;
  questions: QnAQuestion[];
  onSubmitQuestion?: (question: string) => void;
  onViewMore?: () => void;
}

export function ProductQnA({
  product,
  questions,
  onSubmitQuestion,
  onViewMore
}: ProductQnAProps) {
  const [questionInput, setQuestionInput] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (questionInput.trim() && onSubmitQuestion) {
      onSubmitQuestion(questionInput);
      setQuestionInput('');
    }
  };

  return (
    <article className="product-qna bg-white border border-gray-200 rounded-lg p-5 mb-6 shadow-sm mt-[20px]">
      <h2 className="product-qna-title text-lg font-bold text-gray-900 mb-6 pb-3 border-b border-gray-100">
        Hỏi & Đáp về {product.name}
      </h2>

      {/* Q&A Form */}
      <form className="product-qna-form mb-8 p-4 bg-gray-50 border border-gray-200 rounded-lg" onSubmit={handleSubmit}>
        <div className="product-qna-form-wrapper flex gap-3">
          <input
            type="text"
            value={questionInput}
            onChange={(e) => setQuestionInput(e.target.value)}
            placeholder="Xin mời để lại câu hỏi, chúng tôi sẽ trả lời trong 1h từ 8h - 21h mỗi ngày."
            className="product-qna-form-input flex-1 border border-gray-300 rounded-lg px-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-[#d70018] focus:border-transparent transition-all"
          />
          <button
            type="submit"
            className="product-qna-form-submit bg-[#d70018] text-white px-6 py-3 rounded-lg text-sm font-bold hover:bg-red-700 transition-colors whitespace-nowrap shadow-sm hover:shadow-md"
          >
            Gửi câu hỏi
          </button>
        </div>
      </form>

      {/* Q&A List */}
      <div className="product-qna-list">
        {questions.map((qna, idx) => (
          <article key={idx} className={`product-qna-item ${idx < questions.length - 1 ? 'border-b border-gray-100 pb-6 mb-6' : 'pb-4 mb-4'}`}>
            {/* Question */}
            <div className="product-qna-question flex items-start gap-3 mb-4">
              <div className="product-qna-avatar w-8 h-8 bg-gray-100 text-gray-700 font-bold rounded-lg flex items-center justify-center text-sm uppercase shrink-0 shadow-sm">
                {qna.userInitial}
              </div>
              <div className="product-qna-question-content flex-1">
                <div className="product-qna-question-header flex items-baseline gap-2 mb-2">
                  <span className="product-qna-question-author text-[15px] font-bold text-gray-900">
                    {qna.user}
                  </span>
                  <span className="product-qna-question-time text-xs text-gray-500">• {qna.time}</span>
                </div>
                <p className="product-qna-question-text text-sm text-gray-800 mb-3 leading-relaxed">
                  {qna.question}
                </p>
                {qna.answer && (
                  <div className="product-qna-question-actions flex gap-4 text-xs text-blue-600 font-medium">
                    <button className="hover:text-blue-700 hover:underline">Phản hồi</button>
                    <button className="hover:text-blue-700 hover:underline">Thu gọn phản hồi</button>
                  </div>
                )}
              </div>
            </div>

            {/* Answer */}
            {qna.answer && (
              <div className="product-qna-answer ml-11 pl-4 relative">
                <div className="product-qna-answer-line absolute left-0 top-0 bottom-0 w-0.5 bg-gray-200"></div>
                <div className="product-qna-answer-content flex items-start gap-3">
                  <div className="product-qna-answer-avatar w-7 h-7 bg-[#d70018] text-white rounded-lg flex items-center justify-center text-xs font-bold shrink-0 shadow-sm">
                    {qna.adminInitial}
                  </div>
                  <div className="product-qna-answer-body flex-1">
                    <div className="product-qna-answer-header flex items-baseline gap-2 mb-3">
                      <span className="product-qna-answer-author text-sm font-bold text-[#d70018]">
                        {qna.admin}
                      </span>
                      <span className="product-qna-answer-time text-xs text-gray-500">• {qna.time}</span>
                    </div>
                    <div className="product-qna-answer-text bg-gray-50 border border-gray-100 rounded-lg p-4 shadow-sm">
                      <div className="product-qna-answer-content space-y-3">
                        {qna.answer.split('. ').map((sentence, sIdx) => (
                          <p key={sIdx} className="text-sm text-gray-800 leading-relaxed">
                            {sentence}
                          </p>
                        ))}
                      </div>
                      <div className="product-qna-answer-actions mt-4 pt-3 border-t border-gray-200">
                        <button className="product-qna-answer-action text-xs text-blue-600 font-medium cursor-pointer hover:text-blue-700 hover:underline">
                          Phản hồi
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            )}
          </article>
        ))}
      </div>

      {/* Load More Q&A */}
      <div className="product-qna-more pt-4 border-t border-gray-100">
        <button
          onClick={onViewMore}
          className="product-qna-more-btn w-full border border-gray-300 rounded-lg py-3 text-sm text-gray-700 font-medium hover:border-[#d70018] hover:text-[#d70018] hover:bg-red-50 transition-all duration-300 flex items-center justify-center gap-2 group shadow-sm hover:shadow"
        >
          <span>Xem thêm 15 câu hỏi khác</span>
          <ChevronDown size={16} className="group-hover:translate-y-1 transition-transform" />
        </button>
      </div>
    </article>
  );
}

