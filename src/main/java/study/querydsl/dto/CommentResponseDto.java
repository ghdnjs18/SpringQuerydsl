package study.querydsl.dto;

import lombok.Data;
import lombok.Getter;
import study.querydsl.entity.Comment;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String userId;
    private int commentLike;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.userId = comment.getUser().getUserId();
        this.commentLike = comment.getCommentLike();
        this.createdTime = comment.getCreatedTime();
        this.modifiedTime = comment.getModifiedTime();
    }

    public CommentResponseDto(Long id, String comment, String userId, int commentLike, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.commentLike = commentLike;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }
}
