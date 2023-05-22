package com.bmo.common.market_service.model.oreder_details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderProduct {

    @NotNull
    private UUID productId;

    @Min(1)
    private Integer count;
}
