package com.ktk.duka.mygoal.service.transaction;

import com.ktk.duka.mygoal.service.utils.CrudService;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends CrudService<TransactionFilter, Transaction, Long> {

    @Getter
    private TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<Transaction, Long> getRepository() {
        return repository;
    }

    @Override
    public Class<Transaction> getEntityClass() {
        return Transaction.class;
    }

    @Override
    public Class<TransactionFilter> getFilterClass() {
        return TransactionFilter.class;
    }

    @Override
    public Transaction createEntity() {
        return new Transaction();
    }

    @Override
    public TransactionFilter createFilter() {
        return new TransactionFilter();
    }

    @Override
    public Page<Transaction> fetchByQuery(TransactionFilter filter, Pageable pageable) {
        return repository.fetchByQuery(String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getDescription(), "")),
                filter.getTransactionDate(), pageable);
    }

    @Override
    public long countByQuery(TransactionFilter filter) {
        return repository.countByQuery(String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getDescription(), "")),
                filter.getTransactionDate());
    }
}
