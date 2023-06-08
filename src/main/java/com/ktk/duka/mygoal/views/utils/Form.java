package com.ktk.duka.mygoal.views.utils;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

public class Form<F> extends VerticalLayout {
    protected Binder<F> filterFormBinder;

    public Form(Binder<F> filterFormBinder) {
        this.filterFormBinder = filterFormBinder;

    }

}
