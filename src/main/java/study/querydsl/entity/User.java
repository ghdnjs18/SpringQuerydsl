package study.querydsl.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.querydsl.dto.SignupRequestDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "account")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "userid", nullable = false, unique = true)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "account_use")
    private boolean accountUse = true;

    @Column(name = "admin")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    public User(String userId, String password, String email, String nickname, UserRoleEnum role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    public User(SignupRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.password = requestDto.getPassword();
        this.role = requestDto.getRole();
        this.nickname = requestDto.getNickname();
        this.email = requestDto.getEmail();
    }

    public void addPostList(Post post) {
        this.postList.add(post);
    }

    public void addCommentList(Comment comment) {
        this.commentList.add(comment);
    }

    public void change(String password) {
        this.password = password;
    }
}