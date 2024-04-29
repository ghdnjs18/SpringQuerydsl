package study.querydsl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.querydsl.dto.CommentRequestDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "comment", nullable = false, length = 500)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private User user;

    @Column(name = "commentlike", nullable = false)
    private int commentLike = 0;

    @Column(name = "comment_use", nullable = false)
    private boolean commentUse = true;

    @Column(name = "commentlikeues", nullable = false)
    private boolean commentLikeUse = false;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> ChildcommentList = new ArrayList<>();

    public Comment(String comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public Comment(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }

    public void addCommentLikeList(CommentLike commentLike) {
        this.commentLikeList.add(commentLike);
        commentLike.setComment(this);
    }

    public void addCommentList(Comment comment) {
        this.ChildcommentList.add(comment);
        comment.setParentComment(this);
    }
}
