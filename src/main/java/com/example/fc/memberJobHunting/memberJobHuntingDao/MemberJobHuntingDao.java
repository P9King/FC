package com.example.fc.memberJobHunting.memberJobHuntingDao;

import com.example.fc.member.memberVo.MemberVo;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingStackVo;
import com.example.fc.memberJobHunting.memberJobHuntingVo.MemberJobHuntingVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MemberJobHuntingDao {


   //공통으로 쓸 수 있는 사용자 정보 찾기 메서드
   MemberVo findMemberById(Long memberId);

   //구직 게시글 작성(insert)
   public int insertJobHunting(MemberJobHuntingVo memberJobHuntingVo);

   // 생성된 게시글 번호 찾기
   public int findJobHuntingMemberBoard(MemberJobHuntingVo jobHunt);

   // 생선된 게시글 번호로 구직자 stack(기술) 작성(insert)
   public int insertMemberStack(HashMap map);

   //모든 게시글 => 페이징을 위해서
   public List<MemberJobHuntingVo> findAllJobHunting();

   //poster기능 => 특정 게시글 보기
   MemberJobHuntingVo findAllByMemberBoard(MemberJobHuntingVo memberBoard);

   //게시글에 해당 되는 모든 스택 찾기
   List<MemberJobHuntingStackVo> findAllStacksByMemberBoard(MemberJobHuntingVo memberBoard);

   //게시글 업데이트
   int updateJobHuntingBoard(MemberJobHuntingVo memberBoard);
   
   //게시글 삭제
   int deleteMemberBoardByMemberBoard(Long memberBoard);
}
