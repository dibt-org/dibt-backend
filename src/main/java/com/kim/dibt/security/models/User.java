package com.kim.dibt.security.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kim.dibt.core.models.Auditable;
import com.kim.dibt.models.Mention;
import com.kim.dibt.models.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.kim.dibt.models.Comment;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "UPDATE users SET deleted = true,deleted_date = CURRENT_TIMESTAMP WHERE id = ?")
@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedUserFilter", condition = "deleted = :isDeleted")
@Where(clause = "deleted = false")
public class User extends Auditable implements UserDetails {

    @Id
    @GeneratedValue
    private long id;
    private String email;
    private String username;
    private String password;
    private String about;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonSerialize
    private List<Token> tokens;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Mention> mentions;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
