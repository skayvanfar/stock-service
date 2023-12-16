package ir.sk.stock.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
