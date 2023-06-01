package com.bmo.common.market_service.core.repository.specification;

import com.bmo.common.market_service.core.dbmodel.Category;
import com.bmo.common.market_service.core.dbmodel.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

public class ProductSpecification {

  public static Specification<Product> name(String name) {
    return StringUtils.isBlank(name) ? null
        : (userRoot, cq, cb) ->
            cb.like(cb.lower(userRoot.get(Product.Fields.name)), toLikeFormat(name));
  }

  public static Specification<Product> description(String description) {
    return StringUtils.isBlank(description) ? null
        : (userRoot, cq, cb) ->
            cb.like(cb.lower(userRoot.get(Product.Fields.description)), toLikeFormat(description));
  }

  public static Specification<Product> priceBetween(BigDecimal priceFrom, BigDecimal priceTo) {
    return priceFrom == null ? null
        : (userRoot, cq, cb) ->
            cb.between(userRoot.get(Product.Fields.price), priceFrom, priceTo);
  }

  public static Specification<Product> barcode(String barcode) {
    return StringUtils.isBlank(barcode) ? null
        : (userRoot, cq, cb) ->
            cb.like(cb.lower(userRoot.get(Product.Fields.barcode)), toLikeFormat(barcode));
  }

  public static Specification<Product> categoryIds(List<UUID> categoryIds) {
    return CollectionUtils.isEmpty(categoryIds) ? null
        : (userRoot, cq, cb) -> {
          Join<Product, Category> productsCategory = userRoot.join(Product.Fields.categories);
          return cb.in(productsCategory.get(Category.Fields.id));
        };
  }

  private static String toLikeFormat(String term) {
    return "%" + term.toLowerCase() + "%";
  }
}
