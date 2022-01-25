package com.ktk.duka.mygoal.views.utils;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CrudView extends VerticalLayout {
    public CrudView(CrudComponent<?, ?> crudComponent) {
        setSizeFull();
        add(crudComponent);
    }
}