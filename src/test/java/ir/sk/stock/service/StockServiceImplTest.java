package ir.sk.stock.service;

import ir.sk.stock.model.Stock;
import ir.sk.stock.repository.StockRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTest {

    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    private List<Stock> stocks;

    @Before
    public void setUp() {
        Instant first = Instant.parse("1980-04-09T10:15:30.00Z");
        Instant second = Instant.parse("1977-04-09T10:15:30.00Z");

        stocks = Arrays.asList(
                new Stock(1L, "BBT", BigDecimal.valueOf(37.55), first)
                ,new Stock(2L, "BTT", BigDecimal.valueOf(37.55), second));

        Page<Stock> pagedStocks = new PageImpl<>(stocks);
        Mockito.when(stockRepository.findAll(Pageable.ofSize(10))).thenReturn(pagedStocks);

        stockService = new StockServiceImpl(stockRepository);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllStocksBySize() {
        int expectedSize = 2;
        Page<Stock> Stocks = stockService.getAllStocks(Pageable.ofSize(10));
        Assert.assertEquals(expectedSize, Stocks.getTotalElements());
    }

    @Test
    public void testGetAllStocksByContent() {
        Page<Stock> stocks = stockService.getAllStocks(Pageable.ofSize(10));
        Assert.assertEquals(this.stocks, stocks.getContent());
    }
}