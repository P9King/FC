package com.example.fc.support.supportDao;


import com.example.fc.support.supportVo.EpOneToOneVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EpOneToOneDao {

    //    회사 일대일 등록
    public int epPersonalInquire(EpOneToOneVo epOneToOneVo);
    
    //내가 문의한 1대1문의 숫자 조회
    public  int epOneToOneCount(String epOneToOneVo);
    //email로 1대1 문의 검색하기
    public  List<EpOneToOneVo> epOneToOneFindEmail(String epOneToOneVo);
    EpOneToOneVo oneToOneList(int id);
    
    //1대1 수정
    int oneToOneModify(EpOneToOneVo epOneToOneVo);

    //1대1 삭제
    int epOneToOneDelete(EpOneToOneVo epOneToOneVo);


}
