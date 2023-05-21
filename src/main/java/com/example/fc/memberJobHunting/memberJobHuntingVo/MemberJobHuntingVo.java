package com.example.fc.memberJobHunting.memberJobHuntingVo;

import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class MemberJobHuntingVo {
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

    public static MemberJobHuntingVo dtoToVo(MemberJobHuntingDto dto) {
        MemberJobHuntingVo vo = new MemberJobHuntingVo();
        vo.setMemberBoard(dto.getMemberBoard());
        vo.setMemberId(dto.getMemberId());
        vo.setTitle(dto.getTitle());
        vo.setStack(dto.getStack());
        vo.setShowingPeriod(dto.getShowingPeriod());
        vo.setRecruitCondition(dto.getRecruitCondition());
        vo.setGender(dto.getGender());
        vo.setPayment(dto.getPayment());
        vo.setWorkDayStart(dto.getWorkDayStart());
        vo.setWorkDayEnd(dto.getWorkDayEnd());
        vo.setAddr(dto.getAddr());
        vo.setDetail(dto.getAddr());
        vo.setDetail(dto.getDetail());
        vo.setCreateDate(dto.getCreateDate());
        vo.setUpdateDate(dto.getUpdateDate());

        return vo;
    }
}
