package com.example.fc.support.supportDao;


import com.example.fc.support.supportVo.MemberOneToOneVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberOneToOneDao {

    //    멤버 일대일문의 등록
    int mPersonalInquire(MemberOneToOneVo memberOneToOneVo);
    //    공지사항 수정

}
