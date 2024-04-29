package study.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.querydsl.dto.CommentResponseDto;
import study.querydsl.dto.PostDetailResponseDto;
import study.querydsl.dto.PostResponseDto;
import study.querydsl.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 회원이 작성한 게시글 조회
     */

    @Query("select new study.querydsl.dto.PostResponseDto(" +
            "p.id, p.title, u.userId, p.contents, p.postLike, p.createdTime, p.modifiedTime, c.name) " +
            "from Post p left join p.user u left join p.category c where p.user.userId = :userId")
    List<PostResponseDto> findUserPost(@Param("userId") String userId);

    @Query("select new study.querydsl.dto.PostResponseDto(p) from Post p join fetch p.user u join fetch p.category c where p.user.userId = :userId")
    List<PostResponseDto> findUserPost2(@Param("userId") String userId);

    @Query("select new study.querydsl.dto.PostResponseDto(p) from Post p where p.user.userId = :userId")
    List<PostResponseDto> findUserPost3(@Param("userId") String userId);

    /**
     * 게시글 상세 조회
     * 1. 게시글을 조회하고 게시글에 있는 댓글들을 조회해서 dto에 넣어준다.
     *    -
     */

    @Query("select new study.querydsl.dto.PostDetailResponseDto(" +
            "p.id, p.title, u.userId, p.contents, p.postLike, p.createdTime, p.modifiedTime, c.name) " +
            "from Post p left join p.user u left join p.category c where p.id = :postId")
    List<PostDetailResponseDto> findPost(@Param("postId") Long postId);

    @Query("select new study.querydsl.dto.CommentResponseDto(" +
            "c.id, c.comment, u.userId, c.commentLike, c.createdTime, c.modifiedTime) " +
            "from Post p join p.commentList c left join c.user u where p.id = :postId")
    List<CommentResponseDto> findComment(@Param("postId") Long postId);

    @Query("select new study.querydsl.dto.PostDetailResponseDto(p) from Post p join fetch p.user u join fetch p.category c where p.id = :postId")
    List<PostDetailResponseDto> findPost2(@Param("postId") Long postId);

    @Query("select new study.querydsl.dto.CommentResponseDto(c) from Post p join p.commentList c join fetch c.user u where p.id = :postId")
    List<CommentResponseDto> findComment2(@Param("postId") Long postId);

    @Query("select new study.querydsl.dto.PostDetailResponseDto(p) from Post p where p.id = :postId")
    List<PostDetailResponseDto> findPost3(@Param("postId") Long postId);

    @Query("select new study.querydsl.dto.CommentResponseDto(c) from Post p join p.commentList c where p.id = :postId")
    List<CommentResponseDto> findComment3(@Param("postId") Long postId);
}
