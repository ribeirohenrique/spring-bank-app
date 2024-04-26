package com.course.springbankapp.services;

import com.course.springbankapp.entities.Account;
import com.course.springbankapp.repositories.AccountRepository;
import com.course.springbankapp.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //Método auxiliar para atualizar apenas o nome e telefone
    private void updateData(Account entity, Account account) {
        entity.setClientName(account.getClientName());
        entity.setClientPhone(account.getClientPhone());

    }
}
