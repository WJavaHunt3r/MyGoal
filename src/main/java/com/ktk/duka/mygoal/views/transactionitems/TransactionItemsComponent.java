package com.ktk.duka.mygoal.views.transactionitems;

import com.ktk.duka.mygoal.service.transaction.TransactionFilter;
import com.ktk.duka.mygoal.service.transactionitems.TransactionItem;
import com.ktk.duka.mygoal.service.transactionitems.TransactionItemFilter;
import com.ktk.duka.mygoal.service.transactionitems.TransactionItemService;
import com.ktk.duka.mygoal.views.utils.CrudComponent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TransactionItemsComponent extends CrudComponent<TransactionItemFilter, TransactionItem> {

    public TransactionItemsComponent(TransactionItemService crudService) {
        super(crudService);
    }

    @Override
    protected void setupGridColumns(Grid<TransactionItem> grid) {
        grid.addColumns(
                TransactionItem.Fields.user,
                TransactionItem.Fields.amount,
                TransactionItem.Fields.transactionDate,
                TransactionItem.Fields.description

        );

        setDefaultSorting(
                TransactionItem.Fields.user,
                TransactionItem.Fields.transactionDate
        );

    }

    @Override
    protected List<String> setupFilterFields() {
        return List.of(
                TransactionItemFilter.Fields.transaction,
                TransactionItemFilter.Fields.username,
                TransactionItemFilter.Fields.description
        );
    }

//    @Override
//    protected HasValue<?, ?> createFilterFieldFor(String property) {
//        if (TransactionItemFilter.Fields.transactionDate.equals(property)) {
//            return buildDateField(property);
//        }
//        return null;
//    }
}
