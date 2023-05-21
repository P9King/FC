package com.example.fc.memberJobHunting.memberJobHuntingVo;

import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingEmailDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberJobHuntingEmailVo {

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

    public static MemberJobHuntingEmailVo dtoToVo(MemberJobHuntingEmailDto dto){
        MemberJobHuntingEmailVo vo = new MemberJobHuntingEmailVo();
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
