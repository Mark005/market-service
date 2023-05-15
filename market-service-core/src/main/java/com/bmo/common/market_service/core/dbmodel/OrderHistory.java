package com.bmo.common.market_service.core.dbmodel;

import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)
@Entity
@Table(name = "order_history")
public class OrderHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "old_order_status")
  private OrderStatus oldOrderStatus;

  @Column(name = "new_order_status")
  private OrderStatus newOrderStatus;

  private ZonedDateTime date;

  @Type(type = "jsonb")
  @Column(name = "order_details_snapshot", columnDefinition = "jsonb")
  private OrderDetailsSnapshot orderDetailsSnapshot;

  @ManyToOne(optional = false)
  @JoinColumn(name = "order_details_id", nullable = false)
  private OrderDetails orderDetails;

}
