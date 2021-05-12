package me.vlamorsky.delawebtask.component;

import lombok.extern.slf4j.Slf4j;
import me.vlamorsky.delawebtask.client.CBRClient;
import me.vlamorsky.delawebtask.data.dto.converting.ConvertToRubRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@EnableScheduling
public class RubConverter {

    private final CBRClient cbrClient;
    private Map<String, BigDecimal> rubRateData;

    public RubConverter(CBRClient cbrClient) {
        this.cbrClient = cbrClient;
        rubRateData = new HashMap<>(64);
    }

    @Scheduled(fixedRate = 2 * 60 * 60 * 1000)
    public void updateRubRateData() {
        cbrClient.fetchCBRDailyRubRate()
                .ifPresent(cbrDailyRubRateResponse -> rubRateData = cbrDailyRubRateResponse.getRates());
    }

    public BigDecimal convertToRub(ConvertToRubRequest convertToRubRequest) {

        if (rubRateData.containsKey(convertToRubRequest.getCurrency())) {
            return convertToRubRequest.getAmount()
                    .setScale(6, RoundingMode.DOWN)
                    .divide(rubRateData.get(convertToRubRequest.getCurrency()), RoundingMode.DOWN);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
