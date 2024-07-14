package com.study.flashcardaibackend.dto.user;

import com.study.flashcardaibackend.dto.common.SuccessResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class RegisterResponseData {
    String jwtToken;
    String email;

    public RegisterResponseData(String jwtToken, String email) {
        this.jwtToken = jwtToken;
        this.email = email;
    }
}

@Getter
@Setter
public class RegisterResponseDTO extends SuccessResponseDTO<RegisterResponseData> {
    public RegisterResponseDTO(String jwtToken, String email) {
        super(new RegisterResponseData(jwtToken, email));
    }
}
