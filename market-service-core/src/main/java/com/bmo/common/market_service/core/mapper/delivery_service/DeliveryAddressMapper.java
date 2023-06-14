package com.bmo.common.market_service.core.mapper.delivery_service;

import com.bmo.common.delivery_service.model.rest.ContactPhoneDto;
import com.bmo.common.delivery_service.model.rest.DeliveryAddressDto;
import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.core.dbmodel.Phone;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface DeliveryAddressMapper {

  DeliveryAddressDto mapAddress(Address newAddress);

  ContactPhoneDto mapPhone(Phone phone);
}
