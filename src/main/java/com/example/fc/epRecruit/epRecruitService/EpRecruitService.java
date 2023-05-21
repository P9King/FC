package com.example.fc.epRecruit.epRecruitService;

import com.example.fc.ep.epVo.EpVo;
import com.example.fc.epRecruit.epRecruitDao.EpRecruitDao;
import com.example.fc.epRecruit.epRecruitDao.EpRecruitFilesDao;
import com.example.fc.epRecruit.epRecruitDao.EpRecruitMainThumbnailDao;
import com.example.fc.epRecruit.epRecruitDao.EpRecruitStackDao;
import com.example.fc.epRecruit.epRecruitVo.*;
import com.example.fc.epRecruit.epRecruitVo.EpRecruitFilesVo;
import com.example.fc.epRecruit.epRecruitVo.EpRecruitLeftJoinMainThumbnailVO;
import com.example.fc.epRecruit.epRecruitVo.EpRecruitMainThumbnailVo;
import com.example.fc.epRecruit.epRecruitVo.EpRecruitVO;
import com.example.fc.member.memberVo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class EpRecruitService {

    @Value("${epRecruitUploadPath}")
    private String epRecruitPath;
    @Value("${epRecruitThumbnailUploadPath}")
    private String epRecruitThumbnailPath;
    @Value("${epRecruitMainThumbnailUploadPath}")
    private String epRecruitMainThumbnailPath;
    private final EpRecruitDao epRecruitDao;
    private final EpRecruitStackDao epRecruitStackDao;
    private final EpRecruitFilesDao epRecruitFilesDao;
    private final EpRecruitMainThumbnailDao epRecruitMainThumbnailDao;

    public int epRecruitSave(EpRecruitVO epRecruitVO, String showingDate, String showingHour, String showingMin, MultipartFile[] files, HttpSession session) throws IOException {
        EpVo epVo = (EpVo) session.getAttribute("epLogin");
        System.out.println("session >>" + epVo);

        String showingPeriod = showingDate + " " + showingHour + ":" + showingMin + ":59"; //yyyy-mm-dd hh:mm:ss 으로 저장
        epRecruitVO.setShowingPeriod(showingPeriod);

        epRecruitVO.setEpId(epVo.getEpId());

        if (epRecruitVO.getEpId() == null) {
            epRecruitVO.setEpId(-1L);
        }

        if (epRecruitVO.getPayment().equals("0,추후협상")) {
            epRecruitVO.setPayment("추후협상");
        }

        //파일이 미첨부시 바로 저장
        if (files[0].isEmpty()) {
            epRecruitVO.setFileAttached("0");
            int result = epRecruitDao.epRecruitSave(epRecruitVO);

            if (epRecruitVO.getStack() != null) {
                //          스택테이블에 저장
                String[] stackLst = epRecruitVO.getStack().split(",");
//          스택 길이만큼 스택 테이블에 저장
                for (String stack : stackLst) {
//              필요한것: 현재 로그인한 id, 게시글 번호, 스택
                    Long epId = epVo.getEpId();
                    Long epBoard = epRecruitDao.epRecruitLastId();
                    EpRecruitStackVO epRecruitStackVO = EpRecruitStackVO.builder().epId(epId).epBoard(epBoard).stack(stack).build();
                    int res = epRecruitStackDao.epRecruitStackSave(epRecruitStackVO);
                    System.out.println("StackSave>" + stack + ">>>" + res);
                }
            }


//            나도 만약 기술을 저장할때 ',' 구분자말고 ' ' 로 구분할때 제웅이형이 만든 코드 참고하기

            /*member_stack테이블에 stack 저장*/
//            String[] stack = jobHunt.getStack().split(" "); //공백을 기준으로 스택을 나눔
//            HashMap map = new HashMap();
            //게시글 정보로 member_board 값을 찾음 => member_stack에 값을 넣기 위해
//            int memberBoard = memberJobHuntingDao.findJobHuntingMemberBoard(jobHunt);
//            String string_member_board = String.valueOf(memberBoard);
            //찾아온 member_board로 member_stack값에 저장
//            for (int i = 0; i < stack.length; i++) {
//                map.put("stack", stack[i]);
//                map.put("string_member_board", string_member_board);
//                int insertMemberStack = memberJobHuntingDao.insertMemberStack(map);
//            }
            return 1;
            // 파일 첨부시 저장
        } else {
            epRecruitVO.setFileAttached("1");
            int result = epRecruitDao.epRecruitSave(epRecruitVO);

            Long lastEpBoard = -1L;

            File imageFile = new File(epRecruitPath);

            if (result == 1) {
                lastEpBoard = epRecruitDao.epRecruitLastId();
            }

            if (epRecruitVO.getStack() != null) {
//                  스택테이블에 저장
                String[] stackLst = epRecruitVO.getStack().split(",");
//                  스택 길이만큼 스택 테이블에 저장
                for (String stack : stackLst) {
//                  필요한것: 현재 로그인한 id, 게시글 번호, 스택
                    Long epId = epVo.getEpId();
                    Long epBoard = epRecruitDao.epRecruitLastId();
                    EpRecruitStackVO epRecruitStackVO = EpRecruitStackVO.builder().epId(epId).epBoard(epBoard).stack(stack).build();
                    int res = epRecruitStackDao.epRecruitStackSave(epRecruitStackVO);
                    System.out.println("StackSave>" + stack + ">>>" + res);
                }
            }

            // 모든 사진들 업로드 및 썸네일들 생성
            for (MultipartFile file : files) {
                UUID uuid = UUID.randomUUID(); // 랜덤 식별자 생성

                String originalFileName = file.getOriginalFilename(); // 원본 파일명
                String storedFileName = uuid + "_" + originalFileName; // 중복 방지를 위한 바뀐 파일명

                // 파일 객체에 정보담고
                imageFile = new File(epRecruitPath, storedFileName);
                // 파일 이동
                file.transferTo(imageFile);//파일이동

                // 파일 이름 생성
                String thumbnailName = "s_" + storedFileName;
                // 파일 경로 + 이름
                File thumbnailFile = new File(epRecruitThumbnailPath, "s_" + storedFileName);

                // BufferedImage에 ImageIo를 이용하여 saveFile 객체를 담음
                BufferedImage bo_image = ImageIO.read(imageFile);
                // 비어있는 이미지 생성
                BufferedImage bt_image = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);

                // 오지리날 이미지를 썸네일 이미지로 변환
                Graphics2D graphic = bt_image.createGraphics();
                graphic.drawImage(bo_image, 0, 0, 100, 100, null);

                // 저장 :: 1. 만든 썸네일 이미지 2. 사진 형식 3. File
                ImageIO.write(bt_image, "png", thumbnailFile);

                EpRecruitFilesVo epRecruitFilesVo = EpRecruitFilesVo.builder().epRecruitBoard(lastEpBoard)
                        .originalFileName(originalFileName).storedFileName(storedFileName)
                        .savePath(epRecruitPath).thumbnailFileName(thumbnailName).build();

                epRecruitFilesDao.epRecruitFilesSave(epRecruitFilesVo);
            }
            // 메인 썸네일만 생성
            UUID uuid = UUID.randomUUID(); // 랜덤 식별자 생성

            String originalFileName = files[0].getOriginalFilename(); // 원본 파일명
            String thumbnailName = "s_" + uuid + "_" + originalFileName; // 썸네일 명
            // 파일 경로 + 이름
            File mainThumbnailFile = new File(epRecruitMainThumbnailPath, thumbnailName);
            // BufferedImage에 ImageIo를 이용하여 saveFile 객체를 담음
            BufferedImage bo_image = ImageIO.read(imageFile);
            // 비어있는 이미지 생성
            BufferedImage bt_image = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);

            // 오지리날 이미지를 썸네일 이미지로 변환
            Graphics2D graphic = bt_image.createGraphics();
            graphic.drawImage(bo_image, 0, 0, 100, 100, null);

            // 저장 :: 1. 만든 썸네일 이미지 2. 사진 형식 3. 파일 객체
            ImageIO.write(bt_image, "png", mainThumbnailFile);

            // 빌더 생성
            EpRecruitMainThumbnailVo epRecruitMainThumbnailVo = EpRecruitMainThumbnailVo.builder().epRecruitBoard(lastEpBoard)
                    .savePath(epRecruitMainThumbnailPath).thumbnailFileName(thumbnailName).build();

            // 저장
            int res = epRecruitMainThumbnailDao.epRecruitMainThumbnailSave(epRecruitMainThumbnailVo);
            System.out.println("res>>>" + res);
            return 2;
        }
    }

    //    기업게시판메인화면썸네일리스트
    public List<EpRecruitLeftJoinMainThumbnailVO> epRecruitMainList() {
        return epRecruitDao.epRecruitMainList();
    }

    public List<EpRecruitVO> epFindByStackAndTitleList(String stack, String title) {
        List<EpRecruitVO> lst = epRecruitDao.epFindByStackAndTitleList(stack, title);
        return lst;
    }

    public List<EpRecruitVO> epFindByTitleList(String title) {
        List<EpRecruitVO> lst = epRecruitDao.epFindByTitleList(title);
        return lst;
    }

    public List<EpRecruitFilesVo> findEpRecruitFilesAll() {
        return epRecruitFilesDao.findEpRecruitFilesAll();
    }

    public List<EpRecruitVO> epRecruitList() {
        return epRecruitDao.epList();
    }

    public EpRecruitVO epRecruitFindOne(Long epBoard) {
        return epRecruitDao.epRecruitFindById(epBoard);
    }

    public HashMap<String, Object> epNameFindByEpBoard(Long epBoard) {
        return epRecruitDao.epNameFindByEpBoard(epBoard);
    }

    public List<EpRecruitStackVO> epRecruitStacksByBoard(Long epBoard) {
        return epRecruitStackDao.findEpRecruitStacksByBoard(epBoard);
    }

    public Long epRecruitLastId() {
        return epRecruitDao.epRecruitLastId();
    }

    //    게시판 개별삭제
    public void epRecruitDeleteById(Long epBoard) {
        int epRecruitDeleteByIdRes = epRecruitDao.epRecruitDeleteById(epBoard);
        int epRecruitStackDeleteByIdRes = epRecruitStackDao.epRecruitStackDeleteById(epBoard);
    }

    public void epRecruitUpdate(EpRecruitVO epRecruitVO, String showingDate, String showingHour, String showingMin, HttpSession session, Long epBoard) {
//        epRecruit 수정
//        epRecruit 스택들 수정
//        -- 순서 --
//        session에서 ID가져오고 epRecruit에 담기
        EpVo epVo = (EpVo) session.getAttribute("epLogin");
        Long epId = epVo.getEpId();
        epRecruitVO.setEpId(epId);

//        중복부분 수정
        if (epRecruitVO.getPayment().equals("0,추후협상")) epRecruitVO.setPayment("추후협상");

//        게시기간 수정
        String showingPeriod = showingDate + " " + showingHour + ":" + showingMin + ":59"; //yyyy-mm-dd hh:mm:ss 으로 저장
        epRecruitVO.setShowingPeriod(showingPeriod);

//        epRecruit 업데이트 쿼리 수행
        int epRecruitUpdateByIdRes = epRecruitDao.epRecruitUpdateById(epRecruitVO);

//        epRecruitStack 삭제쿼리 수행
        int epRecruitStackDeleteByIdRes = epRecruitStackDao.epRecruitStackDeleteById(epBoard);

        if (epRecruitVO.getStack() != null) {
//        스택테이블에 저장
            String[] stackLst = epRecruitVO.getStack().split(",");
//        스택 길이만큼 스택 테이블에 저장
            for (String stack : stackLst) {
//            필요한것: 현재 로그인한 id, 게시글 번호, 스택
//            stack에 boardId와 데이터 입력하기
                EpRecruitStackVO epRecruitStackVO = EpRecruitStackVO.builder().epId(epId).epBoard(epBoard).stack(stack).build();
                int res = epRecruitStackDao.epRecruitStackSave(epRecruitStackVO);
            }
        }

    }

    //글쓴이 정보
    public EpVo epInfo(Long epId) {
        System.out.println("memberId ser = " + epId);
        EpVo info = epRecruitDao.findEpById(epId);
        return info;
    }
}
