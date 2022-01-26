package com.ktk.duka.mygoal.views.user;

import com.ktk.duka.mygoal.service.user.User;
import com.ktk.duka.mygoal.service.user.UserFilter;
import com.ktk.duka.mygoal.views.utils.CrudComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserComponent extends CrudComponent<UserFilter, User> {
}
