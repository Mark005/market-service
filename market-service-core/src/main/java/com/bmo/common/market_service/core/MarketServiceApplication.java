package com.bmo.common.market_service.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableConfigurationProperties
@SpringBootApplication
public class MarketServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MarketServiceApplication.class, args);
  }

}

