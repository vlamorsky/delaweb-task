package me.vlamorsky.delawebtask.data.dto.converting;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ConvertToRubResponse {

    private BigDecimal amount;
}
