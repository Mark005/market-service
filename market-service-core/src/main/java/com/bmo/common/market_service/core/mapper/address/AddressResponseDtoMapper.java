package com.bmo.common.market_service.core.mapper.address;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.model.address.AddressResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructCommonConfig.class)
public interface AddressResponseDtoMapper {
    AddressResponseDto map(Address address);

    List<AddressResponseDto> map(List<Address> addresses);

}
