package me.vlamorsky.delawebtask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.vlamorsky.delawebtask.data.dto.converting.ConvertToRubRequest;
import me.vlamorsky.delawebtask.data.dto.converting.ConvertToRubResponse;
import me.vlamorsky.delawebtask.component.RubConverter;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvertingService {

    private final RubConverter rubConverter;

    public ConvertToRubResponse convertToRub(ConvertToRubRequest convertToRubRequest) {
        return new ConvertToRubResponse(rubConverter.convertToRub(convertToRubRequest));
    }
}
