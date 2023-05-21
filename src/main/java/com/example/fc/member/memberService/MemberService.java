package com.example.fc.member.memberService;

import com.example.fc.email.dto.EmailVerificationDto;
import com.example.fc.email.model.EmailVerification;
import com.example.fc.email.service.EmailSenderService;
import com.example.fc.member.memberDao.MemberDao;
import com.example.fc.member.memberVo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberDao memberDao;

    //이메일 보내기
    private final EmailSenderService emailSenderService;

    //인증을 위한 해쉬맵
    private final HashMap<String, UUID> hashMap;

    /* insert , update는 리턴타입이 int이므로 리턴받지 않고 실행만 할 수 있음 */
    /* 회원 가입 */
    public void memberJoin(MemberVo memberVo) {

        System.out.println("mp vervice mVo = " + memberVo);
        int result = memberDao.memberJoin(memberVo);
        System.out.println("epservice join result = " + result);

        //이메일 인증코드 발송
        if (result == 1) {
            //이메일 인증을 위한 암호코드
            UUID code = UUID.randomUUID();
            hashMap.put(memberVo.getEmail(), code);
            System.out.println("hashMap = " + hashMap.keySet());

            //이메일 전송
            emailSenderService.memberSignUpEmail(memberVo.getEmail(), hashMap);

            //이메일 인증에 필요한 암호키를 저장
            emailSenderService.insertMemberVerification(memberVo, code);

        }
    }

    /* 회원 수정 */
    public void memberModify(MemberVo memberVo) {
        memberDao.memberModify(memberVo);
    }

    /* 회원 삭제 */
    public void memberDelete(MemberVo memberVo) {
        memberDao.memberDelete(memberVo);
    }

    /* 로그인 */
    public MemberVo memberLogin(MemberVo memberVo) {
        return memberDao.memberLogin(memberVo);
    }

    public int idCheck(MemberVo memberVo) throws Exception {
        System.out.println("epVo service = " + memberVo);
        int result = memberDao.idCheck(memberVo);
        return result;
    }

    public MemberVo memberPasswordCheck(MemberVo memberVo) {
        return memberDao.memberPasswordCheck(memberVo);
    }

    //개인회원
    public MemberVo memberEmailCheck(MemberVo memberVo) {
        return memberDao.memberEmailCheck(memberVo);
    }

    //이메일 인증
    public int memberEmailVerifying(EmailVerificationDto emailVerificationDto) {
        System.out.println("memberVo = " + emailVerificationDto);
        //로그인 이메일과 키값 비교
        int check = emailSenderService.memberCheckVerification(emailVerificationDto);
        System.out.println("check = " + check);
        
        //member 테이블의 emai_check 값 1 주기
        if (check == 1) {
            emailSenderService.memberGotVerification(emailVerificationDto);
            EmailVerification vo = EmailVerification.dtoToModel(emailVerificationDto); //dto가 없는 member를 위해 vo로 변환

            int result = memberDao.emailVerified(vo);
            return result;
        }

        return 0; //실패

    }
}

