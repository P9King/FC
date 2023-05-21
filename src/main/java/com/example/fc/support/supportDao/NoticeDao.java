package com.example.fc.support.supportDao;


import com.example.fc.support.supportVo.NoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDao {
    //    공지사항 리스트
    List<NoticeVo> noticeAllList();

    //    공지사항 등록
    int noticeInsert(NoticeVo noticeVo);

    //    공지사항 수정
    int noticeModify(NoticeVo noticeVo);

    // 생성된 공지사항 게시글 번호 찾기
    public int findNoticeBoard(NoticeVo noticeVo);

    //게시판 상세 조회
    NoticeVo noticeOneList(int notice_board);

    //    게시판 삭제
    int noticeDelete(int notice_board);

}
