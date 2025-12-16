package com.khahnm04.ecommerce.service.product.impl;

import com.khahnm04.ecommerce.dto.request.product.ProductQuestionRequest;
import com.khahnm04.ecommerce.dto.response.product.ProductQuestionResponse;
import com.khahnm04.ecommerce.entity.product.Product;
import com.khahnm04.ecommerce.entity.product.ProductQuestion;
import com.khahnm04.ecommerce.entity.user.User;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.ProductQuestionMapper;
import com.khahnm04.ecommerce.repository.ProductQuestionRepository;
import com.khahnm04.ecommerce.repository.ProductRepository;
import com.khahnm04.ecommerce.service.product.ProductQuestionService;
import com.khahnm04.ecommerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductQuestionServiceImpl implements ProductQuestionService {

    private final ProductQuestionRepository questionRepository;
    private final ProductRepository productRepository;
    private final ProductQuestionMapper questionMapper;
    private final UserService userService;

    @Override
    @Transactional
    public ProductQuestionResponse createQuestion(ProductQuestionRequest request) {
        User currentUser = userService.getCurrentUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        ProductQuestion question = questionMapper.toEntity(request);
        question.setUser(currentUser);
        question.setProduct(product);

        if (request.getParentId() != null) {
            ProductQuestion parent = questionRepository.findById(request.getParentId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_QUESTION_NOT_FOUND));
            if (!parent.getProduct().getId().equals(request.getProductId())) {
                throw new AppException(ErrorCode.INVALID_REPLY_TARGET);
            }
            question.setParent(parent);
        }

        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("ADMIN") || r.getName().equalsIgnoreCase("STAFF"));
        question.setIsAdminReply(isAdmin);

        question = questionRepository.save(question);
        log.info("Question created with id: {}", question.getId());
        return questionMapper.toResponse(question);
    }

}
