package com.ktk.duka.mygoal.service.user;

import com.ktk.duka.mygoal.enums.Role;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import java.time.LocalDate;

@Data
@FieldNameConstants
public class UserFilter {

    private String username;

    private String firstname;

    private String lastname;

    private Role role;

    private LocalDate birthDateFrom;

    private boolean isU20;

}
