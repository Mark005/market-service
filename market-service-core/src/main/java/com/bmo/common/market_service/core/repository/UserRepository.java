package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}