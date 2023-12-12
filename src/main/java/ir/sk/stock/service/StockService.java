package ir.sk.stock.service;

import ir.sk.stock.dto.PriceUpdateDTO;
import ir.sk.stock.dto.StockDTO;
import ir.sk.stock.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by sad.kayvanfar on 12/21/2021
 */
@Service
public interface StockService {
    Page<StockDTO> findAll(Pageable pageable);

    StockDTO findOne(Long id);

    StockDTO create(StockDTO stockDTO);

    StockDTO updatePrice(Long id, PriceUpdateDTO newPrice);

    void delete(Long id);
}
