package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.dbmodel.enums.UserStatus;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

  @Transactional
  @Modifying
  @Query("update User u set u.status = :status where u.id = :id")
  void updateStatusById(UUID id, UserStatus status);

}