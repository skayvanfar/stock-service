package ir.sk.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


/**
 * Created by sad.kayvanfar on 12/21/2021
 */
@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Operation(summary = "Get list of all Stocks by pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Stock.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid pagination info supplied",
                    content = @Content)})
    @GetMapping("/stocks")
    public ResponseEntity<Page<Stock>> getAllStocks(@PageableDefault(sort = "name", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(stockService.getAllStocks(pageable));
    }

    @Operation(summary = "Create a new Stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock is created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Stock.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Stock info supplied",
                    content = @Content)})
    @PostMapping("/stocks")
    public ResponseEntity<Stock> createStock(@Valid @RequestBody Stock stock) {
        return new ResponseEntity<>(stockService.save(stock), HttpStatus.CREATED); // 201
    }

    @Operation(summary = "Get a Stock by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Stock.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Stock not found",
                    content = @Content)})
    @GetMapping("/stocks/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Stock stock = stockService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + id));
        return ResponseEntity.ok().body(stock);
    }

    @Operation(summary = "Update the Stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock is updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Stock.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Stock priceUpdate supplied",
                    content = @Content)})
    @PatchMapping("/stocks/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable(value = "id") Long id, @Valid @RequestBody PriceUpdateDTO priceUpdateDTO) throws ResourceNotFoundException {
        Stock currStock = stockService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + id));

        currStock.setCurrentPrice(priceUpdateDTO.getCurrentPrice());

        final Stock updatedStock = stockService.save(currStock);
        return ResponseEntity.ok(updatedStock);
    }

    @Operation(summary = "Delete the Stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock is deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Stock.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Stock id supplied",
                    content = @Content)})
    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Stock stock = stockService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + id));
        stockService.delete(stock);
        return ResponseEntity.ok("Deleted");
    }
}
