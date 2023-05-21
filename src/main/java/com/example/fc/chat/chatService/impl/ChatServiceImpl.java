package com.example.fc.chat.chatService.impl;

import com.example.fc.chat.chatDao.ChatDao;
import com.example.fc.chat.chatDto.ChatDetailDto;
import com.example.fc.chat.chatDto.ChatMessageDto;
import com.example.fc.chat.chatService.ChatService;
import com.example.fc.chat.chatVo.ChatDetailVo;
import com.example.fc.chat.chatVo.ChatMessageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {


  private final ChatDao chatDao;
  
  /*member 관련*/
  //방 존재 유무
  @Override
  public int findMemberRoomById(int memberRoom) {
    Optional<Integer> result = chatDao.findMemberRoomById(memberRoom);
    if (result.isEmpty()) {
        return 0;
    }
    return result.get();
  }

  //방 생성
  @Override
  public int createMemberRoom(int memberRoom) {
    int result = chatDao.createMemberRoom(memberRoom);

    return result;
  }

  //채팅 저장
  @Override
  public int insertMemberMsg(ChatMessageDto chatMessageDto) {
    ChatMessageVo vo = ChatMessageVo.dtoToVo(chatMessageDto);
    int result = chatDao.insertMemberMsg(vo);

    return result;
  }
  
  //채팅 내역 불러오기
  @Override
  public List<ChatDetailDto> findMemberChatHistory(int memberRoom) {
    List<ChatDetailVo> result = chatDao.findMemberChatHistory(memberRoom);
    List<ChatDetailDto> list = ChatDetailDto.voListToDtoList(result);
    return list;
  }

  /*ep관련*/
  //방 존재 유무 판단
  @Override
  public int findEpRoomById(int epRoom) {
    Optional<Integer> result = chatDao.findEpRoomById(epRoom);
    if (result.isEmpty()) {
      return 0;
    }
    return result.get();
  }

  //방 생성
  @Override
  public int createEpRoom(int epRoom) {
    int result = chatDao.createEpRoom(epRoom);

    return result;
  }

  //채팅 저장
  @Override
  public int insertEpMsg(ChatMessageDto chatMessageDto) {
    ChatMessageVo vo = ChatMessageVo.dtoToVo(chatMessageDto);
    int result = chatDao.insertEpMsg(vo);

    return result;
  }

  //채팅 내역 불러오기
  @Override
  public List<ChatDetailDto> findEpChatHistory(HashMap map) {
    List<ChatDetailVo> result = chatDao.findEpChatHistory(map);
    List<ChatDetailDto> list = ChatDetailDto.voListToDtoList(result);

    return list;
  }

  //모든 개인회원 채팅방 불러오기
  @Override
  public List<ChatDetailDto> findAllMemberChat() {
    List<ChatDetailVo> result = chatDao.findAllMemberChat();
    List<ChatDetailDto> list = ChatDetailDto.voListToDtoList(result);
    return list;
  }

  //모든 기업회원 채팅방 불러오기
  @Override
  public List<ChatDetailDto> findAllEpChat(){
    List<ChatDetailVo> result = chatDao.findAllEpChat();
    List<ChatDetailDto> list = ChatDetailDto.voListToDtoList(result);
    return list;
  }

}