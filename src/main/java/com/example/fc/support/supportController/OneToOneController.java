package com.example.fc.support.supportController;

import com.example.fc.ep.epVo.EpVo;
import com.example.fc.support.supportService.EpOneToOneService;
import com.example.fc.support.supportService.MemberOneToOneService;
import com.example.fc.support.supportVo.EpOneToOneVo;
import com.example.fc.support.supportVo.MemberOneToOneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OneToOneController {

    @Autowired
    MemberOneToOneService memberOneToOneService;
    @Autowired
    EpOneToOneService epOneToOneService;

//    /* 게시판 글 쓰기*/
    @ResponseBody//어노테이션을 사용하여 페이지에 데이터'만'을 전달할 수 있다.
    @GetMapping("/personalInsert")
    public String noticeInsert(HttpSession session) {
        String result;
        if (session.getAttribute("epLogin") != null || session.getAttribute("memberLogin") != null) {
            System.out.println("session31311312131213213131 = " + session);
            result = "<script>location.href='/personalQuestion';</script>";
        } else {
            result = "<script>alert('로그인후 이용해 주세요.');location.href='/login';</script>";
        }

        return result;
    }
    @GetMapping("/personalQuestion")
    public String personalQuestion(HttpSession session) {
        if (session.getAttribute("epLogin") !=null) {
            return "support/epOneToOneQuestion";

        } else if (session.getAttribute("memberLogin") !=null) {
            return "/support/memberOneToOneQuestion";
        }else {
            return "main";
        }
    }

    //insert
    @PostMapping("/personalInquire")
    public String personalInquire(MemberOneToOneVo memberOneToOneVo, EpOneToOneVo epOneToOneVo, HttpSession session) {

        if (session.getAttribute("epLogin") != null) {
            System.out.println("epOneToOneVo = " + epOneToOneVo);
            epOneToOneService.epPersonalInquire(epOneToOneVo);
            System.out.println("personalVo 입력성공하였습니다. === " + memberOneToOneVo);

        } else if (session.getAttribute("memberLogin") != null) {
            memberOneToOneService.mPersonalInquire(memberOneToOneVo);
            System.out.println("personalVo 입력성공하였습니다. === " + memberOneToOneVo);
        }
            return "main";
    }
    @GetMapping("/epPage")
    public String myPage(HttpSession session, Model model) {
        EpOneToOneVo epOneToOneVo = (EpOneToOneVo) session.getAttribute("epOneToOneVo");
        if (epOneToOneVo == null) {
            epOneToOneVo = new EpOneToOneVo();
            session.setAttribute("epOneToOneVo", epOneToOneVo);
        }
        EpVo epVo = (EpVo) session.getAttribute("epLogin");
        System.out.println("Epvo ==>>>> " + epVo);
        int count = epOneToOneService.epOneToOneCount(epVo.getEmail());
        model.addAttribute("count", count);
//    System.out.println("epOneToOneVo = " + epOneToOneVo);
//    System.out.println("count = " + count);
//    session.getAttribute("epLogin");
//    System.out.println("세션값 -----------------" + session.getAttribute("epLogin"));
        return "/ep/epMyPageForm";
    }



    @GetMapping("/epOneToOneFindEmail")
    public String epOneToOneFindEmail(Model model, HttpSession session) {
        EpVo epVo = (EpVo) session.getAttribute("epLogin");
        EpOneToOneVo epOneToOneVo = (EpOneToOneVo) session.getAttribute("epOneToOneVo");
        int count = epOneToOneService.epOneToOneCount(epVo.getEmail());
        String emails = epVo.getEmail();
        List<EpOneToOneVo> epOneToOneFindEmail = epOneToOneService.epOneToOneFindEmail(emails);
        System.out.println("epOneToOneFindEmail---------------------------------------------------- = " + epOneToOneFindEmail);
        model.addAttribute("oneToOneList", epOneToOneFindEmail);
        String noOneToOne ="";
        if (count ==0){
           return "redirect:/failFindEmail";
        }else {
        return "/support/epOneToOneFindEmail";
        }
    }
        @ResponseBody
        @GetMapping("/failFindEmail")
        public String failFindEmail(){
          String  noOneToOne = "<script>alert('1:1문의가 존재하지않습니다 문의후 이용해주세요.');location.href='/epPage';</script>";
            return noOneToOne;
        }

//       1대1문의 상세 조회

    @GetMapping("/oneToOneList")
    public String noticeOneList(EpOneToOneVo epOneToOneVo, Model model, HttpSession session,int id) {
        epOneToOneVo = epOneToOneService.noticeOneList(id);
        model.addAttribute("oneToOneList", epOneToOneVo);
        EpVo epVo = (EpVo) session.getAttribute("epLogin");
        if(epOneToOneVo.getEmail().equals(epVo.getEmail())){
        return "/support/oneToOneDetailForm";


        }else {

        return "redirect:/ex";
        }
    }
    
    //1대1 수정
    @PostMapping("/oneToOneModify")
    public String oneToOneModify(EpOneToOneVo epOneToOneVo, HttpSession session){
        System.out.println("session*********************************** = " + session);
        System.out.println("epOneToOneVo.getIdgetIdgetIdgetIdgetIdgetIdgetIdgetId = " + epOneToOneVo);
        session.setAttribute("oneToOneList",epOneToOneVo);
        epOneToOneService.epOneToOneModify(epOneToOneVo);
        return "redirect:/epOneToOneFindEmail";
    }
//    1대 1문의 삭제
    @PostMapping("/epOneToOneDelete")
    public String epOneToOneDelete(EpOneToOneVo epOneToOneVo, HttpSession session){
        System.out.println("session*********************************** = " + session);
        System.out.println("epOneToOneVo.deletedeletedeletedeletedeletedeletedelete = " + epOneToOneVo);
        session.setAttribute("oneToOneList",epOneToOneVo);
        epOneToOneService.epOneToOneDelete(epOneToOneVo);
        return "redirect:/epOneToOneFindEmail";
    }




}
