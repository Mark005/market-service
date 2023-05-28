package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Phone;
import com.bmo.common.market_service.model.phone.PhoneCreateDto;
import com.bmo.common.market_service.model.phone.PhoneResponseDto;
import com.bmo.common.market_service.model.phone.PhoneUpdateDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructCommonConfig.class)
public interface PhoneMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  Phone mapFromCreateDto(PhoneCreateDto newPhone);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  Phone merge(@MappingTarget Phone target, PhoneUpdateDto updatedPhone);

  PhoneResponseDto map(Phone address);

  List<PhoneResponseDto> map(List<Phone> addresses);
}
