package com.khahnm04.ecommerce.entity.user;

import com.khahnm04.ecommerce.common.enums.AddressType;
import com.khahnm04.ecommerce.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address extends BaseEntity<Long> {

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "receiver_phone", nullable = false, length = 15)
    private String receiverPhone;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "ward", nullable = false)
    private String ward;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "address_name")
    private String addressName;

    @Builder.Default
    @ColumnDefault("'HOME'")
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressType addressType = AddressType.HOME;

    @Builder.Default
    @ColumnDefault("0")
    @Column(name = "is_default")
    private Boolean isDefault = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
