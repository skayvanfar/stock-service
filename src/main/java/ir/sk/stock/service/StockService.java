package ir.sk.stock.service;

import ir.sk.stock.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sad.kayvanfar on 12/21/2021
 */
public interface StockService {
    Page<Stock> getAllStocks(Pageable pageable);

}
