package com.example.fc.memberJobHunting.memberJobHuntingDao;

import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingEmailVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberJobHuntingEmailDao {

    public int sendEmailToEp(MemberJobHuntingEmailVo vo);
}
