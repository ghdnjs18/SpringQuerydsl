package study.querydsl.dto;

import lombok.Data;
import lombok.Getter;
import study.querydsl.entity.Post;

import java.time.LocalDateTime;

@Data
public class PostResponseDto {

    private Long id;
    private String title;
    private String userId;
    private String contents;
    private int postLike;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private String category;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.userId = post.getUser().getUserId();
        this.contents = post.getContents();
        this.postLike = post.getPostLike();
        this.createdTime = post.getCreatedTime();
        this.modifiedTime = post.getModifiedTime();
        this.category = post.getCategory().getName();
    }

    public PostResponseDto(Long id, String title, String userId, String contents, int postLike, LocalDateTime createdTime, LocalDateTime modifiedTime, String category) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.contents = contents;
        this.postLike = postLike;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.category = category;
    }
}
