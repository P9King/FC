package com.example.fc.epRecruit.epRecruitVo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EpRecruitLeftJoinMainThumbnailVO {
  private Long epBoard;
  private String title;
  private String stack;
  private String showingPeriod;
  private String recruit;
  private String gender;
  private String payment;
  private String workDayStart;
  private String workDayEnd;
  private String addr;
  private String detail;
  private String createDate;
  private String updateDate;
  private String fileAttached;
  private Long id;
  private Long epRecruitBoard;
  private String savePath;
  private String thumbnailFileName;
}
