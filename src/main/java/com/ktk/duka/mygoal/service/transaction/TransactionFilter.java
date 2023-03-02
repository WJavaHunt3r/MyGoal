package com.ktk.duka.mygoal.service.transaction;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Data
@FieldNameConstants
public class TransactionFilter {
    public String description;
    public LocalDate transactionDate;
}
