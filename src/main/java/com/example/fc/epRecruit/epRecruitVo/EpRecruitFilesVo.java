package com.example.fc.epRecruit.epRecruitVo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EpRecruitFilesVo {
  private Long id;
  private Long epRecruitBoard;
  private String originalFileName;
  private String storedFileName;
  private String createTime;
  private String savePath;
  private String thumbnailFileName;
  private String thumbnailSavePath;
}
