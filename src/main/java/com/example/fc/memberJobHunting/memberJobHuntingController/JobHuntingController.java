package com.example.fc.memberJobHunting.memberJobHuntingController;

import com.example.fc.email.service.EmailSenderService;
import com.example.fc.ep.epVo.EpVo;
import com.example.fc.epRecruit.epRecruitService.EpRecruitService;
import com.example.fc.member.memberVo.MemberVo;
import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingDto;
import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingEmailDto;
import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingStackDto;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingFilesVo;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingStackVo;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingVo;
import com.example.fc.memberJobHunting.memberJobHuntingservice.MemberJobHuntingService;
import com.example.fc.memberJobHunting.memberJobHuntingservice.impl.MemberJobHuntingServiceImpl;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/jobHunting")
@Controller
public class JobHuntingController {


    public final MemberJobHuntingService jobHuntingService;

    //기업회원에게 이메일 보내기위함
    private final EpRecruitService epRecruitService;
    private final EmailSenderService emailSenderService;

    /*구직 게시글 부분*/
    @GetMapping("/jobHuntingForm")
    public String getJobHuntingService() {

        return "jobHunting/memberJobHuntingForm";
    }

    //구직게시판 저장
    @PostMapping("/jobHuntingForm")
    public String postJobHuntingForm(MemberJobHuntingDto jobHuntingDto, MultipartFile[] file, String showingDate, String showingHour, String showingMin, HttpSession session) throws IOException {
        System.out.println("insertResult = " + file[0].getResource());
        System.out.println("jobHunt = " + jobHuntingDto);
        String showingPeriod = showingDate + " " + showingHour + ":" + showingMin + ":59"; //yyyy-mm-dd hh:mm:ss 으로 저장
        MemberVo userInfo = (MemberVo) session.getAttribute("memberLogin");
        jobHuntingDto.setMemberId(userInfo.getId());
        jobHuntingDto.setShowingPeriod(showingPeriod);
        int insertResult = jobHuntingService.insertJobHunting(jobHuntingDto, file);
        System.out.println("컨트롤러 게시판 저장 result = " + insertResult);

        if (insertResult == 1) {
            return "redirect:/jobHunting/jobHuntingList"; // redirect는 웹 페이지 경로 웹 페이지를 보여줌0
        }

        return "jobHunting/memberJobHuntingList"; // return는 html경로 => 순수 html만 보여줘서 오류
    }

    //구직 게시글의 파일 저장
    @PostMapping(value = "uploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
        JsonObject insertResult = jobHuntingService.insertFile(multipartFile);

        return insertResult;
    }

    //게시글 리스트
    @GetMapping("/jobHuntingList")
    public String getMemberJobHuntingList(Model model, HttpSession session, @PageableDefault(page = 0, size = 6) Pageable pageable) {
        if (session.getAttribute("epLogin") != null || session.getAttribute("memberLogin") != null) {
            List<MemberJobHuntingVo> jobHuntingList = jobHuntingService.findAllJobHunting();

            //페이징처리
            //getOffset은 현제 페이지 넘버를 알려주는 함수
            //getPageSize() 는 화면에 보여줄 리스트 수
            int start = (int) pageable.getOffset(); //처음페이지
            int end = Math.min(start + pageable.getPageSize(), jobHuntingList.size()); //마지막 페이지
            final Page<MemberJobHuntingVo> page = new PageImpl<>(jobHuntingList.subList(start, end), pageable, jobHuntingList.size());
            System.out.println("jobHuntingList = " + jobHuntingList);

            model.addAttribute("list", page);
            return "jobHunting/memberJobHuntingList";
        } else {
            return "redirect:/login";
        }
    }


    //작성된 게시글 보기
    @GetMapping("/jobHuntingPoster")
    public String getMemberJobHuntingPoster(MemberJobHuntingDto memberBoard, Model model) {
        MemberJobHuntingDto boardInfo = jobHuntingService.findAllByMemberBoard(memberBoard); //게시글 번호로 모든 정보 찾기
        MemberVo memberInfo = jobHuntingService.memberInfo(boardInfo.getMemberId());//게시글 쓴 사람 정보 다 가져오기
        System.out.println("boardInfo = " + boardInfo);
        List<MemberJobHuntingFilesVo> writerFilesInfo = jobHuntingService.findAllFilesByMemberBoard(memberBoard.getMemberBoard());//작성자가 등록한 이미지 모두 불러오기

        model.addAttribute("writerName", memberInfo);
        model.addAttribute("writerInfo", boardInfo); // 찾아온 모든 정보 모델에 삽입
        model.addAttribute("writerFilesInfo", writerFilesInfo);

        return "jobHunting/memberJobHuntingPoster";
    }

    //수정할 게시글 보기
    @GetMapping("/updateForm")
    public String update(Model model, MemberJobHuntingDto memberBoard) {
        System.out.println("memberBoard update= " + memberBoard);
        System.out.println("memberBoard id = " + memberBoard.getMemberBoard());
        MemberJobHuntingDto boardInfo = jobHuntingService.findAllByMemberBoard(memberBoard); //게시글 번호로 모든 정보 찾기
        List<MemberJobHuntingStackDto> stacks = jobHuntingService.findAllStacksByMemberBoard(memberBoard); // 스택들
        MemberVo writerInfo = jobHuntingService.memberInfo(boardInfo.getMemberId());//게시글 쓴 사람 정보 다 가져오기

        model.addAttribute("boardInfo", boardInfo);//게시글 모든 정보
        model.addAttribute("writerInfo", writerInfo); //이름

        return "jobHunting/memberJobHuntingUpdate";
    }

    //게시글 수정하기
    @PostMapping("/updateForm")
    public String postUpate(MemberJobHuntingDto memberBoard, String showingDate, String showingHour, String showingMin) {
        System.out.println("memberBoard = " + memberBoard);
        int result = jobHuntingService.updateJobHuntingBoard(memberBoard, showingDate, showingHour, showingMin);
        System.out.println("updateResult = " + result);
        if (result == 1) {
            return "redirect:/jobHunting/jobHuntingList";
        }
        return "redirect:/jobHunting/updateForm?memberBoard="+memberBoard.getMemberBoard(); //실패시 다시 돌아감
    }

    //게시글 삭제하기
    @GetMapping("/delete")
    public String delete(Long memberBoard) {
        int result = jobHuntingService.deleteMemberBoardByMemberBoard(memberBoard);
        System.out.println("게시글 삭제 결과는? = " + result);
        return "redirect:/jobHunting/jobHuntingList";
    }

    //지원서 양식
    @GetMapping("/sendEmail")
    public String getSendEmail(@RequestParam Long toSendAddr, Model model){
        EpVo writerInfo = epRecruitService.epInfo(toSendAddr);
        // MemberVo writerInfo = jobHunting.memberInfo(toSendAddr);

        model.addAttribute("writerInfo",writerInfo);
        return "jobHunting/memberJobHuntingEmailForm";
    }

    //이력서 보내기
    @PostMapping("/sendEmail")
    @ResponseBody
    public String PostSendEmail(MemberJobHuntingEmailDto dto){
        System.out.println("dto = " + dto);
        System.out.println("이프문 통과?");
        int send = emailSenderService.sendEmailToEp(dto);
        if (send==1) {
            return "이메일을 성공적으로 보냈습니다";
        }

        return "이메일 보내기 실패.";
    }

}
