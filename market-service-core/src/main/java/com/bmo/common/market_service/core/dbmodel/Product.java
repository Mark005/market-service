package com.bmo.common.market_service.core.dbmodel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @ManyToOne
  @JoinColumn(name = "cart_id")
  private Cart cart;

  @Builder.Default
  @ManyToMany(mappedBy = "products")
  private List<Category> categories = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "product", orphanRemoval = true)
  private List<ProductItem> productItems = new ArrayList<>();

}
