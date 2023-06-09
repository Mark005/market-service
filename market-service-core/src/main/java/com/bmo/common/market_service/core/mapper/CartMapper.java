package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.model.cart.CartResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class,
    uses = {CategoryMapper.class, ProductMapper.class})
public interface CartMapper {

  CartResponseDto mapToResponseDto(Cart cart);
}
