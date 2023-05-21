package com.example.fc.memberJobHunting.memberJobHuntingservice.impl;

import com.example.fc.member.memberVo.MemberVo;
import com.example.fc.memberJobHunting.memberJobHuntingDao.MemberJobHuntingDao;
import com.example.fc.memberJobHunting.memberJobHuntingDao.MemberJobHuntingFilesDao;
import com.example.fc.memberJobHunting.memberJobHuntingDao.MemberJobHuntingThumnailDao;
import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingDto;
import com.example.fc.memberJobHunting.memberJobHuntingDto.MemberJobHuntingStackDto;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingFilesVo;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingStackVo;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingVo;
import com.example.fc.memberJobHunting.memberJobHuntingservice.MemberJobHuntingService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MemberJobHuntingServiceImpl implements MemberJobHuntingService {

    private final MemberJobHuntingDao memberJobHuntingDao;
    private final MemberJobHuntingFilesDao memberJobHuntingFilesDao;

    private final MemberJobHuntingThumnailDao memberJobHuntingThumnailDao;

    @Value("${JobHuntingContentUploadPath}")
    String savePath; //파일저장 위치

    @Value("${JobHuntingUploadThumnailPath}")
    String thumnailPath;


    //사용자 정보 불러오기 공통 메서드
    @Override
    public MemberVo memberInfo(Long memberId) {
        System.out.println("memberId ser = " + memberId);
        MemberVo info = memberJobHuntingDao.findMemberById(memberId);

        return info;
    }


    //게시글 저장
    @Override
    public int insertJobHunting(MemberJobHuntingDto jobHuntingDto, MultipartFile[] file) {
        MemberJobHuntingVo vo = MemberJobHuntingVo.dtoToVo(jobHuntingDto);
        int result = memberJobHuntingDao.insertJobHunting(vo);
        if (result == 0) return 0; //게시글 실패

        //게시글 스택 저장
        //구직 게시글에 한 컬럼에 받아온 여러 기술 값을 배열에 하나씩 삽입
        if (jobHuntingDto.getStack() != null) {
            String[] stack = jobHuntingDto.getStack().split(",");
            HashMap map = new HashMap();

            //게시글 정보로 member_board 값을 찾음 => member_stack에 값을 넣기 위해
            int member_board = memberJobHuntingDao.findJobHuntingMemberBoard(vo);
            String string_member_board = String.valueOf(member_board);

            //찾아온 member_board로 member_stack값에 저장
            for (int i = 0; i < stack.length; i++) {
                map.put("memberId", jobHuntingDto.getMemberId());
                map.put("stack", stack[i]);
                map.put("stringMemberBoard", string_member_board);
                int insertMemberStack = memberJobHuntingDao.insertMemberStack(map);
            }
            return result;// 게시글 성공

        }
        return result;
    }

    // 게시글 파일 저장
    @Override
    public JsonObject insertFile(MultipartFile multipartFile) {
        JsonObject jsonObject = new JsonObject();

        String fileRoot = savePath; //저장될 외부 파일 경로  C:\\summernote_image\\
        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));   //파일 확장자

        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);    //파일 저장
            jsonObject.addProperty("url", "/jobHuntingContent/" + savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);    //저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        System.out.println("/uploadSummernoteImageFile >>> 요청드러옹ㅁ");
        return jsonObject;

    }

    @Override
    public List<MemberJobHuntingVo> findAllJobHunting() {

        /*컨트롤러에서 정한 한 페이지에 보여줄 게시글 갯수랑 초기 페이지값을 dao로 보내쥼*/
        List<MemberJobHuntingVo> jobHuntingList = memberJobHuntingDao.findAllJobHunting();

        return jobHuntingList;
    }

    //poster기능 : 특정 게시글 보기
    @Override
    public MemberJobHuntingDto findAllByMemberBoard(MemberJobHuntingDto memberBoard) {
        MemberJobHuntingVo vo = MemberJobHuntingVo.dtoToVo(memberBoard);
        MemberJobHuntingVo result = memberJobHuntingDao.findAllByMemberBoard(vo);//작성자 모든 정보 불러오기
        MemberJobHuntingDto writerInfo = MemberJobHuntingDto.voToDto(result);

        return writerInfo;
    }

    //게시글에 해당되는 모든 파일들 찾기
    @Override
    public List<MemberJobHuntingFilesVo> findAllFilesByMemberBoard(Long memberBoard) {
        List<MemberJobHuntingFilesVo> writerFilesInfo = memberJobHuntingFilesDao.findAllFilesByMemberBoard(memberBoard);//작성자가 등록한 이미지 모두 불러오기

        return writerFilesInfo;
    }


    //게시글에 해당되는 모든 스택찾기
    @Override
    public List<MemberJobHuntingStackDto> findAllStacksByMemberBoard(MemberJobHuntingDto memberBoard) {
        MemberJobHuntingVo vo =MemberJobHuntingVo.dtoToVo(memberBoard);
        List<MemberJobHuntingStackVo> result = memberJobHuntingDao.findAllStacksByMemberBoard(vo);
        List<MemberJobHuntingStackDto> list = new ArrayList<>();
        for(MemberJobHuntingStackVo memberJobHuntingStackVo: result){
            MemberJobHuntingStackDto dto = MemberJobHuntingStackDto.voToDto(memberJobHuntingStackVo);
            list.add(dto);
        }
        return list;
    }

    //게시글 수정
    @Override
    public int updateJobHuntingBoard(MemberJobHuntingDto memberBoard , String showingDate, String showingHour, String showingMin) {
        String showingPeriod = showingDate + " " + showingHour + ":" + showingMin + ":59"; //yyyy-mm-dd hh:mm:ss 으로 저장
        memberBoard.setShowingPeriod(showingPeriod);
        MemberJobHuntingVo vo = MemberJobHuntingVo.dtoToVo(memberBoard);
        int result = memberJobHuntingDao.updateJobHuntingBoard(vo);

        return result;
    }

    //게시글 삭제
    @Override
    public int deleteMemberBoardByMemberBoard(Long memberBoard) {
        int result = memberJobHuntingDao.deleteMemberBoardByMemberBoard(memberBoard);

        return 0;
    }

}
