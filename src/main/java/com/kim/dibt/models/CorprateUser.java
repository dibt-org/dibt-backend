package com.kim.dibt.models;

import com.kim.dibt.security.models.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "corporate_users")
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class CorprateUser extends User {
    private String name;
    private String website;
    private String phone;
    private String address;
    private String logo;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id")
    private City city;

}
