package ir.sk.stock.controller;

import ir.sk.stock.StockApplication;
import ir.sk.stock.dto.PriceUpdateDTO;
import ir.sk.stock.model.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by sad.kayvanfar on 12/21/2021
 */
@SpringBootTest(classes = StockApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/stocks");
    }

    @Test
    public void contextLoads() {
    }

    @Test
    @Sql(statements = "INSERT INTO TBL_STOCK (ID, NAME, CURRENT_PRICE, LAST_UPDATED) VALUES (10, 'LCC', 15.64, '2018-12-22T14:42:09.951687Z')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TBL_STOCK",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void returnAStockWithIdOne() {
        String expectedName = "LCC";
        Stock stock = restTemplate.getForObject(baseUrl.concat("/{id}"), Stock.class, 10);
        assertAll(
                () -> Assertions.assertNotNull(stock),
                () -> Assertions.assertEquals(expectedName, stock.getName())
        );
    }

    @Test
    public void getStockReturn404() {
        ResponseEntity<String> err = restTemplate.getForEntity
                (baseUrl.concat("/{id}"), String.class, 100);

        assertAll(
                () -> assertNotNull(err),
                () -> assertEquals(HttpStatus.NOT_FOUND, err.getStatusCode())//,
              //  () -> assertNotNull(err.getBody())
        );
    }

    @Test
    public void createStockAndReturn201HttpStatus() {
        Stock stock = new Stock(10L, "CLC", BigDecimal.valueOf(15.64), Instant.parse("2018-12-22T14:42:09.951687Z"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Stock> postRequest = new HttpEntity<>(stock, headers);
        Stock savedStock = restTemplate.postForEntity(baseUrl, postRequest, Stock.class).getBody();

        assertNotNull(savedStock);

        Stock newStock = restTemplate.getForObject(baseUrl.concat("/{id}"), Stock.class, savedStock.getId());
        assertAll(
                () -> assertNotNull(newStock),
                () -> assertEquals(stock.getName(), newStock.getName()),
                () -> assertEquals(stock.getCurrentPrice(), newStock.getCurrentPrice())
        );
    }


    @Test
    @Sql(statements = "INSERT INTO TBL_STOCK (ID, NAME, CURRENT_PRICE, LAST_UPDATED) VALUES (10, 'CCC', 15.64, '2018-12-22T14:42:09.951687Z')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TBL_STOCK",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateStockAndReturn200HttpStatus() {
        // this line is needed for patch method on RestTemplate
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        PriceUpdateDTO priceUpdateDTO = new PriceUpdateDTO(BigDecimal.valueOf(15.64));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BigDecimal> postRequest = new HttpEntity<>(priceUpdateDTO.getCurrentPrice(), headers);
        Stock updatedStock = restTemplate.patchForObject(baseUrl.concat("/{id}"), postRequest, Stock.class, 10);


        assertNotNull(updatedStock);

        Stock newStock = restTemplate.getForObject(baseUrl.concat("/{id}"), Stock.class, updatedStock.getId());
        assertAll(
                () -> assertNotNull(newStock),
                () -> assertEquals(priceUpdateDTO.getCurrentPrice(), newStock.getCurrentPrice())
        );
    }

    @Test
    @Sql(statements = "INSERT INTO TBL_STOCK (ID, NAME, CURRENT_PRICE, LAST_UPDATED) VALUES (10, 'CHC', 15.64, '2018-12-22T14:42:09.951687Z')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TBL_STOCK",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetAllStocks() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> result = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);

        assertNotNull(result.getBody());

        assertEquals(true, result.getBody().contains("content"));
        System.out.println(result.getBody());
        assertEquals(true, result.getBody().contains("\"totalElements\":1"));
    }

    @Test
    @Sql(statements = "INSERT INTO TBL_STOCK (ID, NAME, CURRENT_PRICE, LAST_UPDATED) VALUES (10, 'CHC', 15.64, '2018-12-22T14:42:09.951687Z')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TBL_STOCK",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteStockAndReturn200HttpStatus() {
        restTemplate.delete(baseUrl.concat("/{id}"), 10);

        Stock newStock = restTemplate.getForObject(baseUrl.concat("/{id}"), Stock.class, 10);
      //  assertNull(newStock);
    }
}
