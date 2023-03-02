package com.ktk.duka.mygoal.service.transactionitems;

import com.ktk.duka.mygoal.service.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {

    @Query("Select t FROM TransactionItem t WHERE t.transaction = ?1 " +
            "AND (lower(t.user.username) LIKE lower(?2) or ?2 IS NULL OR ?2 LIKE '')" +
            "AND (lower(t.description) LIKE lower(?3) or ?3 IS NULL OR ?3 LIKE '')" +
            "AND t.transactionDate > ?4 ")
    Page<TransactionItem> fetchByQuery(Transaction transaction, String username, String description, LocalDate dataFrom, Pageable pageable);

    @Query("Select COUNT (t) FROM TransactionItem t WHERE t.transaction = ?1 " +
            "AND (lower(t.user.username) LIKE lower(?2) or ?2 IS NULL OR ?2 LIKE '')" +
            "AND (lower(t.description) LIKE lower(?3) or ?3 IS NULL OR ?3 LIKE '')" +
            "AND t.transactionDate > ?4 ")
    long countByQuery(Transaction transaction, String username, String description, LocalDate dateFrom);
}
