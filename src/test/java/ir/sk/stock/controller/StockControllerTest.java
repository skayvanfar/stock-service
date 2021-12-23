package ir.sk.stock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sk.stock.dto.PriceUpdateDTO;
import ir.sk.stock.model.Stock;
import ir.sk.stock.service.StockService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl = "/api/stocks";
    private List<Stock> stocks;

    @BeforeEach
    public void setUp() {

        Instant first = Instant.parse("2020-12-22T14:42:09.951687Z");
        Instant second = Instant.parse("2021-12-22T14:42:09.951687Z");

        stocks = Arrays.asList(
                new Stock(1L, "BBT", BigDecimal.valueOf(37.55), first)
                , new Stock(2L, "BTT", BigDecimal.valueOf(37.55), second));
    }

    @Test
    public void getAllStocksBySize() throws Exception {
        Page<Stock> pagedStocks = new PageImpl<>(stocks);
        when(stockService.getAllStocks(ArgumentMatchers.any())).thenReturn(pagedStocks);

        RequestBuilder request = MockMvcRequestBuilders
                .get(baseUrl)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    public void getAllStocksByContent() throws Exception {
        Page<Stock> pagedStocks = new PageImpl<>(stocks);
        when(stockService.getAllStocks(ArgumentMatchers.any())).thenReturn(pagedStocks);
        RequestBuilder request = MockMvcRequestBuilders
                .get(baseUrl)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("BBT")))
                .andReturn();
    }

    @Test
    public void createStockAndReturn201HttpStatus() throws Exception {
        Stock stock = new Stock(10L, "BBT", BigDecimal.valueOf(37.55), Instant.parse("2020-12-22T14:42:09.951687Z"));

        when(stockService.save(stock)).thenReturn(stock);

        RequestBuilder postRequest = MockMvcRequestBuilders
                .post(baseUrl)
                .content(objectMapper.writeValueAsString(stock))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    public void updateStockAndReturn200HttpStatus() throws Exception {
        PriceUpdateDTO priceUpdateDTO = new PriceUpdateDTO(BigDecimal.valueOf(37.55));
        when(stockService.findById(ArgumentMatchers.any())).thenReturn(java.util.Optional.ofNullable(stocks.get(1)));
        when(stockService.save(ArgumentMatchers.any())).thenReturn(stocks.get(1));


        RequestBuilder patchRequest = MockMvcRequestBuilders
                .patch(baseUrl.concat("/1"))
                .content(objectMapper.writeValueAsString(priceUpdateDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(patchRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPrice").value(37.55))
                .andReturn();
    }

    @Test
    public void deleteStockAndReturn200HttpStatus() throws Exception {
        when(stockService.findById(ArgumentMatchers.any())).thenReturn(java.util.Optional.ofNullable(stocks.get(1)));

        RequestBuilder deleteRequest = MockMvcRequestBuilders
                .delete(baseUrl.concat("/1"))
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(deleteRequest)
                .andExpect(status().isOk())
                .andReturn();
    }

}