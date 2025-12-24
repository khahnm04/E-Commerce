package com.khahnm04.ecommerce.modules.category.repository;

import com.khahnm04.ecommerce.shared.enums.CategoryStatus;
import com.khahnm04.ecommerce.modules.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT COUNT(c.parent.id) > 0 FROM Category c WHERE c.parent.id = :parentId")
    boolean existsByParent(@Param("parentId") Long id);

    boolean existsBySlug(String slug);

    boolean existsByName(String slug);

    Page<Category> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Category> findAllByDeletedAtIsNotNull(Pageable pageable);

    Optional<Category> findBySlug(String slug);

    @Modifying
    @Query(value = """
        UPDATE Category c
        SET c.status = :newStatus
        WHERE c.path LIKE :parentPathWithWildcard
        AND c.id != :parentId
        AND c.status = :oldStatus
        AND c.deletedAt IS NULL
    """)
    void updateDescendantStatusesByPath(
            @Param("parentPathWithWildcard") String parentPathWithWildcard,
            @Param("parentId") Long parentId,
            @Param("newStatus") CategoryStatus newStatus,
            @Param("oldStatus") CategoryStatus oldStatus
    );

    @Modifying
    @Query(value = """
        UPDATE Category c
        SET c.path = CONCAT(:newPathPrefix, SUBSTRING(c.path, :oldPathPrefixLength + 1))
        WHERE c.path LIKE :oldPathPrefixWithWildcard
        AND c.deletedAt IS NULL
    """)
    void updateDescendantPaths(
            @Param("oldPathPrefixWithWildcard") String oldPathPrefixWithWildcard,
            @Param("newPathPrefix") String newPathPrefix,
            @Param("oldPathPrefixLength") int oldPathPrefixLength
    );

    @Query("SELECT (count(c.id) > 0) FROM Category c WHERE UPPER(c.name) = UPPER(:name) AND c.id <> :id")
    boolean existsByNameIgnoreCaseAndIdNot(@Param("name") String name, @Param("id") Long id);

    @Query("SELECT (count(c.id) > 0) FROM Category c WHERE UPPER(c.slug) = UPPER(:slug) AND c.id <> :id")
    boolean existsBySlugIgnoreCaseAndIdNot(@Param("slug") String slug, @Param("id") Long id);

}
