package com.example.fc.email.dao.verification;

import com.example.fc.email.model.EmailVerification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailVerificationDao {

    /*기업 관련*/
    //인증코드를 발급한 이메일과 인증 코드 저장
    void insertEpVerification(@Param("email") String email, @Param("strCode") String strCode);

    //이메일 인증
    public int epCheckVerification(EmailVerification emailVerification);

    //이메일 인증 완료
    void epGotVerification(EmailVerification emailVerification);

    /*개인회원 관련*/
    //인증코드를 발급한 이메일과 인증 코드 저장
    void insertMemberVerification(@Param("email") String email, @Param("strCode") String strCode);

    //이메일 인증
    public int memberCheckVerification(EmailVerification emailVerification);


    //이메일 인증 완료
    void memberGotVerification(EmailVerification emailVerification);


}
