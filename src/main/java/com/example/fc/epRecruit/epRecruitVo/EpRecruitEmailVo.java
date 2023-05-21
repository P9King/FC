package com.example.fc.epRecruit.epRecruitVo;

import com.example.fc.epRecruit.epRecruitDto.EpRecruitDto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EpRecruitEmailVo {

    private Long id;
    private String title;
    private String career;
    private String myStack;
    private String myFramework;
    private String payment;
    private String contractPeriod;
    private String toEmail;
    private String myEmail;
    private String myPhone;
    private String detail;

    public static EpRecruitEmailVo dtoToVo(EpRecruitDto dto){
        EpRecruitEmailVo vo = new EpRecruitEmailVo();
        vo.setToEmail(dto.getToEmail());
        vo.setMyEmail(dto.getMyEmail());
        vo.setId(dto.getId());
        vo.setCareer(dto.getCareer());
        vo.setPayment(dto.getPayment());
        vo.setMyFramework(dto.getMyFramework());
        vo.setTitle(dto.getTitle());
        vo.setMyPhone(dto.getMyPhone());
        vo.setMyStack(dto.getMyStack());
        vo.setContractPeriod(dto.getContractPeriod());
        vo.setDetail(dto.getDetail());

        return vo;
    }
}
