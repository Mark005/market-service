package com.bmo.common.market_service.core.dbmodel;

import com.bmo.common.market_service.core.dbmodel.enums.Gender;
import com.bmo.common.market_service.core.dbmodel.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

@FieldNameConstants
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
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

  @Enumerated(EnumType.STRING)
  private UserStatus status;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Builder.Default
  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private List<Address> addresses = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private List<Phone> phones = new ArrayList<>();

  @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id")
  private Cart cart;

}
