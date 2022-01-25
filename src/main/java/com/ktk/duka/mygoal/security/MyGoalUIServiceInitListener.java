package com.ktk.duka.mygoal.security;

import com.ktk.duka.mygoal.config.BusinessContextConfiguration;
import com.ktk.duka.mygoal.views.login.LoginView;
import com.ktk.duka.mygoal.views.user.UserView;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MyGoalUIServiceInitListener implements VaadinServiceInitListener {

    private final SecurityService securityService;
    private final BusinessContextConfiguration businessContext;

    public MyGoalUIServiceInitListener(BusinessContextConfiguration businessContext, SecurityService securityService) {
        this.businessContext = businessContext;
        this.securityService = securityService;
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            uiEvent.getUI().addBeforeEnterListener(enterEvent -> {
                if (!LoginView.class.equals(enterEvent.getNavigationTarget())) {
                    if (!SecurityUtils.isUserLoggedIn()) {
                        enterEvent.rerouteTo(LoginView.class);
                    } else if (!securityService.accessGranted(enterEvent.getNavigationTarget())) {
                        enterEvent.rerouteTo(UserView.class);
                    }
                }
            });  

            uiEvent.getUI().addBeforeEnterListener(enterEvent -> {
                if (LoginView.class.equals(enterEvent.getNavigationTarget())) {
                    enterEvent.getUI().setLocale(businessContext.getDefaultLocale().orElse(Locale.ENGLISH));
                }
            });
        });

        event.getSource().addSessionInitListener(sessionEvent -> sessionEvent.getSession().setLocale(businessContext.getDefaultLocale()
                .orElse(Locale.ENGLISH)));
    }
}
