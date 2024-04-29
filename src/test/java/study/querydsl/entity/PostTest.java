package study.querydsl.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.PostResponseDto;
import study.querydsl.repository.PostJpaRepository;
import study.querydsl.repository.PostQueryRepository;
import study.querydsl.repository.PostRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostTest {

    @Autowired
    EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostJpaRepository postJpaRepository;

    @Autowired
    PostQueryRepository postQueryRepository;

    void befor() {
        // 유저
        User user1 = new User("user1", "1", "11111@naver.com", "nick11", UserRoleEnum.USER);
        User user2 = new User("user2", "2", "22222@naver.com", "nick22", UserRoleEnum.USER);
        User user3 = new User("user3", "3", "33333@naver.com", "nick33", UserRoleEnum.USER);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);

        // 카테고리
        Category categoryA = new Category("AAA");
        Category categoryB = new Category("BBB");
        em.persist(categoryA);
        em.persist(categoryB);

        // 게시글
        for (int i = 0; i < 15; i++) {
            User selectUser;
            if (i % 3 == 0) {
                selectUser = user1;
            } else if (i % 3 == 1) {
                selectUser = user2;
            } else {
                selectUser = user3;
            }
            Category selectedcate = i % 2 == 0 ? categoryA : categoryB;

            Post selectPost = new Post("tile" + i, "content : " + String.valueOf(i).repeat(5), selectUser, selectedcate);
            for (int j = 0; j < 9; j++) {
                User selectUser2;
                if (j % 3 == 0) {
                    selectUser2 = user1;
                } else if (j % 3 == 1) {
                    selectUser2 = user2;
                } else {
                    selectUser2 = user3;
                }
                Comment comment = new Comment(String.valueOf(i*j).repeat(10), selectUser2);
                selectPost.addCommentList(comment);
                em.persist(comment);
            }
            em.persist(selectPost);
        }

    }

    @Test
    void findUserPost_jpa() {
        List<PostResponseDto> userPost = postJpaRepository.findUserPost2("user1");

        for (PostResponseDto postResponseDto : userPost) {
            System.out.println("postResponseDto = " + postResponseDto);
        }
    }

    @Test
    void findUserPost_datajpa() {
        List<PostResponseDto> userPost = postRepository.findUserPost2("user1");

        for (PostResponseDto postResponseDto : userPost) {
            System.out.println("postResponseDto = " + postResponseDto);
        }
    }

    @Test
    void findUserPost_querydsl() {
        List<PostResponseDto> userPost = postQueryRepository.findUserPost2("user1");

        for (PostResponseDto postResponseDto : userPost) {
            System.out.println("postResponseDto = " + postResponseDto);
        }
    }
}