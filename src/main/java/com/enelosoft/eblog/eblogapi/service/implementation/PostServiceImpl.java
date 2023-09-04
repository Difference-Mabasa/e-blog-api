package com.enelosoft.eblog.eblogapi.service.implementation;

import com.enelosoft.eblog.eblogapi.dto.PostDto;
import com.enelosoft.eblog.eblogapi.dto.PostResponse;
import com.enelosoft.eblog.eblogapi.exception.ResourceNotFoundException;
import com.enelosoft.eblog.eblogapi.model.Category;
import com.enelosoft.eblog.eblogapi.model.Post;
import com.enelosoft.eblog.eblogapi.repository.CategoryRepository;
import com.enelosoft.eblog.eblogapi.repository.PostRepository;
import com.enelosoft.eblog.eblogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.mapper =mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", postDto.getCategoryId())));
        Post post = mapToModel(postDto);
        post.setCategory(category.get());

        Post newPost = postRepository.save(post);
        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostDto> postDtos = listOfPosts.stream().map(this::mapToDto).toList();

        return new PostResponse(
                postDtos,
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast(),
                sortBy
        );

    }

    @Override
    public PostDto getPostById(Long id) {
        Optional<Post> post = Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id)));
        return mapToDto(post.get());
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Optional<Post> post = Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id)));

        Optional<Category> category = Optional.ofNullable(categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", postDto.getCategoryId())));

        post.ifPresent(value -> {
            value.setTitle(postDto.getTitle());
            value.setContent(postDto.getContent());
            value.setDescription(postDto.getDescription());
            value.setCategory(category.get());
        });

        Post updatedPost = postRepository.save(post.get());

        return mapToDto(updatedPost);
    }

    @Override
    public String deletePost(Long id) {
        Optional<Post> post = Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id))
        );

        post.ifPresent(postRepository::delete);

        return "Successfully deleted post: %s".formatted(id);
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {

        categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post){
        return mapper.map(post, PostDto.class);
    }

    private Post mapToModel(PostDto postDto){
        return mapper.map(postDto, Post.class);
    }
}
