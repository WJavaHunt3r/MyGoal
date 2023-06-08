package com.ktk.duka.mygoal.views.user;

import com.ktk.duka.mygoal.service.user.User;
import com.ktk.duka.mygoal.service.user.UserFilter;
import com.ktk.duka.mygoal.service.user.UserService;
import com.ktk.duka.mygoal.views.utils.CrudComponent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserComponent extends CrudComponent<UserFilter, User> {

    public UserComponent(UserService userService) {
        super(userService);
    }

    @Override
    protected void setupGridColumns(Grid<User> grid) {
        grid.addColumns(
                User.Fields.myShareID,
                User.Fields.lastname,
                User.Fields.firstname,
                User.Fields.username,
                User.Fields.role
        );

        grid.addColumn(item -> Period.between(item.getBirthDate() , LocalDate.now() ).getYears()).setKey("age");

        setDefaultSorting(
                User.Fields.lastname,
                User.Fields.firstname
        );
    }

    @Override
    protected List<String> setupFilterFields() {
        return List.of(
                UserFilter.Fields.firstname,
                UserFilter.Fields.lastname,
                UserFilter.Fields.role,
                UserFilter.Fields.username,
                UserFilter.Fields.isU20
        );
    }

    @Override
    protected HasValue<?, ?> createFilterFieldFor(String property) {
        if(UserFilter.Fields.birthDateFrom.equals(property)){
            return buildDateField(property);
        }
        if(UserFilter.Fields.role.equals(property)){
            return buildUserSelectField(property);
        }
        if(UserFilter.Fields.isU20.equals(property)){
            return buildYesNoField(property);
        }
        return null;
    }
}
