package ir.sk.stock.mapper;

import ir.sk.stock.dto.StockDTO;
import ir.sk.stock.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "currentPrice", target = "currentPrice")
  @Mapping(source = "lastUpdate", target = "lastUpdate")
  StockDTO stockToStockDTO(Stock stock);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "currentPrice", target = "currentPrice")
  @Mapping(
      source = "lastUpdate",
      target = "lastUpdate",
      defaultExpression = "java(java.time.Instant.now())")
  Stock stockDTOToStock(StockDTO stockDTO);
}
