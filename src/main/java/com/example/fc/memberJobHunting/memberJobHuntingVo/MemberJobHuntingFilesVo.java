package com.example.fc.memberJobHunting.memberJobHuntingVo;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberJobHuntingFilesVo {
        private Long id;
        private Long memberBoard;
        private String originalFileName;
        private String storedFileName;
        private String savePath;

        public static MemberJobHuntingFilesVo toSetterFilesVo(Long memberBoard, String originalFileName, String storedFileName, String savePath){
                MemberJobHuntingFilesVo files = new MemberJobHuntingFilesVo();
                files.setMemberBoard(memberBoard);
                files.setOriginalFileName(originalFileName);
                files.setStoredFileName(storedFileName);
                files.setSavePath(savePath);
                return files;
        }
}
