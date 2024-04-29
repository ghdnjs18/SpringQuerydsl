package study.querydsl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.querydsl.dto.PostRequestDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private User user;

    @Column(name = "postlike", nullable = false)
    private int postLike = 0;

    @Column(name = "post_use", nullable = false)
    private boolean postUse = true;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany
//    @JoinColumn(name = "post_id")
    @OrderBy("createdTime desc")
    private List<Comment> commentList = new ArrayList<>();

    public Post(String title, String contents, User user, Category category) {
        this.title = title;
        this.contents = contents;
        this.user = user;
        this.category = category;
    }

    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void addPostLikeList(PostLike postLike) {
        this.postLikeList.add(postLike);
        postLike.setPost(this);
    }

    public void addCommentList(Comment comment) {
        this.commentList.add(comment);
    }

}
