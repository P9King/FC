package com.example.fc.chat.chatVo;

import com.example.fc.chat.chatDto.ChatDetailDto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatDetailVo {
    private Long id;
    private int ChatRoom;
    private String role;
    private String sender;
    private String chatDetail;
    private Date chatTime;

    public static ChatDetailVo dtoToVo(ChatDetailDto dto) {
        ChatDetailVo vo = new ChatDetailVo();
        vo.setId(dto.getId());
        vo.setChatRoom(dto.getChatRoom());
        vo.setChatTime(dto.getChatTime());
        vo.setChatDetail(dto.getChatDetail());
        vo.setRole(dto.getRole());
        vo.setSender(vo.getSender());

        return vo;
    }
}
