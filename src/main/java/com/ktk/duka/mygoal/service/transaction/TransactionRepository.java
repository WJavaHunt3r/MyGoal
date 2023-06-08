package com.ktk.duka.mygoal.service.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("Select t FROM Transaction t Where " +
            "(lower(t.description) LIKE lower(?1) or ?1 IS NULL OR ?1 LIKE '')" )
    Page<Transaction> fetchByQuery(String description, LocalDate dataFrom, boolean inMyShare, Pageable pageable);

    @Query("Select COUNT (t) FROM Transaction t Where " +
            "(lower(t.description) LIKE lower(?1) or ?1 IS NULL OR ?1 LIKE '')" )
    long countByQuery(String description, LocalDate dateFrom, boolean inMyShare);
}
