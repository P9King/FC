package com.example.fc.memberJobHunting.memberJobHuntingDto;

import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingStackVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class MemberJobHuntingStackDto {

    private Long memberBoard;
    private Long memberId;
    private Date updateDate;
    private String[] stack;

    public static MemberJobHuntingStackDto voToDto(MemberJobHuntingStackVo vo){
        MemberJobHuntingStackDto dto = new MemberJobHuntingStackDto();
        dto.setMemberBoard(vo.getMemberBoard());
        dto.setMemberId(vo.getMemberId());
        dto.setUpdateDate(vo.getUpdateDate());
        dto.setStack(vo.getStack());

        return dto;
    }

}
