package com.ktk.duka.mygoal.service.user.bukcamp;

import com.ktk.duka.mygoal.service.bukcamp.BUKCamp;
import com.ktk.duka.mygoal.service.user.User;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class UserCampFilter {

    private User user;

    private BUKCamp camp;
}
