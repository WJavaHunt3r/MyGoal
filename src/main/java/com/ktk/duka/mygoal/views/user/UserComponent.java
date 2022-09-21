package com.ktk.duka.mygoal.views.user;

import com.ktk.duka.mygoal.service.user.User;
import com.ktk.duka.mygoal.service.user.UserFilter;
import com.ktk.duka.mygoal.service.user.UserService;
import com.ktk.duka.mygoal.views.utils.CrudComponent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserComponent extends CrudComponent<UserFilter, User> {

    public UserComponent(UserService userService) {
        super(userService);
    }

    @Override
    protected void setupGridColumns(Grid<User> grid) {
        grid.addColumns(
                User.Fields.lastname,
                User.Fields.firstname,
                User.Fields.username,
                User.Fields.birthDate,
                User.Fields.role
        );

        setDefaultSorting(
                User.Fields.lastname,
                User.Fields.firstname
        );
    }

    @Override
    protected void setupFilterFields() {

    }
}
