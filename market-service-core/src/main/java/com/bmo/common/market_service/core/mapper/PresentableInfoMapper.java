package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.OrderInfo;
import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.dbmodel.ProductSnapshot;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructCommonConfig.class)
public interface PresentableInfoMapper {

  @Mapping(target = "products", source = "productToQuantity")
  @Mapping(target = "productsPrice", source = "productToQuantity")
  OrderInfo map(Map<Product, Integer> productToQuantity);

  default List<ProductSnapshot> mapToProductSnapshot(Map<Product, Integer> productToQuantity) {
    return productToQuantity.entrySet()
        .stream()
        .map(productQuantityEntry -> {
          ProductSnapshot productSnapshot = map(productQuantityEntry);
          productSnapshot.setQuantity(productQuantityEntry.getValue());
          return productSnapshot;
        })
        .toList();
  }

  @Mapping(target = ".", source = "key")
  @Mapping(target = "quantity", ignore = true)
  ProductSnapshot map(Entry<Product, Integer> productQuantityEntry);

  default BigDecimal mapToQuantity(Map<Product, Integer> productToQuantity) {
    BigDecimal summaryPrice = null;
    for (Entry<Product, Integer> productQuantityEntry : productToQuantity.entrySet()) {
      BigDecimal summaryPriceForAllCurrentProducts = productQuantityEntry
          .getKey()
          .getPrice()
          .multiply(new BigDecimal(productQuantityEntry.getValue()));

      if (summaryPrice == null) {
        summaryPrice = summaryPriceForAllCurrentProducts;
        continue;
      }

      summaryPrice = summaryPrice.add(summaryPriceForAllCurrentProducts);
    }
    return summaryPrice;
  }
}
