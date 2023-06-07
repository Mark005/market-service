package com.bmo.common.market_service.core.dbmodel;

import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
    name = "jsonb",
    typeClass = JsonBinaryType.class
)
@NamedEntityGraphs(
    @NamedEntityGraph(name = "fullUsersOrder",
        attributeNodes = {
            @NamedAttributeNode(value = "productItems", subgraph = "productItems"),
            @NamedAttributeNode(value = "paymentDetails")
        },
        subgraphs = {
            @NamedSubgraph(
                name = "productItems",
                attributeNodes = {
                    @NamedAttributeNode("product")
                })
        }))
@Entity
@Table(name = "users_order")
public class UsersOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(name = "order_date_time")
  private ZonedDateTime orderDateTime;

  @Column(name = "last_update_date_time")
  private ZonedDateTime lastUpdateDateTime;

  @Type(type = "jsonb")
  @Column(name = "order_info", columnDefinition = "jsonb")
  private OrderInfo orderInfo;

  @Builder.Default
  @OneToMany(mappedBy = "usersOrder", orphanRemoval = true)
  private Set<ProductItem> productItems = new HashSet<>();

  @OneToOne(
      mappedBy = "usersOrder",
      optional = false,
      orphanRemoval = true)
  private PaymentDetails paymentDetails;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Builder.Default
  @OneToMany(mappedBy = "usersOrder", orphanRemoval = true)
  private Set<OrderHistory> orderHistories = new HashSet<>();

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
