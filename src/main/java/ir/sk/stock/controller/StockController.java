package ir.sk.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.sk.stock.dto.PriceUpdateDTO;
import ir.sk.stock.dto.StockDTO;
import ir.sk.stock.model.Stock;
import ir.sk.stock.service.StockService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Created by sad.kayvanfar on 12/21/2021 */
@Slf4j
@RestController
@RequestMapping("/api/stocks")
@CrossOrigin("*")
public class StockController {

  private StockService stockService;

  @Autowired
  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  @GetMapping
  @Operation(summary = "Get a list of stocks", description = "Retrieve a paginated list of stocks.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Stocks retrieved successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Stock.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid pagination info supplied",
            content = @Content)
      })
  public ResponseEntity<Page<StockDTO>> getAllStocks(
      @PageableDefault(sort = "name", direction = Sort.Direction.DESC) Pageable pageable) {
    return ResponseEntity.ok(stockService.findAll(pageable));
  }

  @PostMapping()
  @Operation(summary = "Create a Stock", description = "Create a new stock.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Stock is created successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = StockDTO.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Stock info supplied",
            content = @Content),
        @ApiResponse(responseCode = "409", description = "Stock already exists", content = @Content)
      })
  public ResponseEntity<StockDTO> createStock(@Valid @RequestBody StockDTO stockDTO) {
    StockDTO createdStock = stockService.create(stockDTO);
    return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get one Stock", description = "Retrieve one stock by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Stock retrieved successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Stock.class))
            }),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "Stock not found", content = @Content)
      })
  public ResponseEntity<StockDTO> getStockById(@PathVariable Long id) {
    return ResponseEntity.ok(stockService.findOne(id));
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Update the Stock")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Stock is updated successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Stock.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Stock priceUpdate supplied",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Stock not found")
      })
  public ResponseEntity<StockDTO> updateStock(
      @PathVariable Long id, @Valid @RequestBody PriceUpdateDTO priceUpdateDTO) {

    StockDTO updatedStock = stockService.updatePrice(id, priceUpdateDTO);
    log.info("the stock updated by id: " + updatedStock.getId());

    return ResponseEntity.ok(updatedStock);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete the Stock")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Stock is deleted successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Stock.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Stock id supplied",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Stock not found")
      })
  public ResponseEntity<String> deleteStock(@PathVariable Long id) {
    stockService.delete(id);
    log.info("the stock deleted by id: " + id);
    return ResponseEntity.noContent().build();
  }
}
