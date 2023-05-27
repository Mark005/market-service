package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.model.product.ProductCreateDto;
import com.bmo.common.market_service.model.product.ProductFiltersCriteria;
import com.bmo.common.market_service.model.product.ProductUpdateDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  @Override
  public Product createProduct(ProductCreateDto productCreateDto) {
    //ToDo
    return null;
  }

  @Override
  public Page<Product> getProductsFiltered(ProductFiltersCriteria productFiltersCriteria) {
    //ToDo
    return null;
  }

  @Override
  public Product getProductById(UUID productId) {
    //ToDo
    return null;
  }

  @Override
  public Product updateProduct(UUID productId, ProductUpdateDto productUpdateDto) {
    //ToDo
    return null;
  }

  @Override
  public void deleteProduct(UUID productId) {
    //ToDo
  }
}
