package com.example.fc.memberJobHunting.memberJobHuntingservice;

import com.example.fc.member.memberVo.MemberVo;
import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingDto;
import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingStackDto;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingFilesVo;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingVo;
import com.google.gson.JsonObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberJobHuntingService {


    //사용자 정보 불러오기 공통 메서드
    MemberVo memberInfo(Long memberId);

    //게시글 저장
    int insertJobHunting(MemberJobHuntingDto jobHuntingDto, MultipartFile[] file);

    // 게시글 파일 저장
    JsonObject insertFile(MultipartFile multipartFile);

    List<MemberJobHuntingVo> findAllJobHunting();

    //poster기능 : 특정 게시글 보기
    MemberJobHuntingDto findAllByMemberBoard(MemberJobHuntingDto memberBoard);

    //게시글에 해당되는 모든 파일들 찾기
    List<MemberJobHuntingFilesVo> findAllFilesByMemberBoard(Long memberBoard);

    //게시글에 해당되는 모든 스택찾기
    List<MemberJobHuntingStackDto> findAllStacksByMemberBoard(MemberJobHuntingDto memberBoard);

    //게시글 수정
    int updateJobHuntingBoard(MemberJobHuntingDto memberBoard, String showingDate, String showingHour, String showingMin);

    //게시글 삭제
    int deleteMemberBoardByMemberBoard(Long memberBoard);
}
