package com.example.fc.email.service.impl;

import com.example.fc.email.dao.verification.EmailVerificationDao;
import com.example.fc.email.dto.EmailVerificationDto;
import com.example.fc.email.model.EmailVerification;
import com.example.fc.email.service.EmailSenderService;
import com.example.fc.ep.epVo.EpVo;
import com.example.fc.epRecruit.epRecruitDao.EpRecruitEmailDao;
import com.example.fc.epRecruit.epRecruitDto.EpRecruitDto;
import com.example.fc.epRecruit.epRecruitVo.EpRecruitEmailVo;
import com.example.fc.member.memberVo.MemberVo;
import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingEmailDto;
import com.example.fc.memberJobHunting.memberJobHuntingDao.MemberJobHuntingEmailDao;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingEmailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {


    private final JavaMailSender mailSender;

    private final EmailVerificationDao verification;

    private final MemberJobHuntingEmailDao memberJobHuntingEmailDao;

    private final EpRecruitEmailDao epRecruitEmailDao;


    /*ep관련 이메일*/
    //회원가입시 인증 이메일 발송
    @Override
    public void epSignUpEmail(String to, HashMap verifiedCode) {
        System.out.println("verifiedCode esi = " + verifiedCode);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("fctester96@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("FC 회원가입 인증 메일 입니다.");
        simpleMailMessage.setText("http://43.201.239.8/epLogin?verifyingCode=" + verifiedCode.get(to));
        System.out.println("mailsender 메일보내기 성공 : " + mailSender);

        this.mailSender.send(simpleMailMessage);
    }

    //인증 코드 발급한 이메일과 인증 코드 저장
    @Override
    public void insertEpVerification(EpVo epVo, UUID code) {
        String email = epVo.getEmail();
        String strCode = code.toString();
        verification.insertEpVerification(email, strCode);
    }

    //이메일 인증 확인
    @Override
    public int epCheckVerification(EmailVerificationDto emailVerificationDto) {
        System.out.println("emailVerification = " + emailVerificationDto);
        EmailVerification vo = EmailVerification.dtoToModel(emailVerificationDto);
        int result = verification.epCheckVerification(vo);
        System.out.println(result);

        if (result == 1) {
            return result;
        }

        return 0;
    }

    //ep테이블 email_verified = 1 저장
    @Override
    public void epGotVerification(EmailVerificationDto emailVerificationDto) {
        EmailVerification vo = EmailVerification.dtoToModel(emailVerificationDto);
        verification.epGotVerification(vo);
    }

    /*일반회원에게 이메일 보내기*/

    //인증코드 발송
    public void memberSignUpEmail(String to, HashMap verifiedCode) {
        System.out.println("verifiedCode esi = " + verifiedCode);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("fctester96@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("FC 회원가입 인증 메일 입니다.");
        simpleMailMessage.setText("http://43.201.239.8/login?verifyingCode=" + verifiedCode.get(to));
        System.out.println("mailsender 메일보내기 성공 : " + mailSender);

        this.mailSender.send(simpleMailMessage);
    }

    //이메일 인증 코드 및 발송 이메일 저장
    @Override
    public void insertMemberVerification(MemberVo memberVo, UUID code) {
        String email = memberVo.getEmail();
        String strCode = code.toString();
        verification.insertEpVerification(email, strCode);
    }

    //이메일 인증 확인
    @Override
    public int memberCheckVerification(EmailVerificationDto emailVerificationDto) {
        System.out.println("emailVerification = " + emailVerificationDto);
        EmailVerification vo = EmailVerification.dtoToModel(emailVerificationDto);
        int result = verification.memberCheckVerification(vo);
        System.out.println(result);

        if (result == 1) {
            return result;
        }

        return 0;
    }

    @Override
    public void memberGotVerification(EmailVerificationDto emailVerificationDto) {
        EmailVerification vo = EmailVerification.dtoToModel(emailVerificationDto);
        verification.memberGotVerification(vo);
    }

    //구직 이메일 발송 == 기업에게 지원서 발송
    @Override
    public int sendEmailToEp(MemberJobHuntingEmailDto dto) {

        System.out.println("dto 이메일 서비스 임플= " + dto);
        //3부분으로 구분된 전화번를 한 문자열로 조합
        String myPhone = dto.getFrontPh() + dto.getMiddlePh() + dto.getLastPh();
        //dto를 vo로 저장
        MemberJobHuntingEmailVo vo = MemberJobHuntingEmailVo.dtoToVo(dto);
        // 조합된 번호 삽입
        vo.setMyPhone(myPhone);

        //db에 저장
         int result = memberJobHuntingEmailDao.sendEmailToEp(vo);

        //보내는이 정보
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(vo.getMyEmail()); //보낸 이
        simpleMailMessage.setTo(vo.getToEmail()); //받는 이
        simpleMailMessage.setSubject(vo.getTitle());
        simpleMailMessage.setText("경력 : " + vo.getCareer() + "\n"
                + "기술스택 : " + vo.getMyStack() + "\n"
                + "프레임 워크 : " + vo.getMyFramework() + "\n"
                + "계약금/기간 : " + vo.getPayment() + "만원" + "/" + vo.getContractPeriod() + "\n"
                + "이메일/연락처 : " + vo.getMyEmail() + "/" + vo.getMyPhone() + "\n"
                + "상세 내용 : " + vo.getDetail());
        this.mailSender.send(simpleMailMessage);
        System.out.println("이력서 보내기 성공");
        return result;
    }

    @Override
    public int sendEmailToMember(EpRecruitDto dto) {
        System.out.println("dto 이메일 서비스 임플= " + dto);
        //3부분으로 구분된 전화번를 한 문자열로 조합
        String myPhone = dto.getFrontPh() + dto.getMiddlePh() + dto.getLastPh();
        //dto를 vo로 저장
        EpRecruitEmailVo vo = EpRecruitEmailVo.dtoToVo(dto);
        // 조합된 번호 삽입
        vo.setMyPhone(myPhone);

        //db에 저장
        int result = epRecruitEmailDao.sendEmailToMember(vo);

        //보내는이 정보
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(vo.getMyEmail()); //보낸 이
        simpleMailMessage.setTo(vo.getToEmail()); //받는 이
        simpleMailMessage.setSubject(vo.getTitle());
        simpleMailMessage.setText("필요경력 : " + vo.getCareer() + "\n"
                + "기술스택 : " + vo.getMyStack() + "\n"
                + "프레임 워크 : " + vo.getMyFramework() + "\n"
                + "계약금/기간 : " + vo.getPayment() + "만원" + "/" + vo.getContractPeriod() + "\n"
                + "이메일/연락처 : " + vo.getMyEmail() + "/" + vo.getMyPhone() + "\n"
                + "상세 내용 : " + vo.getDetail());
        this.mailSender.send(simpleMailMessage);
        System.out.println("이력서 보내기 성공");
        return result;
    }

}