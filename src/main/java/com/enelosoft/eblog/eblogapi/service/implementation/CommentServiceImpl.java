package com.enelosoft.eblog.eblogapi.service.implementation;

import com.enelosoft.eblog.eblogapi.dto.CommentDto;
import com.enelosoft.eblog.eblogapi.exception.EBlogAPIException;
import com.enelosoft.eblog.eblogapi.exception.ResourceNotFoundException;
import com.enelosoft.eblog.eblogapi.model.Comment;
import com.enelosoft.eblog.eblogapi.model.Post;
import com.enelosoft.eblog.eblogapi.repository.CommentRepository;
import com.enelosoft.eblog.eblogapi.repository.PostRepository;
import com.enelosoft.eblog.eblogapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToModel(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "Id", postId));

        comment.setPost(post);

        commentRepository.save(comment);

        return mapToDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new EBlogAPIException(HttpStatus.BAD_REQUEST, "Comment not found in this post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new EBlogAPIException(HttpStatus.BAD_REQUEST, "Comment not found in this post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setMessage(commentDto.getMessage());

        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public String deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new EBlogAPIException(HttpStatus.BAD_REQUEST, "Comment not found in this post");
        }

        commentRepository.delete(comment);

        return "Successfully deleted comment: %s".formatted(commentId);
    }

    private Comment mapToModel(CommentDto commentDto){
        return mapper.map(commentDto, Comment.class);
    }

    private CommentDto mapToDto(Comment comment){
        return mapper.map(comment, CommentDto.class);
    }
}
