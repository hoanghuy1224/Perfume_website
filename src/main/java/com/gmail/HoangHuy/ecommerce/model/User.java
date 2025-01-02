package com.gmail.HoangHuy.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên người dùng không được để trống")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    private boolean active;

    private String activationCode;

    private String passwordResetCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_perfume", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "user_id"), // Cột tham chiếu đến User
            inverseJoinColumns = @JoinColumn(name = "perfume_id") // Cột tham chiếu đến Perfume
    )
    private List<Perfume> perfumeList;

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}
