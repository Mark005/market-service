package com.bmo.common.market_service.model.user;

import com.bmo.common.market_service.model.enums.GenderDto;
import com.bmo.common.market_service.model.enums.UserStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsersFilterCriteria {

    private String name;
    private String surname;
    private String email;
    private GenderDto gender;
    private UserStatusDto status;
}
