package com.course.springbankapp.entities.dtos;

import com.course.springbankapp.entities.Account;
import com.course.springbankapp.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private Date date;
    private String description;
    private Account account;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.date = transaction.getDate();
        this.description = transaction.getDescription();
        this.account = transaction.getAccount(); // Obtém o id da conta associada
    }
}
