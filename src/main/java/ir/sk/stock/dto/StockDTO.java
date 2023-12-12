package ir.sk.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    private Long id;

    @NotNull
    @Pattern(regexp = "^\\w+$")
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal currentPrice;

    private Instant lastUpdate;
}
