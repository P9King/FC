package com.example.fc.support.supportController;

import com.example.fc.epRecruit.epRecruitVo.EpRecruitLeftJoinMainThumbnailVO;
import com.example.fc.support.supportService.NoticeService;
import com.example.fc.support.supportVo.NoticeFilesVo;
import com.example.fc.support.supportVo.NoticeVo;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class NoticeController {
    @Value("${noticeContentUploadPath}")
    String noticeContentUploadPath;

    public final NoticeService noticeService;

    @PostMapping(value = "uploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = new JsonObject();

        String fileRoot = noticeContentUploadPath;	//저장될 외부 파일 경로  C:\\summernote_image\\
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
            jsonObject.addProperty("url", "/notice/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        System.out.println("/uploadSummernoteImageFile >>> 요청드러옹ㅁ");
        return jsonObject;
    }

    //     공지사항 리스트
    @GetMapping("/noticeAllList")
    public String noticeList(Model model, @PageableDefault(page = 0, size = 10)Pageable pageable) {
        List<NoticeVo> noticeList = noticeService.noticeAllList();
        System.out.println("noticeList control = " + noticeList);

        //    getOffset은 현제 페이지 넘버를 알려주는 함수
        final int start = (int) pageable.getOffset();
//    getPageSize() 는 화면에 보여줄 리스트 수
        final int end = Math.min(start + pageable.getPageSize(), noticeList.size());
//    System.out.println("epRecruitList.size() == " + epRecruitList.size());

        final Page<NoticeVo> page = new PageImpl<>(noticeList.subList(start, end), pageable, noticeList.size());


        model.addAttribute("noticeList", page);


        return "/support/noticeListForm";
    }

    //    공지사항 글 쓰기
    @GetMapping("/noticeInsert")
    public String noticeInsert() {
        return "/support/noticeInsertForm";
    }

    @PostMapping("/noticeInsert")
    public String noticeInsert(NoticeVo noticeVo, HttpSession session) throws IOException {
        session.getAttribute("memberLogin");
        System.out.println(session);
        int uploadResult = noticeService.noticeInsert(noticeVo);
        System.out.println("컨트롤러 게시판 저장 result = " + uploadResult);

        return "redirect:/noticeAllList"; // return는 html경로 => 순수 html만 보여줘서 오류
    }


    //   공지사항 상세 조회
    @GetMapping("/noticeOneList")
    public String noticeOneList(Model model, int noticeBoard) {
        NoticeVo noticeVo = noticeService.noticeOneList(noticeBoard);
        List<NoticeFilesVo> noticeFilesVo = noticeService.noticeFilesList(noticeBoard);
        model.addAttribute("noticeList", noticeVo);
        model.addAttribute("noticeFilesList", noticeFilesVo);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + noticeFilesVo);
        System.out.println("-===========================" + noticeBoard);
        System.out.println("-===========================" + noticeVo);
        return "/support/noticeDetailForm";
    }


    //    공지사항 수정
    @GetMapping("/noticeModify")
    public String noticeModify(Model model,int noticeBoard) {
        NoticeVo noticeVo = noticeService.noticeOneList(noticeBoard);
        model.addAttribute("noticeList", noticeVo);
        return "/support/noticeModifyForm";
    }

    @PostMapping("/noticeModify")
    public String noticeModify(Model model, NoticeVo noticeVo) {
        noticeService.noticeModify(noticeVo);
        System.out.println("=================================!!!:" + noticeVo);
        model.addAttribute("noticeList", noticeVo);
        System.out.println("============================" + noticeVo);
        return "redirect:/noticeAllList";
    }

    //    공지사항 삭제
    @PostMapping("/noticeDelete")
    public String noticeDelete(int noticeBoard) {
        System.out.println("삭제되기전 값 " + noticeBoard);
        noticeService.noticeDelete(noticeBoard);
        System.out.println("삭제되었습니다 " + noticeBoard);
        return "redirect:/noticeAllList";
    }

}
