package com.example.fc.chat.chatDto;

import com.example.fc.chat.chatVo.ChatDetailVo;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatDetailDto {
    private Long id;
    private int ChatRoom;
    private String role;
    private String sender;
    private String chatDetail;
    private Date chatTime;

    public static ChatDetailDto voToDto(ChatDetailVo vo){
        ChatDetailDto dto = new ChatDetailDto();
        dto.setId(vo.getId());
        dto.setChatRoom(vo.getChatRoom());
        dto.setChatTime(vo.getChatTime());
        dto.setSender(vo.getSender());
        dto.setRole(vo.getRole());
        dto.setChatDetail(vo.getChatDetail());

        return dto;
    }

    public static List<ChatDetailDto> voListToDtoList(List<ChatDetailVo> result) {
        List<ChatDetailDto> list = new ArrayList<>();
        for (ChatDetailVo chatDetailVo: result) {
            ChatDetailDto dto = ChatDetailDto.voToDto(chatDetailVo);
            list.add(dto);
        }
        return list;
    }
}
