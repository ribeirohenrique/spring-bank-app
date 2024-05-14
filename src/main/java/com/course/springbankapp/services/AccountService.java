package com.course.springbankapp.services;

import com.course.springbankapp.entities.Account;
import com.course.springbankapp.entities.Transaction;
import com.course.springbankapp.repositories.AccountRepository;
import com.course.springbankapp.repositories.TransactionRepository;
import com.course.springbankapp.resources.exceptions.BankingExceptions;
import com.course.springbankapp.services.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    //Encontrar conta por Id
    public Account findById(Long id) {
        Optional<Account> object = accountRepository.findById(id);
        return object.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    //Consultar saldo por Id
    public Double balance(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        transactionRepository.save(new Transaction(null, new Date(), "Consulta de saldo realizada.", account));
        return account.getAccountBalance();
    }

    //Criar nova conta
    public Account insert(Account account) {
        try {
            accountRepository.save(account);
            transactionRepository.save(new Transaction(null, new Date(), "Conta criada.", account));
            return account;
        } catch (DataIntegrityViolationException e) {
            throw new AccountDuplicateException(account.getAccountNumber());
        }

    }

    //Atualizar conta existente
    public Account update(Long id, Account account) {
        try {
            Account entity = accountRepository.getReferenceById(id);
            updateData(entity, account);
            accountRepository.save(entity);
            transactionRepository.save(new Transaction(null, new Date(), "Dados bancários atualizados.", entity));
            return entity;
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    //Efetuar depósito por id
    public Account deposit(Long id, Double amount) {
        try {
            Account entity = accountRepository.getReferenceById(id);
            entity.setAccountBalance(entity.getAccountBalance() + amount);
            accountRepository.save(entity);
            transactionRepository.save(new Transaction(null, new Date(), "Depósito bancário efetuado.", entity));
            return entity;
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    //Efetuar saque por id
    public Account withdraw(Long id, Double amount) {
        try {
            Account entity = accountRepository.getReferenceById(id);
            if (entity.getAccountBalance() < amount) {
                throw new AccountBalanceException(id);
            }
            if (entity.getAccountLimit() < amount) {
                throw new AccountLimitException(id);
            }
            entity.setAccountBalance(entity.getAccountBalance() - amount);
            accountRepository.save(entity);
            transactionRepository.save(new Transaction(null, new Date(), "Saque bancário efetuado.", entity));
            return entity;
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }

    }

    //Efetuar mudança de limite de saque por id
    public Account changeAccountLimit(Long id, Double amount) {
        try {
            Account entity = accountRepository.getReferenceById(id);
            entity.setAccountLimit(entity.getAccountLimit() + amount);
            accountRepository.save(entity);
            transactionRepository.save(new Transaction(null, new Date(), "Limite de saque bancário alterado.", entity));
            return entity;
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    //Efetuar transferência entre contas
    public Account transferBetweenAccounts(Long accountNumberSender, Long accountNumberReceiver, Double amount) {
        LocalDateTime currentTime = LocalDateTime.now();
        Account accountSender = accountRepository.getReferenceById(accountNumberSender);
        Account accountReceiver = accountRepository.getReferenceById(accountNumberReceiver);
        try {
            // Verifique se o horário atual está entre 21h e 6h
            if (currentTime.getHour() >= 21 || currentTime.getHour() < 6) {
                throw new BankingExceptions("Transferências não são permitidas entre as 21h e 6h.");
            }
            if (accountSender.getAccountBalance() < amount) {
                throw new AccountBalanceException("Erro: O valor a ser transferido é maior que o saldo atual.");
            }
            if (accountSender.getAccountLimit() < amount) {
                throw new AccountLimitException("Erro: O valor a ser transferido é maior que o limite atual.");
            }
            //primeiro subtrai do Sender
            accountSender.setAccountBalance(accountSender.getAccountBalance() - amount);
            accountRepository.save(accountSender);

            //depois deposita no Receiver
            accountReceiver.setAccountBalance(accountReceiver.getAccountBalance() + amount);
            accountRepository.save(accountReceiver);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(accountNumberReceiver);
        } catch (BankingExceptions e) {
            System.out.println(e.getMessage());
        }
        transactionRepository.save(new Transaction(null, new Date(), ("Transferência de " + accountSender.getAccountNumber() + " para " + accountReceiver.getAccountNumber() + " efetuada com sucesso."), accountSender));
        return accountSender;
    }

    //Método auxiliar para atualizar apenas o nome e telefone
    private void updateData(Account entity, Account account) {
        entity.setClientName(account.getClientName());
        entity.setClientPhone(account.getClientPhone());

    }
}
