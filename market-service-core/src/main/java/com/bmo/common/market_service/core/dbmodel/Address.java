package com.bmo.common.market_service.core.dbmodel;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  private String country;

  private String city;

  private String street;

  private String building;

  private String apartment;

  private String postalCode;

  private String comment;

  @Column(name = "is_primary", nullable = false)
  private Boolean isPrimary;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;


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
