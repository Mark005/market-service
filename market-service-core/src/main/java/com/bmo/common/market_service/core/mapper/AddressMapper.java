package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.model.address.AddressCreateDto;
import com.bmo.common.market_service.model.address.AddressResponseDto;
import com.bmo.common.market_service.model.address.AddressUpdateDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructCommonConfig.class)
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address mapFromCreateDto(AddressCreateDto newAddress);

    AddressResponseDto mapToResponseDto(Address address);

    List<AddressResponseDto> mapToResponseDto(List<Address> addresses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address merge(@MappingTarget Address userFromDb, AddressUpdateDto updatedAddress);
}
