package study.querydsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.dto.PostResponseDto;
import study.querydsl.entity.QCategory;
import study.querydsl.entity.QPost;
import study.querydsl.entity.QUser;

import java.util.List;

import static study.querydsl.entity.QCategory.*;
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
}
