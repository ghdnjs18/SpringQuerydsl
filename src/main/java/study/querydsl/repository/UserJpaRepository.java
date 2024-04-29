package study.querydsl.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.dto.PostResponseDto;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserJpaRepository {

    private final EntityManager em;

    /**
     * 회원이 작성한 게시글 조회
     * 일반 조회 시
     * 게시글 1회, 유저 1회, 카테고리 N개
     */

    public List<PostResponseDto> findUserPost3(String userId) {
        return em.createQuery("select new study.querydsl.dto.PostResponseDto(p) " +
                        "from User u " +
                        "join u.postList p " +
                        "where u.userId = :userId", PostResponseDto.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
