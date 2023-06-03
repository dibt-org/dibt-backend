package com.kim.dibt.repo;

import com.kim.dibt.models.Comment;
import com.kim.dibt.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long post_id);
}
