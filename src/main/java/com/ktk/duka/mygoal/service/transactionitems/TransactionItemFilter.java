package com.ktk.duka.mygoal.service.transactionitems;

import com.ktk.duka.mygoal.service.transaction.Transaction;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Data
@FieldNameConstants
public class TransactionItemFilter {
    public String username;

    public String description;

    public LocalDate transactionDate;

    public Transaction transaction;
}
