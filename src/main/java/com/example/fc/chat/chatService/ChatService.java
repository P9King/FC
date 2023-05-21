package com.example.fc.chat.chatService;

import com.example.fc.chat.chatDto.ChatDetailDto;
import com.example.fc.chat.chatDto.ChatMessageDto;
import com.example.fc.chat.chatVo.ChatDetailVo;
import com.example.fc.chat.chatVo.ChatMessageVo;

import java.util.HashMap;
import java.util.List;

public interface ChatService {
    /*member 관련*/

    //방 존재 유무
    int findMemberRoomById(int memberRoom);

    //방 생성
    int createMemberRoom(int memberRoom);

    //채팅 저장
    public int insertMemberMsg(ChatMessageDto chatMessageDto);

    //채팅 내역 불러오기
    public List<ChatDetailDto> findMemberChatHistory(int memberRoom);

    //모든 개인회원 채팅방 불러오기
    public List<ChatDetailDto> findAllMemberChat();

    /*ep관련*/
    //방 존재 유무 판단
    public int findEpRoomById(int epRoom);

    //방 생성
    public int createEpRoom(int epRoom);

    //채팅 저장
    public int insertEpMsg(ChatMessageDto chatMessageDto);

    //채팅 내역 불러오기
    public List<ChatDetailDto> findEpChatHistory(HashMap map);

    //모든 기업회원 채팅방 불러오기
    public List<ChatDetailDto> findAllEpChat();


}
