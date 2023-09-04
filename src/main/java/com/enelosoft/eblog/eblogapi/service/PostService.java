package com.enelosoft.eblog.eblogapi.service;

import com.enelosoft.eblog.eblogapi.dto.PostDto;
import com.enelosoft.eblog.eblogapi.dto.PostResponse;
import com.enelosoft.eblog.eblogapi.model.Post;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortOrder);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    String deletePost(Long id);
    List<PostDto> getPostsByCategoryId(Long categoryId);

}
