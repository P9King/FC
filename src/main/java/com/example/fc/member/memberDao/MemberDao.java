package com.example.fc.member.memberDao;

import com.example.fc.email.model.EmailVerification;
import com.example.fc.ep.epVo.EpVo;
import com.example.fc.member.memberVo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {

    /* 회원가입 */
    public int memberJoin(MemberVo memberVo);

    /* 회원수정 */
    public int memberModify(MemberVo memberVo);

    /* 회원탈퇴 */
    public int memberDelete(MemberVo memberVo);

    /* 로그인 */
    MemberVo memberLogin(MemberVo memberVo);
     /*아이디중복검사*/
    int idCheck(MemberVo memberVo) throws Exception;
    /*패스워드 찾기*/
    public MemberVo memberPasswordCheck(MemberVo memberVo);
    /* 이메일 찾기*/
    public MemberVo memberEmailCheck(MemberVo memberVo);

    //이메일 어쩌구
    int memberEmailVerified(MemberVo memberVo);

    //member Table 이메일 인증
    int emailVerified(EmailVerification emailVerification);
}
