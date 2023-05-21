package com.example.fc.memberJobHunting.memberJobHuntingVo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberJobHuntingThumnailVo {
    private Long id;
    private Long memberBoard;
    private String savePath;
    private String thumbnailFileName;
}

