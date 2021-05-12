package me.vlamorsky.delawebtask.data.dto.cbr;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CBRDailyRubRateResponse {

    private String date;
    private Long timestamp;
    private String base;

    private Map<String, BigDecimal> rates;
}
