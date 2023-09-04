package com.enelosoft.eblog.eblogapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "PostDto model"
)
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should have at least two characters")
    @Schema(
            description = "Blog post title"
    )
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should have at least ten characters")
    private String description;

    @NotEmpty
    @Schema(
            description = "Blog post content"
    )
    private String content;

    private Set<CommentDto> comments;

    private Long categoryId;
}
