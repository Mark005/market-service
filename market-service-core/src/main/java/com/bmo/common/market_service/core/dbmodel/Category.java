package com.bmo.common.market_service.core.dbmodel;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@FieldNameConstants
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraphs(
    @NamedEntityGraph(name = "categoryTree",
        attributeNodes = {
            @NamedAttributeNode(value = "subCategories", subgraph = "categoryWithCategories")
        },
        subgraphs = {
            @NamedSubgraph(
                name = "categoryWithCategories",
                attributeNodes = {
                    @NamedAttributeNode("subCategories")
                })
        }))
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  private String name;

  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_category_id")
  private Category parentCategory;

  @Builder.Default
  @OneToMany(mappedBy = "parentCategory", orphanRemoval = true)
  private Set<Category> subCategories = new HashSet<>();

  @Builder.Default
  @ManyToMany(mappedBy = "categories")
  private Set<Product> products = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Category category = (Category) o;
    return getId() != null && Objects.equals(getId(), category.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
