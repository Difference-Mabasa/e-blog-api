package com.enelosoft.eblog.eblogapi.service.implementation;

import com.enelosoft.eblog.eblogapi.dto.PostDto;
import com.enelosoft.eblog.eblogapi.dto.PostResponse;
import com.enelosoft.eblog.eblogapi.exception.ResourceNotFoundException;
import com.enelosoft.eblog.eblogapi.model.Post;
import com.enelosoft.eblog.eblogapi.repository.PostRepository;
import com.enelosoft.eblog.eblogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper =mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToModel(postDto);
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

        post.ifPresent(value -> {
            value.setTitle(postDto.getTitle());
            value.setContent(postDto.getContent());
            value.setDescription(postDto.getDescription());
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

    private PostDto mapToDto(Post post){
        return mapper.map(post, PostDto.class);
    }

    private Post mapToModel(PostDto postDto){
        return mapper.map(postDto, Post.class);
    }
}
