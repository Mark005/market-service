package com.bmo.common.market_service.core.mapper.cart;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.model.cart.CartResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface CartResponseDtoMapper {
    CartResponseDto map(Cart cart);
}
