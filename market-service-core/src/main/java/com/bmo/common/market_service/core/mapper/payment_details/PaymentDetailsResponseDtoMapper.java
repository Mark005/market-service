package com.bmo.common.market_service.core.mapper.payment_details;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Category;
import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.model.category.CategoryResponseDto;
import com.bmo.common.market_service.model.category.CategorySimpleResponseDto;
import com.bmo.common.market_service.model.payment_details.PaymentDetailsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapStructCommonConfig.class)
public interface PaymentDetailsResponseDtoMapper {

    PaymentDetailsResponseDto map(PaymentDetails paymentDetails);
}
