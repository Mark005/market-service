package com.bmo.common.market_service.core.mapper.phone;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.core.dbmodel.Phone;
import com.bmo.common.market_service.model.address.AddressResponseDto;
import com.bmo.common.market_service.model.phone.PhoneResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructCommonConfig.class)
public interface PhoneResponseDtoMapper {
    PhoneResponseDto map(Phone address);

    List<PhoneResponseDto> map(List<Phone> addresses);

}
