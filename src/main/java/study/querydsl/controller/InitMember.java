package study.querydsl.controller;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.*;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct // 트랜잭션과 함께 사용할 수 없다.
    public void init() {
        initMemberService.init2();
    }

    @Component
    static class InitMemberService {
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            Team teamA = new Team("teamA");
            Team teamB = new Team("teamB");
            em.persist(teamA);
            em.persist(teamB);

            for (int i = 0; i < 100; i++) {
                Team selectedTeam = i % 2 == 0 ? teamA : teamB;
                em.persist(new Member("member" + i, i, selectedTeam));
            }
        }

        @Transactional
        public void init2() {
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
    }
}
