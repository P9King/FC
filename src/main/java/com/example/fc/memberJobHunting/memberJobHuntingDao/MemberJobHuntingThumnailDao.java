package com.example.fc.memberJobHunting.memberJobHuntingDao;

import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingThumnailVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberJobHuntingThumnailDao {

    public int insertJobHuntingThumnailVo(MemberJobHuntingThumnailVo thumbnails); //섬네일 저장
}
