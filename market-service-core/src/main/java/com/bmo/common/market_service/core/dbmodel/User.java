package com.bmo.common.market_service.core.dbmodel;

import com.bmo.common.market_service.core.dbmodel.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "security_user_id")
  private UUID securityUserId;

  private String name;

  private String surname;

  private String email;

  @Enumerated
  private Gender gender;

  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private List<Address> addresses = new ArrayList<>();

  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private List<Phone> phones = new ArrayList<>();

  @OneToOne(optional = false, orphanRemoval = true)
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;

}
