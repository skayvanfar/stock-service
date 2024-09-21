package ir.sk.stock.service;

import ir.sk.stock.dto.PriceUpdateDTO;
import ir.sk.stock.dto.StockDTO;
import ir.sk.stock.exception.StockAlreadyExistsException;
import ir.sk.stock.exception.StockNotFoundException;
import ir.sk.stock.mapper.StockMapper;
import ir.sk.stock.model.Stock;
import ir.sk.stock.repository.StockRepository;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Created by sad.kayvanfar on 12/21/2021 */
@Service
public class StockServiceImpl implements StockService {

  private final StockRepository stockRepository;
  private final StockMapper stockMapper;

  @Autowired
  public StockServiceImpl(StockRepository stockRepository, StockMapper stockMapper) {
    this.stockRepository = stockRepository;
    this.stockMapper = stockMapper;
  }

  @Transactional(readOnly = true)
  @Override
  public Page<StockDTO> findAll(Pageable pageable) {
    Page<Stock> stockPage = stockRepository.findAll(pageable);
    return stockPage.map(stockMapper::stockToStockDTO);
  }

  @Transactional(readOnly = true)
  @Override
  public StockDTO findOne(Long id) {
    return stockRepository
        .findById(id)
        .map(stockMapper::stockToStockDTO)
        .orElseThrow(() -> StockNotFoundException.withId(id));
  }

  @Transactional
  @Override
  public StockDTO create(StockDTO stockDTO) {
    if (stockRepository.existsByName(stockDTO.getName())) {
      throw StockAlreadyExistsException.withName(stockDTO.getName());
    }

    Stock stock = stockMapper.stockDTOToStock(stockDTO);
    stock.setLastUpdate(Instant.now());
    stock = stockRepository.save(stock);
    return stockMapper.stockToStockDTO(stock);
  }

  @Transactional
  @Override
  public StockDTO updatePrice(Long id, PriceUpdateDTO newPrice) {
    Stock stock = stockRepository.findById(id).orElseThrow(() -> StockNotFoundException.withId(id));

    stock.setCurrentPrice(newPrice.getCurrentPrice());
    stock = stockRepository.save(stock);
    return stockMapper.stockToStockDTO(stock);
  }

  @Transactional
  @Override
  public void delete(Long id) {
    Stock stock = stockRepository.findById(id).orElseThrow(() -> StockNotFoundException.withId(id));
    stockRepository.deleteById(stock.getId());
  }
}
