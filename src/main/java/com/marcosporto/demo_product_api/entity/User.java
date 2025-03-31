package com.marcosporto.demo_product_api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;
    @Column(name = "password", nullable = false, length = 200)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENT;

    @CreatedDate
    @Column(name = "createDate")
    private LocalDateTime createDate;
    @LastModifiedDate
    @Column(name = "modificationDate")
    private LocalDateTime modificationDate;

    @CreatedBy
    @Column(name = "createBy")
    private String createBy;

    @LastModifiedBy
    @Column(name = "modifiedBy")
    private String modifiedBy;

    public enum Role {
        ROLE_ADMIN, // Acesso total
        ROLE_MANAGER, // Gerencia produtos
        ROLE_SELLER, // Vende produtos, mas não pode editar usuários
        ROLE_CLIENT, // Acesso para visualizar e comprar produtos
        ROLE_GUEST; // Acesso apenas para visualizar produtos
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
