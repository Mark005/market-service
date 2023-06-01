package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.product_item.ProductItemCreateDto;
import com.bmo.common.market_service.model.product_item.ProductItemFiltersCriteria;
import com.bmo.common.market_service.model.product_item.ProductItemPatchStatusDto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ProductItemService {

  List<ProductItem> createProductItems(ProductItemCreateDto productCreateDto);

  Page<ProductItem> getProductItemsByProductId(UUID productId, ProductItemFiltersCriteria filtersCriteria, PageRequestDto pageRequest);

  ProductItem getProductItemById(UUID productItemId);

  ProductItem patchProductItemStatus(UUID productItemId, ProductItemPatchStatusDto patchStatusDto);

  void deleteProductItem(UUID productItemId);
}
