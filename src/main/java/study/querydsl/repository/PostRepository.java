package study.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
}
