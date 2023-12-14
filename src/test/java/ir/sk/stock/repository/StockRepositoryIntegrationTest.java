package ir.sk.stock.repository;

import ir.sk.stock.model.Stock;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StockRepositoryIntegrationTest {
  @Autowired private TestEntityManager entityManager;

  @Autowired private StockRepository stockRepository;

  @Test
  public void getAllStocks() {
    // given
    Stock stock =
        Stock.builder().name("NNN").currentPrice(BigDecimal.ONE).lastUpdate(Instant.now()).build();
    entityManager.persist(stock);
    entityManager.flush();

    // when
    Iterable<Stock> stocks = stockRepository.findAll();
    System.out.println(stocks);
  }
}
