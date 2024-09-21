package ir.sk.stock.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.Instant;

public record StockDTO(
    Long id,
    @NotNull @Pattern(regexp = "^\\w+$") String name,
    @NotNull @DecimalMin(value = "0") @Digits(integer = 12, fraction = 2) BigDecimal currentPrice,
    Instant lastUpdate) {}
