package com.bmo.common.market_service.core.dbmodel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.Hibernate;

@FieldNameConstants
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  private String name;

  private String description;

  private BigDecimal price;

  private String barcode;

  @Builder.Default
  @OneToMany(mappedBy = "product", orphanRemoval = true)
  private Set<CartProduct> cartProducts = new LinkedHashSet<>();

  @Builder.Default
  @ManyToMany
  @JoinTable(name = "category_products",
      joinColumns = @JoinColumn(
          name = "product_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "category_id",
          referencedColumnName = "id"))
  private Set<Category> categories = new HashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "product", orphanRemoval = true)
  private Set<ProductItem> productItems = new HashSet<>();


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Product product = (Product) o;
    return getId() != null && Objects.equals(getId(), product.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
