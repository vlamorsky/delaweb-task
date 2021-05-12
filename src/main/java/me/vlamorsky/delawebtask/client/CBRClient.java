package me.vlamorsky.delawebtask.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.vlamorsky.delawebtask.data.dto.cbr.CBRDailyRubRateResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CBRClient {

    private final RestTemplate restTemplate;

    public Optional<CBRDailyRubRateResponse> fetchCBRDailyRubRate() {

        CBRDailyRubRateResponse cbrDailyRubRateResponse = null;

        try {

            cbrDailyRubRateResponse = restTemplate.exchange(
                    "https://www.cbr-xml-daily.ru/latest.js",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CBRDailyRubRateResponse>() {})
                    .getBody();

            log.info("Fetched RUB rate: {}", cbrDailyRubRateResponse);

        } catch (RestClientException exception) {
            log.error("CBR daily data fetching error:", exception);
        }

        return Optional.ofNullable(cbrDailyRubRateResponse);
    }
}
