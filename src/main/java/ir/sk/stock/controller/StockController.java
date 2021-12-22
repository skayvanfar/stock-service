package ir.sk.stock.controller;

import ir.sk.stock.dto.PriceUpdateDTO;
import ir.sk.stock.exception.ResourceNotFoundException;
import ir.sk.stock.model.Stock;
import ir.sk.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * Created by sad.kayvanfar on 12/21/2021
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stocks")
    public ResponseEntity<Page<Stock>> getAllStocks(@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(stockService.getAllStocks(pageable)); // 200
    }

    @PostMapping("/stocks")
    public ResponseEntity<Stock> createEmployee(@Valid @RequestBody Stock stock) {
        return new ResponseEntity<>(stockService.save(stock), HttpStatus.CREATED); // 201
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<Stock> getEmployeeById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Stock stock = stockService.findById(id).orElseThrow(() -> new ResourceNotFoundException("stocks not found for this id :: " + id));
        return ResponseEntity.ok().body(stock);
    }

    @PatchMapping("/stocks/{id}")
    public ResponseEntity<Stock> updatePokemon(@PathVariable(value = "id") Long id, @Valid @RequestBody PriceUpdateDTO priceUpdateDTO) throws ResourceNotFoundException {
        Stock currPokemon = stockService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + id));

        currPokemon.setCurrentPrice(priceUpdateDTO.getCurrentPrice());

        final Stock updatedEmployee = stockService.save(currPokemon);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Stock stock = stockService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + id));
        stockService.delete(stock);
        return ResponseEntity.ok("Deleted");
    }
}
