package com.example.fc.member.memberController;


import com.example.fc.email.dto.EmailVerificationDto;
import com.example.fc.email.model.EmailVerification;
import com.example.fc.member.memberService.MemberService;
import com.example.fc.member.memberVo.MemberVo;
import lombok.RequiredArgsConstructor;
import com.example.fc.epRecruit.epRecruitService.EpRecruitService;
import com.example.fc.epRecruit.epRecruitVo.EpRecruitLeftJoinMainThumbnailVO;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingVo;
import com.example.fc.memberJobHunting.memberJobHuntingservice.impl.MemberJobHuntingServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller // 컨트롤러는 값만 넘겨주는 역할
@Slf4j
@RequiredArgsConstructor
public class MemberController {


   private final MemberService memberService;

    @Autowired
    EpRecruitService epRecruitService;

    @Autowired
    MemberJobHuntingServiceImpl jobHunting;


    @GetMapping("/")
    public String member(Model model) {
        List<EpRecruitLeftJoinMainThumbnailVO> epRecruitList = epRecruitService.epRecruitMainList();
        List<MemberJobHuntingVo> jobHuntingList = jobHunting.findAllJobHunting();
        model.addAttribute("epList", epRecruitList);
        model.addAttribute("list", jobHuntingList);
        return "main";
    }

    /* 회원가입 전 회원/기업 선택 */
    @GetMapping("/joinSelect")
    public String joinSelect(){
        return "joinForm";
    }


    /* 회원가입 */
    @GetMapping("/insert")
    public String memberJoin() {
        return "/member/memberJoinForm";
    }

    @PostMapping("/insert")
    public String memberJoin(MemberVo memberVo) {
        log.info("회원가입 폼에서 입력받은 데이터: {}", memberVo);
        memberService.memberJoin(memberVo);

        return "/loginForm";
    }

    /* 로그인 */
    @GetMapping("/login")
    public String memberLogin(HttpSession session, Model model, HttpServletRequest request,  @RequestParam(required = false) String verifyingCode) {

        String userType2 = request.getParameter("userType2");
        if (session.getAttribute("memberLogin") != null || session.getAttribute("epLogin") != null ) {
            //기존에 memberLogin 이란 세션 값이 존재한다면 기존 값을 제거해 준다.
            session.removeAttribute("memberLogin");
            session.removeAttribute("epLogin");
        }

        if(userType2 == null){
            userType2 = "0";
        }

        int userType = Integer.parseInt(userType2);

        session.removeAttribute("mPasswordFind");
        session.removeAttribute("mEmailFind");
        model.addAttribute("userType", userType);

        //post로 로그인이 되기떄문에 hidden으로 암호키 post로 전송
        model.addAttribute("verifyingCode", verifyingCode);

        return "/loginForm";
    }

    @PostMapping("/login")
    public String memberLogin(MemberVo memberVo, HttpSession session, EmailVerificationDto emailVerificationDto) {
        //로그인시 받아온 값을 서비스로 넘겨줌
        System.out.println("emailVerification ctr = " + emailVerificationDto);
        System.out.println("memberVo ctr= " + memberVo);

        String returnURL = "";
        if (session.getAttribute("memberLogin") != null) {
            //기존에 memberLogin 이란 세션 값이 존재한다면 기존 값을 제거해 준다.
            session.removeAttribute("memberLogin");
        }
        MemberVo vo = memberService.memberLogin(memberVo);
        System.out.println(vo);
        int result = memberService.memberEmailVerifying(emailVerificationDto);
        System.out.println("이메일 인증 결과 0실패, 1성공 = " + result);

        if (vo != null && vo.getEmailVerified()==1) {
            //세션에 memberLogin 이란 이름으로 memberVo 객체를 저장해 놓는다.
            session.setAttribute("memberLogin", vo);
            System.out.println("로그인 되었습니다 이메일과 비밀번호는 :" + vo);
            //로그인 성공시
            System.out.println("session 저장 값 : " + session.getAttribute( "memberLogin" ));
            return "redirect:/";
        } else {
            //로그인 실패시
            System.out.println("로그인 실패");
            returnURL = "LoginForm";
        }
        return returnURL;
    }

    
//    로그아웃
    @GetMapping("/logout")
    public String memberLogout(HttpSession session) {
        session.invalidate(); //세션 전체를 날려버린다.
//        session.removeAttribute("memberLogin"); //하나씩 날릴려면 이렇게 사용해도 된다
        return "/loginForm";
    }

//    마이페이지 호출
    @GetMapping("/myPage")
    public String myPage(HttpSession session) {
        session.getAttribute("memberLogin");
        System.out.println("세션값 -----------------" + session.getAttribute("memberLogin"));
        return "/member/memberMyPageForm";
    }
//    멤버 수정
    @GetMapping("/modify")
    public String memberModify(HttpSession session){
        session.getAttribute("memberLogin");
        return "/member/memberModifyForm";
    }

    @PostMapping("/modify")
    public String memberModify(MemberVo memberVo, HttpSession session){
        memberService.memberModify(memberVo);
        log.info("회원 수정 폼에서 입력받은 데이터: {}", memberVo);
        session.setAttribute("memberLogin",memberVo);
        return "/member/memberMyPageForm";
    }

    @PostMapping("/delete")
    public String memberDelete(MemberVo memberVo,HttpSession session){

        memberService.memberDelete(memberVo);
                  
        session.removeAttribute("memberLogin");
        log.info("--------------------들어온 값" + memberVo.getId());
        return "/loginForm";
    }
    @ResponseBody // 값 변환을 위해 꼭 필요함
    @GetMapping("/mIdCheck") // 아이디 중복확인을 위한 값으로 따로 매핑
    public int idCheck(MemberVo memberVo) throws Exception{
        System.out.println("memberVo값 = " + memberVo);
        int result = memberService.idCheck(memberVo); // 중복확인한 값을 int로 받음
        System.out.println("result +++++++++= 6+++++++++++++++++++::::" + result);
        return result;
    }
    @GetMapping("/memberPassword")
    public String memberPassword() {
        return "member/memberFindPass";
    }
    @GetMapping("/memberFindResult")
    public String memberFindResult() {return "member/memberFindResult";
    }

    //비밀번호찾기
    @PostMapping("/mPasswordFind")
    public String mPasswordFind(MemberVo memberVo, HttpSession session) {
        MemberVo vo = memberService.memberPasswordCheck(memberVo);
        System.out.println("vo = " + vo);
        String failmessage ="";

        if (vo != null) {
            failmessage ="";
            session.setAttribute("mPasswordFind", vo);
            System.out.println("비밀번호 는 ============" + vo);
            return "member/memberFindResult";
        } else {
        return "redirect:/findAlert";
        }
    }

    //실패시 알람창
    @ResponseBody
    @GetMapping("/findAlert")
    public String findAlert(){
        String failmessage ="";
        failmessage = "<script>alert('올바르지 않은 정보입니다.'); history.go(-1);</script>";
        return failmessage;
    }


    @PostMapping("/mEmailFind")
    public String mEmailFind(MemberVo memberVo, HttpSession session){
        MemberVo vo = memberService.memberEmailCheck(memberVo);
        System.out.println("vo = " + vo);
        if (vo != null) {
            session.setAttribute("mEmailFind",vo);
            System.out.println("이메일은 는 ============"+vo);

            return "member/memberFindResult";

        }else{
           return "redirect:/findAlert";

        }

    }


}
