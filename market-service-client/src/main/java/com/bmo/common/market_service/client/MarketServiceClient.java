package com.bmo.common.market_service.client;

import com.bmo.common.market_service.model.user.UserResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "market-service-client", url = "${feign.client.config.market-service.url}")
public interface MarketServiceClient {

  @GetMapping("/users/{userId}")
  UserResponseDto getUserById(@PathVariable("userId") UUID userId);

}
