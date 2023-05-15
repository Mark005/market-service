package com.bmo.common.market_service.core.dbmodel;

import com.bmo.common.market_service.core.dbmodel.enums.Gender;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  private String country;

  private String city;

  private String street;

  private Gender building;

  private Gender apartment;

  private Gender postalCode;

  private Gender comment;

  @Column(name = "is_primary", nullable = false)
  private Gender isPrimary;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

}
