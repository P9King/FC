package com.example.fc.pageNation.pageVo;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.Criteria;

@Getter
@ToString
// 도움: https://congsong.tistory.com/26
public class PageNationVo {
  // 현재 페이지 번호
  private int page;

//  페이지마다 출력할 데이터 개수
  private int recordSize;

//  화면 하단에 출력할 페이지의 크기
  private int pageSize;

//  검색할 문자
  private String keyword;

//  keyword 와 함께 사용
//  게시글의 제목, 내용, 작성자 중 하나 또는 전체로 like 검색
  private String searchType;

  public PageNationVo() {
   this.page = 1;
   this.recordSize = 6;
   this.pageSize = 10;

  }

//  Mysql에서 LIMIT 구문의 시작 부분에 사용되는 메서드
  public int getOffset() {
    return (page - 1) * recordSize;
  }
}

