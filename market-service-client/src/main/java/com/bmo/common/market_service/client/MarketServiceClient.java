package com.bmo.common.market_service.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "market-service-client", url = "${feign.client.config.market-service.url}")
public interface MarketServiceClient {

  @PostMapping("/test/{id}")
  Object test(@PathVariable("id") UUID pathVar, @RequestBody Object object);

}
