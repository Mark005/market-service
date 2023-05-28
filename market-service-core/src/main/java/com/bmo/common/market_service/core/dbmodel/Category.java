package com.bmo.common.market_service.core.dbmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  private String name;

  private String description;

  @ManyToOne
  @JoinColumn(name = "parent_category_id")
  private Category parentCategory;

  @Builder.Default
  @OneToMany(mappedBy = "parentCategory", orphanRemoval = true)
  private List<Category> subCategories = new ArrayList<>();

  @Builder.Default
  @ManyToMany
  @JoinTable(name = "category_products",
      joinColumns = @JoinColumn(name = "category_id"),
      inverseJoinColumns = @JoinColumn(name = "products_id"))
  private List<Product> products = new ArrayList<>();

}
