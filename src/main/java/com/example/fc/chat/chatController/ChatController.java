package com.example.fc.chat.chatController;




import com.example.fc.chat.chatDto.ChatDetailDto;
import com.example.fc.chat.chatDto.ChatMessageDto;
import com.example.fc.chat.chatService.ChatService;
import com.example.fc.chat.chatVo.ChatDetailVo;
import com.example.fc.chat.chatVo.ChatMessageVo;
import com.example.fc.chat.chatService.impl.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    //@SendTo() 이용안하면 사용하는 변수
    // convertAndSend 는 객체를 인자로 넘겨주면 자동으로 Message 객체로 변환 후 도착지로 전송한다.
   private final SimpMessageSendingOperations sendingOperations;
    public  final SimpMessagingTemplate template;


    //관리자용 모든 채팅방 보기
    @GetMapping("/chatRoom")
    public String adminRoom(Model model){
        //모든 채팅 불러오기
        List<ChatDetailDto> allMemberChatList = chatService.findAllMemberChat();
        System.out.println("allMemberChatList = " + allMemberChatList.size());
        if(allMemberChatList.size()==0){
            System.out.println("일반회원 채팅 내역이 없습니다.");
            model.addAttribute("memberList",0);
        }else {
            model.addAttribute("memberList", allMemberChatList);
        }
        List<ChatDetailDto> allEpChatList = chatService.findAllEpChat();
        System.out.println("allEpChatList = " + allEpChatList);
        if(allEpChatList.size() == 0){
            System.out.println("기업회원 채팅 내역이 없습니다.");
            model.addAttribute("epList", 0);
        }else {
            model.addAttribute("epList",allEpChatList);
        }

        return "chat/view/chatRoom";
    }

    @GetMapping("/memberChatRoom/{memberRoom}")
    public String getMemberChat(@PathVariable("memberRoom") String memberRoom){

        return "chat/view/memberChat";
    }

    @GetMapping("/epChatRoom/{epRoom}")
    public String getEpChatMonitor(@PathVariable("epRoom") String epRoom ){

        return "chat/view/epChat";
    }


    //일반회원용 채팅
    @GetMapping("/memberChat/enterMemberRoom/{memberRoom}")
    public String getMemberChat(@PathVariable("memberRoom") int memberRoom, Model model){
        System.out.println("memberRoom = " + memberRoom);
        //방 존재 유무
        int result = chatService.findMemberRoomById(memberRoom);
        if (result == 0) {
            //방 생성
           int createRoom = chatService.createMemberRoom(memberRoom);
            System.out.println("채팅방 생성 결과 = " + createRoom);
        } else if (result>0) {
            //대회 기록 불러오기
            List<ChatDetailDto> chatHistory = chatService.findMemberChatHistory(memberRoom);
            chatHistory.forEach((i)-> System.out.println("채팅 내역 = " + i) );
            model.addAttribute("chatHistory", chatHistory);
        }
        return "chat/view/memberChat";
    }

    //기업회원용 채팅
    @GetMapping("/epChat/enterEpRoom/{epRoom}")
    public String getEpChat(@PathVariable("epRoom") int epRoom, Model model){
        System.out.println("epRoom = " + epRoom);
        //방 존재 유무
        int result = chatService.findEpRoomById(epRoom);
        System.out.println("result 기업 회원 방개수= " + result);
        if (result == 0) {
            //방 생성
            int createRoom = chatService.createEpRoom(epRoom);
            System.out.println("채팅방 생성 결과 = " + createRoom);
        } else if (result == 1) {
            //대회 기록 불러오기
            System.out.println("여기에 들어오긴해?");
            HashMap map = new HashMap();
            map.put("section", "ep");
            map.put("epRoom",epRoom);
            List<ChatDetailDto> chatHistory = chatService.findEpChatHistory(map);
            chatHistory.forEach((i)-> System.out.println("채팅 내역 = " + i) );
            model.addAttribute("chatHistory", chatHistory);
        }

        return "chat/view/epChat";
    }

    //메세지 맵핑 관련
    @MessageMapping("/chat/memberMsg")
    public void userChatMapping(@Payload ChatMessageDto chatMessageDto){
        System.out.println("chatMessage = " + chatMessageDto);

       //받아온 메세지를 db에 저장
        int insertMsg = chatService.insertMemberMsg(chatMessageDto);

        //db에 메세지 삽입 성공시 메세지 보내기
        if(insertMsg == 1){
            template.convertAndSend("/topic/memberRoom/"+chatMessageDto.getMemberVo().getId(), chatMessageDto);
        }
    }

    @MessageMapping("/chat/epMsg")
    public void epChatMapping(@Payload ChatMessageDto chatMessageDto, String epRoom){
        System.out.println("chatMessage = " + chatMessageDto);
        System.out.println("epRoom = " + epRoom);
        //받아온 메세지를 db에 저장
        int insertMsg = chatService.insertEpMsg(chatMessageDto);

        //db에 메세지 삽입 성공시 메세지 보내기
        if(insertMsg == 1){

            template.convertAndSend("/topic/epRoom/"+ chatMessageDto.getEpRoom(), chatMessageDto);
        }

    }

}