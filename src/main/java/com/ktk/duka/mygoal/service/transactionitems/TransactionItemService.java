package com.ktk.duka.mygoal.service.transactionitems;


import com.ktk.duka.mygoal.service.utils.CrudService;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionItemService extends CrudService<TransactionItemFilter, TransactionItem, Long> {

    @Getter
    private TransactionItemRepository repository;

    public TransactionItemService(TransactionItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<TransactionItem, Long> getRepository() {
        return repository;
    }

    @Override
    public Class<TransactionItem> getEntityClass() {
        return TransactionItem.class;
    }

    @Override
    public Class<TransactionItemFilter> getFilterClass() {
        return TransactionItemFilter.class;
    }

    @Override
    public TransactionItem createEntity() {
        return new TransactionItem();
    }

    @Override
    public TransactionItemFilter createFilter() {
        return new TransactionItemFilter();
    }

    @Override
    public Page<TransactionItem> fetchByQuery(TransactionItemFilter filter, Pageable pageable) {
        return repository.fetchByQuery(filter.getTransaction(),
                String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getUsername(), "")),
                String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getDescription(), "")),
                filter.getTransactionDate(), pageable);
    }

    @Override
    public long countByQuery(TransactionItemFilter filter) {
        return repository.countByQuery(filter.getTransaction(),
                String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getUsername(), "")),
                String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getDescription(), "")),
                filter.getTransactionDate());
    }
}
