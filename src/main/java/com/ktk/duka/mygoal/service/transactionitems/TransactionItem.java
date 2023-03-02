package com.ktk.duka.mygoal.service.transactionitems;

import com.ktk.duka.mygoal.service.transaction.Transaction;
import com.ktk.duka.mygoal.service.user.User;
import com.ktk.duka.mygoal.service.utils.CrudEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "TRANSACTION_ITEMS")
@FieldNameConstants
public class TransactionItem extends CrudEntity<TransactionItem, Long> {
    @Size(max = 150)
    @Column(name = "DESCRIPTION", length = 50)
    @NotNull
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "TRANSACTION_DATE")
    private LocalDate transactionDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "USERS")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TRANSCTIONS")
    private Transaction transaction;

    @NotNull
    @Column(name = "AMOUNT")
    private Integer amount;

}
