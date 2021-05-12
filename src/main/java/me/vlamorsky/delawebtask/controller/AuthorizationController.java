package me.vlamorsky.delawebtask.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.vlamorsky.delawebtask.data.dto.authorization.SignInRequest;
import me.vlamorsky.delawebtask.data.dto.authorization.SignInResponse;
import me.vlamorsky.delawebtask.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/authorization")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {

        log.info("POST: /api/authorization/sign-in {}", signInRequest);

        return new ResponseEntity<>(authorizationService.signIn(signInRequest), HttpStatus.OK);
    }
}
