package com.example.fc.support.supportService;

import com.example.fc.support.supportDao.MemberOneToOneDao;
import com.example.fc.support.supportVo.MemberOneToOneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberOneToOneService {

    @Autowired
    MemberOneToOneDao memberOneToOneDao;


    //    공지사항 등록
    public void mPersonalInquire(MemberOneToOneVo memberOneToOneVo) {
            memberOneToOneDao.mPersonalInquire(memberOneToOneVo);
    }
}
