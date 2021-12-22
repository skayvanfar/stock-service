package ir.sk.stock.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by sad.kayvanfar on 12/21/2021
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TBL_STOCK")
public class Stock {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @NotNull
    @Pattern(regexp = "^\\w+$", message = "{constraints.Pattern.alphanumeric.message}")
    @Column(name = "NAME", unique = true)
    private String name;

    @NotNull
    @DecimalMin(value = "0", message = "{constraints.Digits.positive}")
    @Digits(integer = 12, fraction = 2, message = "{constraints.Digits.decimal.message}")
    @Column(name = "CURRENT_PRICE")
    private BigDecimal currentPrice;

    @Column(name = "LAST_UPDATED")
    private Instant lastUpdate;
}
