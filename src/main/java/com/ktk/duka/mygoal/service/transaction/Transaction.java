package com.ktk.duka.mygoal.service.transaction;

import com.ktk.duka.mygoal.service.utils.CrudEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "TRANSACTIONS")
@FieldNameConstants
public class Transaction extends CrudEntity<Transaction, Long> {
    @Size(max = 150)
    @Column(name = "DESCRIPTION", length = 150)
    @NotNull
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "TRANSACTION_DATE")
    private LocalDate transactionDate;

    private boolean inMyShare;

}
