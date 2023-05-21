package com.example.fc.support.supportService;

import com.example.fc.support.supportDao.NoticeDao;
import com.example.fc.support.supportDao.NoticeFilesDao;
import com.example.fc.support.supportVo.NoticeFilesVo;
import com.example.fc.support.supportVo.NoticeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeDao noticeDao;
    private final NoticeFilesDao noticeFilesDao;


    //    공지사항 리스트
    public List<NoticeVo> noticeAllList() {
        return noticeDao.noticeAllList();
    }

    //  공지사항 이미지리스트
    public List<NoticeFilesVo> noticeFilesList(int noticeBoard) {
        return noticeFilesDao.noticeFilesList(noticeBoard);
    }

    //    공지사항 등록, TIMESTAMP 를 사용함으로써 게시글 저장 날짜 자동 생성(now()사용 x)
    public int noticeInsert(NoticeVo noticeVo) throws IOException {
        int result = noticeDao.noticeInsert(noticeVo); // 게시글 등록
        int noticeBoard = noticeDao.findNoticeBoard(noticeVo);
        return 0;
    }

    /*   System.out.println("서비스 게시판 저장 result = " + result);*/

    //    공지사항 수정
    public void noticeModify(NoticeVo noticeVo) {
        noticeDao.noticeModify(noticeVo);
    }

    //    공지사항 상세 조회
    public NoticeVo noticeOneList(int notice_board) {
        return noticeDao.noticeOneList(notice_board);
    }

    //    공지사항 삭제
    public void noticeDelete(int notice_board) {
        noticeDao.noticeDelete(notice_board);
    }
}
