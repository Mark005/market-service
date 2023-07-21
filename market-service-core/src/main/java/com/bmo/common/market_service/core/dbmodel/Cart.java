package com.bmo.common.market_service.core.dbmodel;

import java.util.LinkedHashSet;
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
import javax.persistence.MapsId;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraphs(
    @NamedEntityGraph(name = "fullCart",
        attributeNodes = {
            @NamedAttributeNode(value = "cartProducts", subgraph = "cartProducts")
        },
        subgraphs = {
            @NamedSubgraph(
                name = "cartProducts",
                attributeNodes = {
                    @NamedAttributeNode("product")
                })
        }))
@Table(name = "cart")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Builder.Default
  @Fetch(FetchMode.JOIN)
  @OneToMany(mappedBy = "cart", orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<CartProduct> cartProducts = new LinkedHashSet<>();


  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "id")
  private User user;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Cart cart = (Cart) o;
    return getId() != null && Objects.equals(getId(), cart.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
