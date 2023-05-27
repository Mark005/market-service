package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import com.bmo.common.market_service.model.product_item.ProductItemCreateDto;
import java.util.UUID;

public interface ProductItemService {

  ProductItem createProductItem(ProductItemCreateDto productCreateDto);

  ProductItem getProductItemById(UUID productItemId);

  ProductItem patchProductItemStatus(UUID productItemId, ProductItemStatus productItemStatus);

  void deleteProductItem(UUID productItemId);
}
