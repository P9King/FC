package com.example.fc.memberJobHunting.memberJobHuntingDto;

import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingEmailVo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberJobHuntingEmailDto {
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
    private String frontPh;
    private String middlePh;
    private String lastPh;
    private String detail;

    public static MemberJobHuntingEmailDto voToDto(MemberJobHuntingEmailVo vo){
        MemberJobHuntingEmailDto dto = new MemberJobHuntingEmailDto();
        dto.setToEmail(vo.getToEmail());
        dto.setMyEmail(vo.getMyEmail());
        dto.setId(vo.getId());
        dto.setCareer(vo.getCareer());
        dto.setPayment(vo.getPayment());
        dto.setMyFramework(vo.getMyFramework());
        dto.setTitle(vo.getTitle());
        dto.setMyPhone(vo.getMyPhone());
        dto.setMyStack(vo.getMyStack());
        dto.setContractPeriod(vo.getContractPeriod());
        dto.setDetail(vo.getDetail());

        return dto;
    }

}
