package com.ktk.duka.mygoal.views.user;

import com.ktk.duka.mygoal.views.utils.CrudView;
import com.ktk.duka.mygoal.views.utils.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = UserView.NAME, layout = MainLayout.class)
public class UserView extends CrudView implements HasDynamicTitle {

    public static final String NAME = "users";

    public UserView(UserComponent crudComponent) {
        super(crudComponent);
    }

    @Override
    public String getPageTitle() {
        return UI.getCurrent().getTranslation(String.join(".", MainLayout.class.getName(), NAME));
    }
}
