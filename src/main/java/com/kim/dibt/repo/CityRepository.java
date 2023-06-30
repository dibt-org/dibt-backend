package com.kim.dibt.repo;

import com.kim.dibt.models.City;
import com.kim.dibt.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByName(String name);
}
