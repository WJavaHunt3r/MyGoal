package com.ktk.duka.mygoal.rest;

import com.ktk.duka.mygoal.service.transaction.Transaction;
import com.ktk.duka.mygoal.service.transaction.TransactionRepository;
import com.ktk.duka.mygoal.service.transactionitems.TransactionItemRepository;
import com.ktk.duka.mygoal.service.user.User;
import com.ktk.duka.mygoal.service.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyGoalRestController {

    private TransactionRepository transactionRepository;
    private TransactionItemRepository transactionItemRepository;
    private UserRepository userRepository;

    public MyGoalRestController(TransactionRepository transactionRepository, TransactionItemRepository transactionItemRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionItemRepository = transactionItemRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions() {
        return ResponseEntity.ok(transactionRepository.findAll());
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> postTransaction(@RequestBody @Valid Transaction transaction){
        return ResponseEntity.ok(transactionRepository.save(transaction));
    }

}
