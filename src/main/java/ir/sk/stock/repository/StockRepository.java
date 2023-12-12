package ir.sk.stock.repository;

import ir.sk.stock.model.Stock;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sad.kayvanfar on 12/21/2021
 */
@Repository
public interface StockRepository extends PagingAndSortingRepository<Stock, Long> {
    boolean existsByName(String name);
}
