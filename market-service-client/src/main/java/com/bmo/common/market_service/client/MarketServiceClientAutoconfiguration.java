package com.bmo.common.market_service.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = MarketServiceClient.class)
@ConditionalOnProperty("feign.client.config.market-service.url")
public class MarketServiceClientAutoconfiguration {
}
