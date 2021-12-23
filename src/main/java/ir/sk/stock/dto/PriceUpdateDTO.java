package ir.sk.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by sad.kayvanfar on 12/22/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceUpdateDTO {
    @NotNull
    @DecimalMin(value = "0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal currentPrice;
}
