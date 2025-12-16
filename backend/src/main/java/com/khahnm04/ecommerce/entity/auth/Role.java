package com.khahnm04.ecommerce.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    private String name;

    @Column(name = "description", columnDefinition = "TEXT",  nullable = false)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_name"),
        inverseJoinColumns = @JoinColumn(name = "permission_name")
    )
    @OrderBy("name ASC")
    Set<Permission> permissions;

}
