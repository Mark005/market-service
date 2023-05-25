package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.model.product.ProductCreateDto;
import com.bmo.common.market_service.model.product.ProductFiltersCriteria;
import org.springframework.data.domain.Page;

public interface ProductService {

  Product createProduct(ProductCreateDto productCreateDto);

  Page<Product> getProductsFiltered(ProductFiltersCriteria productFiltersCriteria);

}
