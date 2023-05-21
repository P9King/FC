package com.example.fc.epRecruit.epRecruitDao;

import com.example.fc.epRecruit.epRecruitVo.EpRecruitFilesVo;
import com.example.fc.epRecruit.epRecruitVo.EpRecruitMainThumbnailVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EpRecruitMainThumbnailDao {

    //    기업 구인 메인화면 썸네일 등록
    public int epRecruitMainThumbnailSave(EpRecruitMainThumbnailVo epRecruitMainThumbnailVo);

    // 기업 구인 메인화면 썸네일 리스트
    public List<EpRecruitMainThumbnailVo> epRecruitMainThumbnailList();

}
