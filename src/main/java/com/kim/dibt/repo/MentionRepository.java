package com.kim.dibt.repo;

import com.kim.dibt.models.Mention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MentionRepository extends JpaRepository<Mention, Long> {

    long countAllByUserId(Long userId);

}
