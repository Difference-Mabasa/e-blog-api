package com.enelosoft.eblog.eblogapi.repository;

import com.enelosoft.eblog.eblogapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
