package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.enums.Gender;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentMethod;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentStatus;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import com.bmo.common.market_service.core.dbmodel.enums.UserStatus;
import com.bmo.common.market_service.model.enums.GenderDto;
import com.bmo.common.market_service.model.enums.PaymentMethodDto;
import com.bmo.common.market_service.model.enums.PaymentStatusDto;
import com.bmo.common.market_service.model.enums.ProductItemStatusDto;
import com.bmo.common.market_service.model.enums.UserStatusDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface EnumMapper {

  UserStatus map(UserStatusDto user);

  Gender map(GenderDto gender);

  PaymentStatus map(PaymentStatusDto paymentStatus);

  ProductItemStatus map(ProductItemStatusDto productItemStatus);

  PaymentMethod map(PaymentMethodDto paymentMethod);
}
