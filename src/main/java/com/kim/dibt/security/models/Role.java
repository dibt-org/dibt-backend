package com.kim.dibt.security.models;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class Role {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleName;

    public Role(RoleType roleName) {
        this.roleName = roleName;
    }


}
