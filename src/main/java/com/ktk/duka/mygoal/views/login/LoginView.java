package com.ktk.duka.mygoal.views.login;

import com.ktk.duka.mygoal.config.BusinessContextConfiguration;
import com.ktk.duka.mygoal.service.entity.user.UserService;
import com.ktk.duka.mygoal.service.utils.UserImportService;
import com.ktk.duka.mygoal.views.utils.MainLayout;
import com.ktk.duka.mygoal.views.utils.ComponentUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringComponent
@RouteAlias(value = "")
@Route(value = LoginView.NAME)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginView extends VerticalLayout implements BeforeEnterObserver, HasDynamicTitle, PageConfigurator {
    public static final String NAME = "login";
    private final LoginForm loginForm;
    private final BusinessContextConfiguration config;
    private UserImportService userImportService;

    public LoginView(BusinessContextConfiguration config, UserImportService userImportService){
        this.userImportService = userImportService;
        this.config = config;

        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        
        LoginI18n provider = ComponentUtils.loginFormTranslationProvider();
        loginForm = new LoginForm();
        loginForm.setAction(NAME);
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.setI18n(provider);

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.add(createHeader(provider), loginForm);
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        
        this.add(layout);
        userImportService.importUsersFromCsv();
    }

    private Component createHeader(LoginI18n provider) {
//        Image logo = new Image(config.getCompanyLogoPath(), config.getCompanyName());
//        logo.setWidth("100px");

        H1 title = new H1(provider.getHeader().getTitle());
        title.getStyle().set("color", "var(--lumo-tint)");
        title.getStyle().set("margin-top", "1rem");
        title.getStyle().set("margin-bottom", "0rem");

        H3 environment = new H3(config.getEnvironment());
        environment.getStyle().set("margin", "0rem");
        environment.getStyle().set("margin-top", "1rem");
        environment.getStyle().set("padding", "0rem");

        VerticalLayout layout = new VerticalLayout(title, new Hr());
        layout.setWidth("22rem");
        layout.getStyle().set("padding", "3rem");
        layout.getStyle().set("background-color", "var(--lumo-shade-10pct)");
        layout.getStyle().set("border-radius", "var(--lumo-border-radius-s)");
        layout.setHeightFull();
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        return layout;
    }

    @Override
    public String getPageTitle() {
        return String.format("%s" ,
                UI.getCurrent().getTranslation(String.join(".", MainLayout.class.getName(), NAME)));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.setError(true);
        }
    }

    @Override
    public void configurePage(InitialPageSettings initialPageSettings) {
        Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put("rel", "shortcut icon");
        initialPageSettings.addLink("icons/icon.png", attributes);
    }
}
