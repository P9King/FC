package com.example.fc.epRecruit.epRecruitDao;

import com.example.fc.epRecruit.epRecruitVo.EpRecruitStackVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EpRecruitStackDao {

//    기업 구인 기술 스택들 등록
    public int epRecruitStackSave(EpRecruitStackVO epRecruitStackVO);

//    기업 구인 게시판_아이디 스택들 리스트
    public List<EpRecruitStackVO> findEpRecruitStacksByBoard(Long epBoard);

//    게시판개별삭제(스택들)
    public int epRecruitStackDeleteById(Long epBoard);



}
