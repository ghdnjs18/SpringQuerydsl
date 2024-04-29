package study.querydsl.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.PostResponseDto;
import study.querydsl.repository.UserJpaRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    void findUserPost_jpa() {
        List<PostResponseDto> userPost = userJpaRepository.findUserPost3("user1");

        for (PostResponseDto postResponseDto : userPost) {
            System.out.println("postResponseDto = " + postResponseDto);
        }
    }

    @Test
    void findUserPost_datajpa() {
        List<PostResponseDto> userPost = userJpaRepository.findUserPost3("user1");

        for (PostResponseDto postResponseDto : userPost) {
            System.out.println("postResponseDto = " + postResponseDto);
        }
    }

    @Test
    void findUserPost_querydsl() {
        List<PostResponseDto> userPost = userJpaRepository.findUserPost3("user1");

        for (PostResponseDto postResponseDto : userPost) {
            System.out.println("postResponseDto = " + postResponseDto);
        }
    }
}