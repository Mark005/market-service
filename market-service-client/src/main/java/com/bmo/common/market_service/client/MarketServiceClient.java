package com.bmo.common.market_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "market-service-client", url = "${feign.client.config.market-service.url}")
public interface MarketServiceClient {

  @PostMapping("/test/{id}")
  Object test(@PathVariable("id") UUID pathVar, @RequestBody Object object);

}
