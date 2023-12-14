package ir.sk.stock.service;

import ir.sk.stock.dto.StockDTO;
import ir.sk.stock.mapper.StockMapper;
import ir.sk.stock.model.Stock;
import ir.sk.stock.repository.StockRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

  private StockService stockService;

  @Mock private StockRepository stockRepository;

  private StockMapper stockMapper;

  private List<Stock> stocks;

  @BeforeEach
  public void setUp() {
    Instant first = Instant.parse("1980-04-09T10:15:30.00Z");
    Instant second = Instant.parse("1977-04-09T10:15:30.00Z");

    stocks =
        Arrays.asList(
            new Stock(1L, "BBT", BigDecimal.valueOf(37.55), first),
            new Stock(2L, "BTT", BigDecimal.valueOf(38.55), second));

    Page<Stock> pagedStocks = new PageImpl<>(stocks);
    Mockito.when(stockRepository.findAll(Pageable.ofSize(10))).thenReturn(pagedStocks);

    stockService = new StockServiceImpl(stockRepository, stockMapper);
  }

  @Test
  public void testGetAllStocksBySize() {
    int expectedSize = 2;
    Page<StockDTO> Stocks = stockService.findAll(Pageable.ofSize(10));
    Assertions.assertEquals(expectedSize, Stocks.getTotalElements());
  }

  @Test
  public void testGetAllStocksByContent() {
    Page<StockDTO> stocks = stockService.findAll(Pageable.ofSize(10));
    Assertions.assertEquals(this.stocks, stocks.getContent());
  }

  @Test
  public void testSave() {
    Stock newStock =
        new Stock(1L, "KKK", BigDecimal.valueOf(38.45), Instant.parse("1985-04-09T10:15:30.00Z"));
    StockDTO stockDTO =
        new StockDTO(
            1L, "KKK", BigDecimal.valueOf(38.45), Instant.parse("1985-04-09T10:15:30.00Z"));
    Mockito.when(stockRepository.save(newStock)).thenReturn(newStock);
    String expectedName = "KKK";
    StockDTO stock = stockService.create(stockDTO);
    Assertions.assertNotNull(stock);
    Assertions.assertEquals(expectedName, stock.getName());
  }
}
