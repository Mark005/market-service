package com.bmo.common.market_service.core.repository.specification;

import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ProductItemSpecification {

  public static Specification<ProductItem> productId(UUID productId) {
    return Objects.isNull(productId) ? null
        : (itemRoot, cq, cb) -> {
          Join<ProductItem, Product> productItemsProduct = itemRoot.join(ProductItem.Fields.product);
          return cb.equal(productItemsProduct.get(Product.Fields.id), productId);
        };
  }

  public static Specification<ProductItem> status(ProductItemStatus status) {
    return Objects.isNull(status) ? null
        : (itemRoot, cq, cb) -> cb.equal(itemRoot.get(ProductItem.Fields.status), status);
  }
}
