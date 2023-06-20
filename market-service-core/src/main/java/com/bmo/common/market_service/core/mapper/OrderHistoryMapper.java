package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.OrderHistory;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.dbmodel.UsersOrderSnapshot;
import java.time.ZonedDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructCommonConfig.class, imports = ZonedDateTime.class)
public interface OrderHistoryMapper {

  @Mapping(target = "newOrderStatus", source = "status")
  @Mapping(target = "updateDateTime", expression = "java(ZonedDateTime.now())")
  @Mapping(target = "usersOrderSnapshot", source = "usersOrder")
  @Mapping(target = "usersOrder", source = "usersOrder")
  OrderHistory mapHistoryRecord(UsersOrder usersOrder);

  @Mapping(target = "paymentDetailsSnapshot", source = "paymentDetails")
  UsersOrderSnapshot mapSnapshot(UsersOrder usersOrder);

}
