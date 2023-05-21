package com.example.fc.chat.chatVo;

import com.example.fc.chat.chatDto.ChatMessageDto;
import com.example.fc.ep.epVo.EpVo;
import com.example.fc.member.memberVo.MemberVo;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageVo {

    //채팅 내용
    private String content;
    
    // 채팅 보낸이
    private String sender;
    
    //객체로 정보 받아오기
    private MemberVo memberVo;
    private EpVo epVo;

    //회원 구분역할=>ep,member
    private String section;

    //방 번호 역할
    private int memberRoom;
    private int epRoom;

    //운영자와 대화 참여자 구분
    private String role;

    public static ChatMessageVo dtoToVo(ChatMessageDto dto){
        ChatMessageVo vo = new ChatMessageVo();
        vo.setContent(dto.getContent());
        vo.setEpRoom(dto.getEpRoom());
        vo.setEpVo(dto.getEpVo());
        vo.setSection(dto.getSection());
        vo.setMemberRoom(dto.getMemberRoom());
        vo.setMemberVo(dto.getMemberVo());
        vo.setRole(dto.getRole());
        vo.setSender(dto.getSender());

        return vo;
    }
}