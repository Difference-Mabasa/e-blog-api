package com.enelosoft.eblog.eblogapi.repository;

import com.enelosoft.eblog.eblogapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId);
}
