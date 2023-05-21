package com.example.fc.chat.chatDto;

import com.example.fc.chat.chatVo.ChatMessageVo;
import com.example.fc.ep.epVo.EpVo;
import com.example.fc.member.memberVo.MemberVo;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

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

    public static ChatMessageDto voToDto(ChatMessageVo vo){
        ChatMessageDto dto = new ChatMessageDto();
        dto.setContent(vo.getContent());
        dto.setSender(vo.getSender());
        dto.setRole(vo.getRole());
        dto.setEpVo(vo.getEpVo());
        dto.setEpRoom(vo.getEpRoom());
        dto.setMemberRoom(vo.getMemberRoom());
        dto.setMemberVo(vo.getMemberVo());
        dto.setSection(vo.getSection());

        return dto;
    }
}

