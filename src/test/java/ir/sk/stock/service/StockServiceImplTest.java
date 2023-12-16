package ir.sk.stock.service;

import static org.mockito.ArgumentMatchers.any;

import ir.sk.stock.dto.StockDTO;
import ir.sk.stock.mapper.StockMapper;
import ir.sk.stock.mapper.StockMapperImpl;
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

    stockMapper = new StockMapperImpl();

    stockService = new StockServiceImpl(stockRepository, stockMapper);
  }

  @Test
  public void testGetAllStocksBySize() {
    Page<Stock> pagedStocks = new PageImpl<>(stocks);
    Mockito.when(stockRepository.findAll(Pageable.ofSize(10))).thenReturn(pagedStocks);

    int expectedSize = 2;
    Page<StockDTO> result = stockService.findAll(Pageable.ofSize(10));
    List<StockDTO> expected =
        stocks.stream().map(stock -> stockMapper.stockToStockDTO(stock)).toList();
    Assertions.assertEquals(expectedSize, result.getTotalElements());
    Assertions.assertEquals(expected, result.getContent());
  }

  @Test
  public void testGetAllStocksByContent() {
    Page<Stock> pagedStocks = new PageImpl<>(stocks);
    Mockito.when(stockRepository.findAll(Pageable.ofSize(10))).thenReturn(pagedStocks);

    List<StockDTO> expected =
        stocks.stream().map(stock -> stockMapper.stockToStockDTO(stock)).toList();
    Page<StockDTO> stocks = stockService.findAll(Pageable.ofSize(10));
    Assertions.assertEquals(expected, stocks.getContent());
  }

  @Test
  public void testCreate() {
    Stock oldStock =
        new Stock(1L, "KKK", BigDecimal.valueOf(38.45), Instant.parse("1985-04-09T10:15:30.00Z"));
    StockDTO stockDTO =
        new StockDTO(
            1L, "KKK", BigDecimal.valueOf(45.08), Instant.parse("1985-04-09T10:15:30.00Z"));
    Mockito.when(stockRepository.save(any(Stock.class))).thenReturn(oldStock);
    StockDTO stock = stockService.create(stockDTO);
    Assertions.assertNotNull(stock);
    Assertions.assertEquals(oldStock.getCurrentPrice(), stock.getCurrentPrice());
  }
}
