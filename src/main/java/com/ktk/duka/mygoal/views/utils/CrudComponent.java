package com.ktk.duka.mygoal.views.utils;

import com.ktk.duka.mygoal.service.utils.CrudEntity;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.InitializingBean;

public class CrudComponent<F, E extends CrudEntity<E, ?>> extends VerticalLayout implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
