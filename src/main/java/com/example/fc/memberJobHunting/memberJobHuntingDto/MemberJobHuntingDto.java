package com.example.fc.memberJobHunting.memberJobHuntingDto;

import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MemberJobHuntingDto {
    private Long memberBoard;
    private Long memberId;
    private String title;
    private String stack;
    private String showingPeriod;
    private String recruitCondition;
    private String gender;
    private String payment;
    private String workDayStart;
    private String workDayEnd;
    private String addr;
    private String detail;
    private String createDate;
    private String updateDate;
    private int fileAttached;

    public static MemberJobHuntingDto voToDto(MemberJobHuntingVo vo){
        MemberJobHuntingDto dto = new MemberJobHuntingDto();
        dto.setMemberBoard(vo.getMemberBoard());
        dto.setMemberId(vo.getMemberId());
        dto.setTitle(vo.getTitle());
        dto.setStack(vo.getStack());
        dto.setShowingPeriod(vo.getShowingPeriod());
        dto.setRecruitCondition(vo.getRecruitCondition());
        dto.setGender(vo.getGender());
        dto.setPayment(vo.getPayment());
        dto.setWorkDayStart(vo.getWorkDayStart());
        dto.setWorkDayEnd(vo.getWorkDayEnd());
        dto.setAddr(vo.getAddr());
        dto.setDetail(vo.getAddr());
        dto.setDetail(vo.getDetail());
        dto.setCreateDate(vo.getCreateDate());
        dto.setUpdateDate(vo.getUpdateDate());

        return dto;
    }
}
