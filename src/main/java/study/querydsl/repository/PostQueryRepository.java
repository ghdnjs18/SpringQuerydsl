package study.querydsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.dto.CommentResponseDto;
import study.querydsl.dto.PostDetailResponseDto;
import study.querydsl.dto.PostResponseDto;
import study.querydsl.entity.QCategory;
import study.querydsl.entity.QComment;
import study.querydsl.entity.QPost;
import study.querydsl.entity.QUser;

import java.util.List;

import static study.querydsl.entity.QCategory.*;
import static study.querydsl.entity.QComment.*;
import static study.querydsl.entity.QPost.*;
import static study.querydsl.entity.QUser.*;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 회원이 작성한 게시글 조회
     */

    public List<PostResponseDto> findUserPost(String userId) {
        return queryFactory
                .select(Projections.constructor(PostResponseDto.class,
                        post.id,
                        post.title,
                        user.userId,
                        post.contents,
                        post.postLike,
                        post.createdTime,
                        post.modifiedTime,
                        category.name))
                .from(post)
                .leftJoin(post.user, user)
                .leftJoin(post.category, category)
                .where(post.user.userId.eq(userId))
                .fetch();
    }

    public List<PostResponseDto> findUserPost2(String userId) {
        return queryFactory
                .select(Projections.constructor(PostResponseDto.class, post))
                .from(post)
                .join(post.user, user).fetchJoin()
                .join(post.category, category).fetchJoin()
                .where(post.user.userId.eq(userId))
                .fetch();
    }

    public List<PostResponseDto> findUserPost3(String userId) {
        return queryFactory
                .select(Projections.constructor(PostResponseDto.class, post))
                .from(post)
                .where(post.user.userId.eq(userId))
                .fetch();
    }

    /**
     * 게시글 상세 조회
     * 1. 게시글을 조회하고 게시글에 있는 댓글들을 조회해서 dto에 넣어준다.
     *    -
     */

    public List<PostDetailResponseDto> findPostComment(Long postId) {
        List<PostDetailResponseDto> result = findPost(postId);

        result.forEach(o -> {
            List<CommentResponseDto> comments = findComment(postId);
            o.setCommentList(comments);
        });

        return result;
    }

    public List<PostDetailResponseDto> findPost(Long postId) {
        return queryFactory
                .select(Projections.constructor(PostDetailResponseDto.class,
                        post.id,
                        post.title,
                        user.userId,
                        post.contents,
                        post.postLike,
                        post.createdTime,
                        post.modifiedTime,
                        category.name))
                .from(post)
                .leftJoin(post.user, user)
                .leftJoin(post.category, category)
                .where(post.id.eq(postId))
                .fetch();
    }

    public List<CommentResponseDto> findComment(Long postId) {
        return queryFactory
                .select(Projections.constructor(CommentResponseDto.class,
                        comment1.id,
                        comment1.comment,
                        user.userId,
                        comment1.commentLike,
                        comment1.createdTime,
                        comment1.modifiedTime))
                .from(post)
                .join(post.commentList, comment1)
                .leftJoin(comment1.user, user)
                .where(post.id.eq(postId))
                .fetch();
    }

    public List<PostDetailResponseDto> findPost2(Long postId) {
        return queryFactory
                .select(Projections.constructor(PostDetailResponseDto.class, post))
                .from(post)
                .join(post.user, user).fetchJoin()
                .join(post.category, category).fetchJoin()
                .where(post.id.eq(postId))
                .fetch();
    }

    public List<CommentResponseDto> findComment2(Long postId) {
        return queryFactory
                .select(Projections.constructor(CommentResponseDto.class, comment1))
                .from(post)
                .join(post.commentList, comment1)
                .join(comment1.user).fetchJoin()
                .where(post.id.eq(postId))
                .fetch();
    }

    public List<PostDetailResponseDto> findPost3(Long postId) {
        return queryFactory
                .select(Projections.constructor(PostDetailResponseDto.class, post))
                .from(post)
                .where(post.id.eq(postId))
                .fetch();
    }

    public List<CommentResponseDto> findComment3(Long postId) {
        return queryFactory
                .select(Projections.constructor(CommentResponseDto.class, comment1))
                .from(post)
                .join(post.commentList, comment1)
                .where(post.id.eq(postId))
                .fetch();
    }
}
