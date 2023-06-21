package com.kim.dibt.repo;

import com.kim.dibt.models.Mention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mention, Long> {

}
