package com.example.fc.ep.epService;

import com.example.fc.email.dto.EmailVerificationDto;
import com.example.fc.email.model.EmailVerification;
import com.example.fc.email.service.EmailSenderService;
import com.example.fc.ep.epDao.EpDao;
import com.example.fc.ep.epVo.EpVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EpService {

    @Autowired
    EpDao epDao;

    //이메일 보내기
    private final EmailSenderService emailSenderService;


    //인증을 위한 해쉬맵
    private final HashMap<String, UUID> hashMap;

    public void  epJoin(EpVo epVo){
        System.out.println("ep vervice epVo = " + epVo);
        int result = epDao.epJoin(epVo);

        if(result == 1){
            //이메일 인증을 위한 암호코드
            UUID code = UUID.randomUUID();
            hashMap.put(epVo.getEmail(), code);
            System.out.println("hashMap = " + hashMap.keySet());

            //이메일 인증에 필요한 암호키를 저장
            emailSenderService.insertEpVerification(epVo, code);

            //이메일 전송
            emailSenderService.epSignUpEmail(epVo.getEmail(), hashMap);

        }



    }

    public  EpVo epLogin(EpVo epVo){
        return  epDao.epLogin(epVo);

    }
    
    public void epModify(EpVo epVo){
        epDao.epModify(epVo);
    }

        public  int epDelete(EpVo epVo){
            return epDao.epDelete(epVo);}

    public int idCheck(EpVo epVo) throws Exception{
        System.out.println("epVo service = " + epVo);
        int result = epDao.idCheck(epVo);
        return result;
    }
    public int nameCheck(EpVo epVo) throws Exception{
        System.out.println("epVo service = " + epVo);
        int result = epDao.nameCheck(epVo);
        return result;
    }
    public EpVo epPasswordCheck(EpVo epVo){return  epDao.epPasswordCheck(epVo);}
    public EpVo epEmailCheck(EpVo epVo){return  epDao.epEmailCheck(epVo);
    }

    //이메일 인증
    public int epEmailVerifying(EpVo epVo, EmailVerificationDto emailVerificationDto) {
        int check = emailSenderService.epCheckVerification(emailVerificationDto);
        if (check == 1) {
            emailSenderService.epGotVerification(emailVerificationDto);
            int result = epDao.emailVerified(epVo);
            return result;
        }

        return 0; //실패
    }
}