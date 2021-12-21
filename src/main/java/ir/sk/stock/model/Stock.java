package ir.sk.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by sad.kayvanfar on 12/21/2021
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    private Long id;

    private String name;

    private BigDecimal currentPrice;

    private Instant lastUpdate;
}
