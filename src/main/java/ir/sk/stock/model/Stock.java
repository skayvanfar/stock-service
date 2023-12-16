package ir.sk.stock.model;

import jakarta.persistence.*;
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
import lombok.extern.slf4j.Slf4j;

/** hold the state of a stock Created by sad.kayvanfar on 12/21/2021 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TBL_STOCK")
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @NotNull
  @Pattern(regexp = "^\\w+$")
  @Column(name = "NAME", unique = true)
  private String name;

  @NotNull
  @DecimalMin(value = "0")
  @Digits(integer = 12, fraction = 2)
  @Column(name = "CURRENT_PRICE")
  private BigDecimal currentPrice;

  @Column(name = "LAST_UPDATED")
  private Instant lastUpdate;
}
