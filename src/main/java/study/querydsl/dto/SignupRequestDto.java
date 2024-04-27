package study.querydsl.dto;

import lombok.Getter;
import study.querydsl.entity.UserRoleEnum;

import static study.querydsl.entity.UserRoleEnum.*;


@Getter
public class SignupRequestDto {
    private String userId;

    private String password;
    private UserRoleEnum role = USER;

    private String nickname;

    private String email;

    //어드민
    private boolean admin = false;
    private String adminToken = "";
}
