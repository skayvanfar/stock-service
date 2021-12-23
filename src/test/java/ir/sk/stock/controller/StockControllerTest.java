package ir.sk.stock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sk.stock.model.Stock;
import ir.sk.stock.service.StockService;
import ir.sk.stock.service.StockServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws Exception {

        Instant first = Instant.parse("2020-12-22T14:42:09.951687Z");
        Instant second = Instant.parse("2021-12-22T14:42:09.951687Z");

        List<Stock> stocks = Arrays.asList(
                new Stock(1L, "BBT", BigDecimal.valueOf(37.55), first)
                ,new Stock(2L, "BTT", BigDecimal.valueOf(37.55), second));

        Page<Stock> pagedStocks = new PageImpl<>(stocks);

        when(stockService.getAllStocks(ArgumentMatchers.any())).thenReturn(pagedStocks);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllStocksBySize() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/stocks")
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    public void getAllStocksByContent() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/stocks")
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("BBT")))
                .andReturn();
    }


}