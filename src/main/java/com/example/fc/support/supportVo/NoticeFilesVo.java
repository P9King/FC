package com.example.fc.support.supportVo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeFilesVo {
    private Long id;
    private Long noticeBoard;
    private String originalFileName;
    private String storedFileName;
    private String savePath;

    public static NoticeFilesVo toSetterFilesVo(Long noticeBoard, String originalFileName, String storedFileName, String savePath){
        NoticeFilesVo files = new NoticeFilesVo();
        files.setNoticeBoard(noticeBoard);
        files.setOriginalFileName(originalFileName);
        files.setStoredFileName(storedFileName);
        files.setSavePath(savePath);
        return files;
    }
}
