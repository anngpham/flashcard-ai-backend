package com.study.flashcardaibackend.dto.user;

import com.study.flashcardaibackend.dto.common.SuccessResponseDTO;
import com.study.flashcardaibackend.model.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class LoginResponseData {
    private User user;
    private String jwtToken;

    public LoginResponseData(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }
}

@Getter
@Setter
public class LoginResponseDTO extends SuccessResponseDTO<LoginResponseData> {
    public LoginResponseDTO(User user, String jwtToken) {
        super(new LoginResponseData(user, jwtToken));
    }
}
