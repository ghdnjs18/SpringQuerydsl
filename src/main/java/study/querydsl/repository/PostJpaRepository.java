package study.querydsl.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.dto.PostDetailResponseDto;
import study.querydsl.dto.PostResponseDto;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostJpaRepository {

    private final EntityManager em;

    /**
     * 회원이 작성한 게시글 조회
     * 일반 조회 시
     * 게시글 1회, 유저 1회, 카테고리 N개
     * 페치 조인 조회 시
     * 전체 1회 - 대신 필요없는 유저정보와 카테고리정보 검색해 데이터 크기가 커짐 - 페치 조인
     * 전체 1회 - DTO에 엔티티를 넘기지 않고 필드값 하나씩 넘길경우 데이터를 정제해서 조회가 가능하다. - 조회떄 이미 같이 넣음
     */

    public List<PostResponseDto> findUserPost(String userId) {
        return em.createQuery("select new study.querydsl.dto.PostResponseDto(" +
                        "p.id," +
                        "p.title," +
                        "u.userId," +
                        "p.contents," +
                        "p.postLike," +
                        "p.createdTime," +
                        "p.modifiedTime," +
                        "c.name) " +
                "from Post p " +
                "left join p.user u " +
                "left join p.category c " +
                "where p.user.userId = :userId", PostResponseDto.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<PostResponseDto> findUserPost2(String userId) {
        return em.createQuery("select new study.querydsl.dto.PostResponseDto(p) " +
                        "from Post p " +
                        "join fetch p.user u " +
                        "join fetch p.category c " +
                        "where p.user.userId = :userId", PostResponseDto.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<PostResponseDto> findUserPost3(String userId) {
        return em.createQuery("select new study.querydsl.dto.PostResponseDto(p) " +
                        "from Post p " +
                        "where p.user.userId = :userId", PostResponseDto.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
