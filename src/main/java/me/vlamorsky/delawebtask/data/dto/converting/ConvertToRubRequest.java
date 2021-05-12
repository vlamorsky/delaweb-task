package me.vlamorsky.delawebtask.data.dto.converting;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class ConvertToRubRequest {

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[A-Z]{3}$")
    private String currency;

    @NotNull
    private BigDecimal amount;
}
