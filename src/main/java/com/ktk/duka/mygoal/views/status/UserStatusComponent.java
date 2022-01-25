package com.ktk.duka.mygoal.views.status;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserStatusComponent extends VerticalLayout {
}
