package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Phone;
import com.bmo.common.market_service.model.phone.PhoneResponseDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface PhoneMapper {
    PhoneResponseDto map(Phone address);

    List<PhoneResponseDto> map(List<Phone> addresses);

}
