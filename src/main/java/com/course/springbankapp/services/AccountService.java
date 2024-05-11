package com.course.springbankapp.services;

import com.course.springbankapp.entities.Account;
import com.course.springbankapp.repositories.AccountRepository;
import com.course.springbankapp.resources.exceptions.BankingExceptions;
import com.course.springbankapp.services.exceptions.AccountBalanceException;
import com.course.springbankapp.services.exceptions.AccountLimitException;
import com.course.springbankapp.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    //Encontrar conta por Id
    public Account findById(Long id) {
        Optional<Account> object = accountRepository.findById(id);
        return object.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    //Consultar saldo por Id
    public Double balance(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return account.getAccountBalance();
    }

    //Criar nova conta
    public Account insert(Account account) {
        return accountRepository.save(account);
    }

    //Atualizar conta existente
    public Account update(Long id, Account account) {
        try {
            Account entity = accountRepository.getReferenceById(id);
            updateData(entity, account);
            return accountRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    //Efetuar depósito por id
    public Account deposit(Long id, Double amount) {
        try {
            Account entity = accountRepository.getReferenceById(id);
            entity.setAccountBalance(entity.getAccountBalance() + amount);
            return accountRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

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
            return accountRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }

    }

    //Efetuar depósito por id
    public Account changeAccountLimit(Long id, Double amount) {
        try {
            Account entity = accountRepository.getReferenceById(id);
            entity.setAccountLimit(entity.getAccountLimit() + amount);
            return accountRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

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
            //depois deposita no Receiver
            accountReceiver.setAccountBalance(accountReceiver.getAccountBalance() + amount);
            accountRepository.save(accountSender);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(accountNumberReceiver);
        } catch (BankingExceptions e) {
            System.out.println(e.getMessage());
        }
        return accountRepository.save(accountSender);
    }

    //Método auxiliar para atualizar apenas o nome e telefone
    private void updateData(Account entity, Account account) {
        entity.setClientName(account.getClientName());
        entity.setClientPhone(account.getClientPhone());

    }
}
