package com.example.fc.support.supportVo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class NoticeVo {

    private Long noticeBoard;
    private String title;
    private String detail;
    private String sortation;
    private String createDate;
    private int fileAttached; //파일 첨부상태 : 0 미첨부, 1 첨부



}