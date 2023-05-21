package com.example.fc.support.supportDao;


import com.example.fc.support.supportVo.NoticeFilesVo;
import com.example.fc.support.supportVo.NoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeFilesDao {

    int insertNoticeFiles(NoticeFilesVo noticeFilesVo);

    List<NoticeFilesVo> noticeFilesList(int noticeBoard);

}
