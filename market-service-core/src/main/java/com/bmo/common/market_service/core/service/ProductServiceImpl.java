package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.mapper.PageableMapper;
import com.bmo.common.market_service.core.mapper.ProductMapper;
import com.bmo.common.market_service.core.repository.ProductRepository;
import com.bmo.common.market_service.core.repository.specification.ProductSpecification;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.product.ProductCreateDto;
import com.bmo.common.market_service.model.product.ProductFiltersCriteria;
import com.bmo.common.market_service.model.product.ProductUpdateDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  @Override
  @Transactional
  public Product createProduct(ProductCreateDto productCreateDto) {
    Product product = productMapper.mapFromCreateDto(productCreateDto);
    return productRepository.save(product);
  }

  @Override
  public Page<Product> getProductsFiltered(ProductFiltersCriteria filter, PageRequestDto pageRequest) {
    Specification<Product> specification =
        Specification.where(ProductSpecification.name(filter.getName()))
            .and(ProductSpecification.description(filter.getDescription()))
            .and(ProductSpecification.priceBetween(filter.getPriceFrom(), filter.getPriceTo()))
            .and(ProductSpecification.barcode(filter.getBarcode()))
            .and(ProductSpecification.categoryIds(filter.getCategoryIds()));

    return productRepository.findAll(specification, PageableMapper.map(pageRequest));
  }

  @Override
  public Product getProductById(UUID productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new EntityNotFoundException("Product", productId));
  }

  @Override
  @Transactional
  public Product updateProduct(UUID productId, ProductUpdateDto productUpdateDto) {
    return productRepository.findById(productId)
        .map(productFromDb -> productMapper.merge(productFromDb, productUpdateDto))
        .map(productRepository::save)
        .orElseThrow(() -> new EntityNotFoundException("Product", productId));
  }

  @Override
  public void deleteProduct(UUID productId) {
    productRepository.deleteById(productId);
  }
}
