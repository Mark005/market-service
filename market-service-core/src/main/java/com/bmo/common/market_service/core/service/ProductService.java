package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.product.ProductCreateDto;
import com.bmo.common.market_service.model.product.ProductFiltersCriteria;
import com.bmo.common.market_service.model.product.ProductUpdateDto;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ProductService {

  Product createProduct(ProductCreateDto productCreateDto);

  Page<Product> getProductsFiltered(ProductFiltersCriteria productFiltersCriteria, PageRequestDto pageRequest);

  Product getProductById(UUID productId);

  Product updateProduct(UUID productId, ProductUpdateDto productUpdateDto);

  void deleteProduct(UUID productId);
}
