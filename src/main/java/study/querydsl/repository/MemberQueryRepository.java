package study.querydsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<MemberTeamDto> search(MemberSearchCondition condition) {

        return queryFactory
                .select(Projections.constructor(MemberTeamDto.class,
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                .fetch();
    }

    private BooleanExpression ageGoe(Integer ageGoeCond) {
        return ageGoeCond == null ? null : member.age.goe(ageGoeCond);
    }

    private BooleanExpression ageLoe(Integer ageLoeCond) {
        return ageLoeCond == null ? null : member.age.loe(ageLoeCond);
    }

    private BooleanExpression teamNameEq(String teamNameCond) {
        return !hasText(teamNameCond) ? null : team.name.eq(teamNameCond);
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return !hasText(usernameCond) ? null : member.username.eq(usernameCond);
    }
}
