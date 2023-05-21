package com.example.fc.email.dto;


import com.example.fc.email.model.EmailVerification;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class EmailVerificationDto {

    private Long id;
    private String verifyingCode;
    private int verified;
    private String email;
    private String password;
    public static EmailVerificationDto modelToDto(EmailVerification model){
        EmailVerificationDto dto = new EmailVerificationDto();
        dto.setId(model.getId());
        dto.setVerifyingCode(model.getVerifyingCode());
        dto.setVerified(model.getVerified());
        dto.setEmail(model.getEmail());
        dto.setPassword(model.getPassword());

        return dto;

    }
}
