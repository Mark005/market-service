package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import com.bmo.common.market_service.core.mapper.EnumMapper;
import com.bmo.common.market_service.core.mapper.PageableMapper;
import com.bmo.common.market_service.core.repository.ProductItemRepository;
import com.bmo.common.market_service.core.repository.specification.ProductItemSpecification;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.product_item.ProductItemCreateDto;
import com.bmo.common.market_service.model.product_item.ProductItemFiltersCriteria;
import com.bmo.common.market_service.model.product_item.ProductItemPatchStatusDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {

  private final ProductItemRepository productItemRepository;

  private final EnumMapper enumMapper;

  @Override
  public List<ProductItem> createProductItems(ProductItemCreateDto productCreateDto) {
    List<ProductItem> productItems = IntStream.range(0, productCreateDto.getItemsCount())
        .mapToObj(unused -> ProductItem.builder()
            .status(ProductItemStatus.AVAILABLE)
            .product(Product.builder()
                .id(productCreateDto.getProductId())
                .build())
            .build())
        .toList();
    return productItemRepository.saveAll(productItems);
  }

  @Override
  public Page<ProductItem> getProductItemsByProductId(UUID productId,
      ProductItemFiltersCriteria filtersCriteria, PageRequestDto pageRequest) {

    Specification<ProductItem> specification =
        Specification.where(ProductItemSpecification.productId(productId))
            .and(ProductItemSpecification.status(enumMapper.map(filtersCriteria.getItemStatus())));

    return productItemRepository.findAll(specification, PageableMapper.map(pageRequest));
  }

  @Override
  public ProductItem getProductItemById(UUID productItemId) {
    return productItemRepository.findById(productItemId)
        .orElseThrow(() -> new EntityNotFoundException("ProductItem", productItemId));
  }

  @Override
  public ProductItem patchProductItemStatus(
      UUID productItemId,
      ProductItemPatchStatusDto patchStatusDto) {
    ProductItemStatus productItemStatus = enumMapper.map(patchStatusDto.getStatus());
    ProductItem productItem = productItemRepository.findById(productItemId)
        .orElseThrow(() -> new EntityNotFoundException("ProductItem", productItemId));

    productItem.setStatus(productItemStatus);
    return productItemRepository.save(productItem);
  }

  @Override
  public void deleteProductItem(UUID productItemId) {
    productItemRepository.deleteById(productItemId);
  }
}
