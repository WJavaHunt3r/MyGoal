package com.ktk.duka.mygoal.views.transactions;

import com.ktk.duka.mygoal.service.transaction.Transaction;
import com.ktk.duka.mygoal.service.transaction.TransactionFilter;
import com.ktk.duka.mygoal.service.transaction.TransactionService;
import com.ktk.duka.mygoal.views.utils.CrudComponent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TransactionComponent extends CrudComponent<TransactionFilter, Transaction> {

    public TransactionComponent(TransactionService crudService) {
        super(crudService);
    }

    @Override
    protected void setupGridColumns(Grid<Transaction> grid) {
        grid.addColumns(
                Transaction.Fields.description,
                Transaction.Fields.transactionDate
        );

        setDefaultSorting(
                Transaction.Fields.transactionDate
        );

    }

    @Override
    protected List<String> setupFilterFields() {
        return List.of(
                TransactionFilter.Fields.description,
                TransactionFilter.Fields.transactionDate
        );
    }

    @Override
    protected HasValue<?, ?> createFilterFieldFor(String property) {
        if (TransactionFilter.Fields.transactionDate.equals(property)) {
            return buildDateField(property);
        }
        return null;
    }
}
