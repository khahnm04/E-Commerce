package com.khahnm04.ecommerce.modules.product.service.impl;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductQuestionRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductQuestionResponse;
import com.khahnm04.ecommerce.modules.product.entity.Product;
import com.khahnm04.ecommerce.modules.product.entity.ProductQuestion;
import com.khahnm04.ecommerce.modules.user.entity.User;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.product.mapper.ProductQuestionMapper;
import com.khahnm04.ecommerce.modules.product.repository.ProductQuestionRepository;
import com.khahnm04.ecommerce.modules.product.repository.ProductRepository;
import com.khahnm04.ecommerce.modules.product.service.ProductQuestionService;
import com.khahnm04.ecommerce.modules.user.service.UserService;
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
