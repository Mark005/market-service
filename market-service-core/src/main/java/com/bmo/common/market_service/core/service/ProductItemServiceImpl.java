package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import com.bmo.common.market_service.model.product_item.ProductItemCreateDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {

  @Override
  public ProductItem createProductItem(ProductItemCreateDto productCreateDto) {
    //ToDo
    return null;
  }

  @Override
  public ProductItem getProductItemById(UUID productItemId) {
    //ToDo
    return null;
  }

  @Override
  public ProductItem patchProductItemStatus(
      UUID productItemId,
      ProductItemStatus productItemStatus) {
    //ToDo
    return null;
  }

  @Override
  public void deleteProductItem(UUID productItemId) {
    //ToDo
  }
}
