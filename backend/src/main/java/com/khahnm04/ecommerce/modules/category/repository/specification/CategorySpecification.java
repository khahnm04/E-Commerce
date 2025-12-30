package com.khahnm04.ecommerce.modules.category.repository.specification;

import com.khahnm04.ecommerce.modules.category.entity.Category;
import com.khahnm04.ecommerce.shared.enums.CategoryStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CategorySpecification {

    private static final String FIELD_DELETED_AT = "deletedAt";
    private static final String FIELD_PARENT = "parent";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_ID = "id";

    public static Specification<Category> notDeleted() {
        return (root, query, cb) -> cb.isNull(root.get(FIELD_DELETED_AT));
    }

    public static Specification<Category> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get(FIELD_DELETED_AT));
    }

    public static Specification<Category> hasParent(Long parentId) {
        return (root, query, cb) -> {
            if (parentId == null) return null;
            return cb.equal(root.get(FIELD_PARENT).get(FIELD_ID), parentId);
        };
    }

    public static Specification<Category> isRoot() {
        return (root, query, cb) -> cb.isNull(root.get(FIELD_PARENT));
    }

    public static Specification<Category> hasStatus(CategoryStatus status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.get(FIELD_STATUS), status);
        };
    }

    public static Specification<Category> searchByName(String search) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(search)) return null;
            String searchPattern = "%" + search.toLowerCase() + "%";
            return cb.like(cb.lower(root.get(FIELD_NAME)), searchPattern);
        };
    }

    public static Specification<Category> hasTimestampBetween(String dateType, LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> {
            LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
            LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;
            if (startDateTime == null && endDateTime == null) {
                return null;
            }
            if (startDateTime != null && endDateTime != null) {
                return cb.between(root.get(dateType), startDateTime, endDateTime);
            } else if (startDateTime != null) {
                return cb.greaterThanOrEqualTo(root.get(dateType), startDateTime);
            } else {
                return cb.lessThanOrEqualTo(root.get(dateType), endDateTime);
            }
        };
    }

}
