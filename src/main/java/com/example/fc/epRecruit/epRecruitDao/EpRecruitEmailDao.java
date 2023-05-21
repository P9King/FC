package com.example.fc.epRecruit.epRecruitDao;

import com.example.fc.epRecruit.epRecruitVo.EpRecruitEmailVo;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingEmailVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EpRecruitEmailDao {
    //채용 추천서 보내기
    int sendEmailToMember(EpRecruitEmailVo vo);
}
