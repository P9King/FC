package com.example.fc.member.memberVo;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberVo {

    private long id;
    private String name;
    private String email;
    private String password;
    private String addr;
    private String phone;
    private String gender;
    private String birth;
    private String role;
    private String nickName;
    private int emailVerified; //email인증 0 미인증, 1인증


}
