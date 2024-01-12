package com.elderpereira.springessential.infra.databases.postgres.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "user_name", unique = true)
    private String username;

    @Getter
    @Setter
    @Column(name = "full_name")
    private String fullName;

    @Setter
    @Column(name = "password")
    private String password;

    @Setter
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    @Setter
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Setter
    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Setter
    @Column(name = "enabled")
    private Boolean enabled;

    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission", joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_permission")}
    )
    private List<PermissionEntity> permissions;

    public List<String> getRoles() {
        return permissions.stream()
                .map(PermissionEntity::getRole)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
