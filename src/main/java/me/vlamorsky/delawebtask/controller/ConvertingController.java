package me.vlamorsky.delawebtask.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.vlamorsky.delawebtask.component.RequestLimiter;
import me.vlamorsky.delawebtask.data.dto.converting.ConvertToRubRequest;
import me.vlamorsky.delawebtask.data.dto.converting.ConvertToRubResponse;
import me.vlamorsky.delawebtask.service.ConvertingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/converting")
@RequiredArgsConstructor
public class ConvertingController {

    private final ConvertingService convertingService;
    private final RequestLimiter requestLimiter;

    @PostMapping("/convert-to-rub")
    public ResponseEntity<ConvertToRubResponse> convertToRub(@Valid @RequestBody ConvertToRubRequest convertToRubRequest,
                                                             UsernamePasswordAuthenticationToken authenticationToken) {

        log.info("POST: /api/converting/convert-to-rub {}", convertToRubRequest);

        requestLimiter.process(authenticationToken);

        return new ResponseEntity<>(convertingService.convertToRub(convertToRubRequest), HttpStatus.OK);
    }
}
