package com.example.fc.epRecruit.epRecruitVo;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpRecruitStackVO {
    private Long id;
    private Long epBoard;
    private Long epId;
    private String stack;
    private String createDate;
    private String updateDate;

}
