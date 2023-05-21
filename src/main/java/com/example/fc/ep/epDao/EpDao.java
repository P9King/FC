package com.example.fc.ep.epDao;

import com.example.fc.ep.epVo.EpVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EpDao {


    /* 회원가입 */
    public int epJoin(EpVo epVo);

    /* 로그인 */
    EpVo epLogin(EpVo epVo);

    // dao == crud =>  xml에서 쿼리문 작성;
    // dto == 가져온 데이터를 읽고 쓰기 => getter, setter;
    // vo == 오직 읽기전용 => setter 없음.

    int epModify(EpVo epVo);
    int epDelete(EpVo epVo);

    int idCheck(EpVo epVo) throws Exception;

    int nameCheck(EpVo epVo) throws Exception;

    EpVo epPasswordCheck(EpVo epVo);

    EpVo epEmailCheck(EpVo epVo);

    //이메일 인증
    int emailVerified(EpVo epVo);
}