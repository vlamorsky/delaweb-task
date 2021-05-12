package me.vlamorsky.delawebtask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.vlamorsky.delawebtask.data.dto.authorization.SignInRequest;
import me.vlamorsky.delawebtask.data.dto.authorization.SignInResponse;
import me.vlamorsky.delawebtask.component.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public SignInResponse signIn(SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        return new SignInResponse(
                jwtProvider.generateJwtToken(authentication),
                "Bearer");
    }
}
