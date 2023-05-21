package com.example.fc.memberJobHunting.memberJobHuntingDao;

import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingFilesVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberJobHuntingFilesDao {

    //게시글에서 파일 저장
    int insertJobHuntingFiles(MemberJobHuntingFilesVo memberJobHuntingFilesVo);

    //poster 기능 => 작성자의 모든 이미지 정보 불러옴
    List<MemberJobHuntingFilesVo> findAllFilesByMemberBoard(Long memberBoard);
}
