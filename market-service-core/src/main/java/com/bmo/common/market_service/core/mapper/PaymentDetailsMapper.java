package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.model.payment_details.PaymentDetailsResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class,
        uses = {CategoryMapper.class})
public interface PaymentDetailsMapper {

    PaymentDetailsResponseDto mapToResponseDto(PaymentDetails paymentDetails);
}
