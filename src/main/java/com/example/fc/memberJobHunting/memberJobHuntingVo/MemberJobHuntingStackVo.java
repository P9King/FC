package com.example.fc.memberJobHunting.memberJobHuntingVo;

import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingStackDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

//구직 게시판에서 사용자가 선택한 기술들을 담는 vo
@Getter
@Setter
@ToString
public class MemberJobHuntingStackVo {
    private Long memberBoard;
    private Long memberId;
    private Date updateDate;
    private String[] stack;

    public static MemberJobHuntingStackVo dtoToVo(MemberJobHuntingStackDto dto){
        MemberJobHuntingStackVo vo = new MemberJobHuntingStackVo();
        vo.setMemberBoard(dto.getMemberBoard());
        vo.setMemberId(dto.getMemberId());
        vo.setUpdateDate(dto.getUpdateDate());
        vo.setStack(dto.getStack());

        return vo;
    }

}
