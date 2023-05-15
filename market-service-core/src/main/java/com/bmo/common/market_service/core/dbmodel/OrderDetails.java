package com.bmo.common.market_service.core.dbmodel;

import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  private OrderStatus orderStatus;

  private ZonedDateTime orderDate;

  private ZonedDateTime lastUpdateDate;

  @Builder.Default
  @OneToMany(mappedBy = "orderDetails", orphanRemoval = true)
  private List<ProductItem> productItems = new ArrayList<>();

  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "payment_details_id")
  private PaymentDetails paymentDetails;

  @Builder.Default
  @OneToMany(mappedBy = "orderDetails", orphanRemoval = true)
  private List<OrderHistory> orderHistories = new ArrayList<>();

}
