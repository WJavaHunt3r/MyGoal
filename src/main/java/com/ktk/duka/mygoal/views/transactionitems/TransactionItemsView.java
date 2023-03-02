package com.ktk.duka.mygoal.views.transactionitems;

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
@Route(value = TransactionItemsView.NAME, layout = MainLayout.class)
public class TransactionItemsView extends CrudView implements HasDynamicTitle {
    public static final String NAME = "transaction_items";

    public TransactionItemsView(TransactionItemsComponent crudComponent) {
        super(crudComponent);
    }

    @Override
    public String getPageTitle() {
        return UI.getCurrent().getTranslation(String.join(".", MainLayout.class.getName(), NAME));
    }
}
