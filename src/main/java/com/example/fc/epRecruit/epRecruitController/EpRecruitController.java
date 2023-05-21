package com.example.fc.epRecruit.epRecruitController;

import com.example.fc.email.service.EmailSenderService;
import com.example.fc.ep.epVo.EpVo;
import com.example.fc.epRecruit.epRecruitDto.EpRecruitDto;
import com.example.fc.epRecruit.epRecruitService.EpRecruitService;

import com.example.fc.epRecruit.epRecruitVo.*;
import com.example.fc.member.memberVo.MemberVo;
import com.example.fc.memberJobHunting.memberJobHuntingservice.impl.MemberJobHuntingServiceImpl;
import com.google.gson.JsonObject;
import com.example.fc.epRecruit.epRecruitVo.EpRecruitVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/epRecruit")
@RequiredArgsConstructor
@Log4j2
public class EpRecruitController {
    @Value("${epRecruitContentUploadPath}")
    String epRecruitContentUploadPath;
    private final EpRecruitService epRecruitService;

    //개인회원에게 이메일 보내기위함
    public final MemberJobHuntingServiceImpl jobHunting;
    
    //이메일 관련 서비스
    private final EmailSenderService emailSenderService;

    @GetMapping("/epRecruitForm")
    public String recruitForm(HttpSession session) {
        EpVo epVo = (EpVo) session.getAttribute("epLogin");
        System.out.println("session >>" + epVo);

        if (session.getAttribute("epLogin") != null) {
            return "/epRecruit/epRecruitForm";
        } else {
            return "redirect:/login";
        }

    }

    //  구인 게시판 저장
    @PostMapping("/epRecruitAction")
    public String epRecruitSave(EpRecruitVO epRecruitVO, String showingDate, String showingHour, String showingMin, @RequestParam("file") MultipartFile[] files, HttpSession session) throws IOException {

        String showingPeriod = showingDate + " " + showingHour + ":" + showingMin + ":59";
        log.info("showingPeriod>>> " + showingPeriod);

        int res = epRecruitService.epRecruitSave(epRecruitVO, showingDate, showingHour, showingMin, files, session);

        return "redirect:/epRecruit/epRecruitList";

    }

    //  @PostMapping("/epRecruitActionAjax")
//  @ResponseBody
//  public int epRecruitSave(EpRecruitVO epRecruitVO, HttpSession session) {
//    int res = epRecruitService.epRecruitSave(epRecruitVO, session);
//
//    return res;
//  }
    @PostMapping(value = "uploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = new JsonObject();

        String fileRoot = epRecruitContentUploadPath;    //저장될 외부 파일 경로  C:\\upload\\epRecruit\\content\\
        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    //파일 확장자

        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);    //파일 저장
            jsonObject.addProperty("url", "/epRecruitContent/" + savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);    //저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        System.out.println("/uploadSummernoteImageFile >>> 요청드러옹ㅁ");
        return jsonObject;
    }


    @GetMapping("epRecruitActionSuccess")
    public String epRecruitActionSuccess(Model model) {
        Long epRecruitLastId = epRecruitService.epRecruitLastId();
        model.addAttribute("epRecruitLastId", epRecruitLastId);
        return "/epRecruit/epRecruitActionSuccess";
    }

    @GetMapping("/epRecruitList")
    public String epRecruitList(@RequestParam(value = "stack", required = false, defaultValue = "") String stack,
                                @RequestParam(value = "title", required = false, defaultValue = "") String title,
                                Model model, HttpSession session, @PageableDefault(page = 0, size = 6) Pageable pageable) {
        boolean epLogin = session.getAttribute("epLogin") != null; // 로그인 상태
        boolean memberLogin = session.getAttribute("memberLogin") != null;
        boolean stackIsNull = stack == null;
        boolean titleIsNull = title == null;

        System.out.println(stack);
        System.out.println(title);

        List<EpRecruitVO> epRecruitList = null;

        if ( epLogin || memberLogin ) {

            if ((stackIsNull || stack.equals("") ) && ( titleIsNull || title.equals("") )) {
                epRecruitList = epRecruitService.epRecruitList();
                System.out.println("여기1");
            } else if ((stackIsNull || stack.equals("") ) && !titleIsNull) {
                epRecruitList = epRecruitService.epFindByTitleList(title);
                System.out.println("여기2");
            } else if ( !stackIsNull ) {
                epRecruitList = epRecruitService.epFindByStackAndTitleList(stack, title);
                System.out.println("여기3");
            } else {
                epRecruitList = epRecruitService.epRecruitList();
            }

//    getOffset은 현제 페이지 넘버를 알려주는 함수
            final int start = (int) pageable.getOffset();
//    getPageSize() 는 화면에 보여줄 리스트 수
            final int end = Math.min(start + pageable.getPageSize(), epRecruitList.size());

            final Page<EpRecruitVO> page = new PageImpl<>(epRecruitList.subList(start, end), pageable, epRecruitList.size());

            model.addAttribute("epList", page);

            return "/epRecruit/epRecruitList";
        }  else {
            return "redirect:/login";
        }
    }

    @GetMapping("updateForm")
    public String update(Model model, Long epBoard) {

        EpRecruitVO epRecruitFindOne = epRecruitService.epRecruitFindOne(epBoard); // 게시판 정보
        List<EpRecruitStackVO> epRecruitStacksByBoard = epRecruitService.epRecruitStacksByBoard(epBoard); // 스택들
        HashMap<String, Object> ep = epRecruitService.epNameFindByEpBoard(epBoard); // 글쓴이 이름

        System.out.println("dasdsadasd  " + epRecruitStacksByBoard);

        model.addAttribute("epRecruit", epRecruitFindOne);
        model.addAttribute("epRecruitStack", epRecruitStacksByBoard);
        model.addAttribute("ep", ep);

        return "epRecruit/epRecruitUpdate";
    }

    @PostMapping("updateAction")
    public String update(EpRecruitVO epRecruitVO, String showingDate, String showingHour, String showingMin, HttpSession session, Long epBoard, RedirectAttributes redirect) {
//        epRecruit 수정
//        epRecruit 스택들 수정
        log.info("update >>> " + epBoard);
        log.info("update >>> " + epRecruitVO);
        epRecruitService.epRecruitUpdate(epRecruitVO, showingDate, showingHour, showingMin, session, epBoard);

        redirect.addAttribute("epBoard", epBoard);
        return "redirect:/epRecruit/poster";
//        return "epRecruit/epRecruitUpdate";
    }

    @GetMapping("delete")
    public String delete(Long epBoard) {
//        eprecruit 번호 삭제
//        eprecruitStack 번호 삭제
        epRecruitService.epRecruitDeleteById(epBoard);
        return "redirect:/epRecruit/epRecruitList";
    }

    //  기업 게시글
    @GetMapping("poster")
    public String getEpBoard(Model model, HttpSession session, Long epBoard) {
        EpVo epVo = (EpVo) session.getAttribute("epLogin");
        System.out.println();
        log.info("poseter session >>> " + epVo);

        EpRecruitVO epRecruitFindOne = epRecruitService.epRecruitFindOne(epBoard);
        Long writer = epRecruitFindOne.getEpId();
        EpVo writerInfo = epRecruitService.epInfo(writer);
        List<EpRecruitStackVO> epRecruitStacksByBoard = epRecruitService.epRecruitStacksByBoard(epBoard);
        HashMap<String, Object> ep = epRecruitService.epNameFindByEpBoard(epBoard);


        model.addAttribute("writerInfo", writerInfo);
        model.addAttribute("epRecruit", epRecruitFindOne);
        model.addAttribute("ep", ep);
        model.addAttribute("epRecruitStack", epRecruitStacksByBoard);
        return "epRecruit/epRecruitPoster";
    }

    //지원 추천서 양식
    @GetMapping("/sendEmail")
    public String getSendEmail(@RequestParam Long toSendAddr, Model model){
        MemberVo writerInfo = jobHunting.memberInfo(toSendAddr);
       // EpVo writerInfo = epRecruitService.epInfo(toSendAddr);
        System.out.println("ep writerInfo = " + writerInfo);

        model.addAttribute("writerInfo",writerInfo);
        return "epRecruit/epRecruitEmailForm";
    }

    //이력서 보내기
    @PostMapping("/sendEmail")
    @ResponseBody
    public String PostSendEmail(EpRecruitDto dto){
        System.out.println("dto = " + dto);
        System.out.println("이프문 통과?");
        int send = emailSenderService.sendEmailToMember(dto);
        if (send==1) {
            return "이메일을 성공적으로 보냈습니다";
        }

        return "이메일 보내기 실패.";
    }

}
