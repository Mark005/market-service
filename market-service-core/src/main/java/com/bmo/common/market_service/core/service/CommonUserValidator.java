package com.bmo.common.market_service.core.service;

import java.util.UUID;

public interface CommonUserValidator {

  void validateUserNotDeleted(UUID userId);
}
