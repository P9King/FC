package com.example.fc.email.model;

import com.example.fc.email.dto.EmailVerificationDto;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class EmailVerification {
    private Long id;
    private String verifyingCode;
    private int verified;
    private String email;
    private String password;

    public static EmailVerification dtoToModel(EmailVerificationDto dto){
        EmailVerification model = new EmailVerification();
        model.setId(dto.getId());
        model.setVerifyingCode(dto.getVerifyingCode());
        model.setVerified(dto.getVerified());
        model.setEmail(dto.getEmail());
        model.setPassword(dto.getPassword());

        return model;
    }
}
