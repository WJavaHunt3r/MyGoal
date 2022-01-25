package com.ktk.duka.mygoal.views.logout;

import com.ktk.duka.mygoal.security.SecurityService;
import com.ktk.duka.mygoal.views.login.LoginView;
import com.ktk.duka.mygoal.views.utils.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Route(value = LogoutView.NAME)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogoutView extends VerticalLayout implements BeforeEnterObserver, HasDynamicTitle {
    public static final String NAME = "logout";

    private final SecurityService securityService;

    public LogoutView(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        UI.getCurrent().navigate(LoginView.class);
        securityService.logout();
    }

    @Override
    public String getPageTitle() {
        return UI.getCurrent().getTranslation(String.join(".", MainLayout.class.getName(), NAME));
    }
}
