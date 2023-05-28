package com.bmo.common.market_service.core.repository.specification;

import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.dbmodel.enums.Gender;
import com.bmo.common.market_service.core.dbmodel.enums.UserStatus;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

  public static Specification<User> name(String name) {
    return StringUtils.isBlank(name) ? null
        : (userRoot, cq, cb) ->
            cb.like(cb.lower(userRoot.get(User.Fields.name)), toLikeFormat(name));
  }

  public static Specification<User> surname(String surname) {
    return StringUtils.isBlank(surname) ? null
        : (userRoot, cq, cb) ->
            cb.like(cb.lower(userRoot.get(User.Fields.surname)), toLikeFormat(surname));
  }

  public static Specification<User> email(String email) {
    return StringUtils.isBlank(email) ? null
        : (userRoot, cq, cb) ->
            cb.like(cb.lower(userRoot.get(User.Fields.email)), toLikeFormat(email));
  }

  public static Specification<User> status(UserStatus status) {
    return Objects.isNull(status) ? null
        : (userRoot, cq, cb) -> cb.equal(userRoot.get(User.Fields.status), status);
  }

  public static Specification<User> gender(Gender gender) {
    return Objects.isNull(gender) ? null
        : (userRoot, cq, cb) -> cb.equal(userRoot.get(User.Fields.gender), gender);
  }


  private static String toLikeFormat(String term) {
    return "%" + term.toLowerCase() + "%";
  }
}
