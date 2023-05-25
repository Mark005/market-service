package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.model.address.AddressResponseDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface AddressMapper {
    AddressResponseDto mapToResponseDto(Address address);

    List<AddressResponseDto> mapToResponseDto(List<Address> addresses);

}
