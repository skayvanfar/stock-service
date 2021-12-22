package ir.sk.stock.service;

import ir.sk.stock.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by sad.kayvanfar on 12/21/2021
 */
@Service
public interface StockService {
    Page<Stock> getAllStocks(Pageable pageable);
    Stock save(Stock stock);
    Optional<Stock> findById(Long id);
    void delete(Stock stock);
}
