package com.kim.dibt.models;

import com.kim.dibt.core.models.Auditable;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cities")
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class City extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String plateCode;
    public City(String name, Integer plateCode) {
        this.name = name;
        this.plateCode = plateCode.toString();
    }

    @OneToMany(mappedBy = "city")
    private List<CorprateUser> corporateUsers;

}
