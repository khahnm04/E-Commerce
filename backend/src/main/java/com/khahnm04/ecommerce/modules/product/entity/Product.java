package com.khahnm04.ecommerce.modules.product.entity;

import com.khahnm04.ecommerce.shared.enums.ProductStatus;
import com.khahnm04.ecommerce.modules.brand.entity.Brand;
import com.khahnm04.ecommerce.shared.entity.BaseEntity;
import com.khahnm04.ecommerce.modules.category.entity.ProductCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "old_price")
    private Long oldPrice;

    @Column(name = "thumbnail", nullable = false, columnDefinition = "TEXT")
    private String thumbnail;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status = ProductStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> productCategories;

    public void addCategory(ProductCategory productCategory) {
        productCategories.add(productCategory);
        productCategory.setProduct(this);
    }

    @OrderBy("displayOrder ASC")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAttributeValue> productAttributeValues;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVariant> productVariants;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductFaq> productFaqs;

}

//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
//    private List<NewsDetail> newsDetails;
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
//    private List<ProductFaq> productFaqs;
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
//    private List<ProductQuestion> productQuestions;
