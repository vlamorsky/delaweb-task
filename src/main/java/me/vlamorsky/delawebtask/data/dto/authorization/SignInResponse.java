package me.vlamorsky.delawebtask.data.dto.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResponse {

    private String token;
    private String authorizationType;
}
