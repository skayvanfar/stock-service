package ir.sk.stock;

import ir.sk.stock.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by sad.kayvanfar on 12/21/2021
 */
@SpringBootApplication
@Import(SwaggerConfig.class)
public class StockApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }
}
