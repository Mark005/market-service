package com.bmo.common.market_service.core.dbmodel;

import com.bmo.common.market_service.core.dbmodel.enums.Gender;
import com.bmo.common.market_service.core.dbmodel.enums.UserStatus;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
  private Set<Address> addresses = new HashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private Set<Phone> phones = new HashSet<>();

  @OneToOne(
      mappedBy = "user",
      optional = false,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private Cart cart;

  @Builder.Default
  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private Set<UsersOrder> usersOrders = new LinkedHashSet<>();


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    User user = (User) o;
    return getId() != null && Objects.equals(getId(), user.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
