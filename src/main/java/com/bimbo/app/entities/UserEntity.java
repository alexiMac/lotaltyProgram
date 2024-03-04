package com.bimbo.app.entities;

import com.bimbo.app.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_users", schema = "loyaltyProgram")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name="pk_id_user", nullable = false)
    private Integer id;

    @Column(name="v_first_name", nullable = false)
    private String firstName;

    @Column(name="v_last_name", nullable = false)
    private String lastName;

    @Column(name="v_email", nullable = false, unique = true)
    private String email;

    @Column(name = "v_password", nullable = false)
    private String password;

    @Column(name="v_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="b_approved", columnDefinition = "boolean default false" )
    @Builder.Default
    @ColumnDefault("false")
    private Boolean approved = false;

    @Column(name="d_created_date", columnDefinition="timestamp default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }
}
