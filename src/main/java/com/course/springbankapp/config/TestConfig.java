package com.course.springbankapp.config;


import com.course.springbankapp.entities.Account;
import com.course.springbankapp.entities.Transaction;
import com.course.springbankapp.repositories.AccountRepository;
import com.course.springbankapp.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void run(String... args) {

        Account acc1 = new Account(null, 15161718, 842, "Fernando Rocha", "199825340796", "CC");
        Account acc2 = new Account(null, 19202122, 635, "Rafaela Pereira Dourados", "119846392101", "CP");
        accountRepository.saveAll(Arrays.asList(acc1, acc2));

        Transaction tra1 = new Transaction(null, new Date(), "Conta criada.", acc1);
        Transaction tra2 = new Transaction(null, new Date(), "Conta criada.", acc2);
        Transaction tra3 = new Transaction(null, new Date(), "Dados bancários atualizados.", acc2);
        transactionRepository.saveAll(Arrays.asList(tra1, tra2, tra3));

        acc1.setTransactionHistory(List.of(tra1));
        acc2.setTransactionHistory(List.of(tra2));

    }
}
