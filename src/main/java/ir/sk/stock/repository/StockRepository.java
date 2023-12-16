package ir.sk.stock.repository;

import ir.sk.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Created by sad.kayvanfar on 12/21/2021 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
  boolean existsByName(String name);
}
