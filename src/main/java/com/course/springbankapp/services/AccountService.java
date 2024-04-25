package com.course.springbankapp.services;

import com.course.springbankapp.entities.Account;
import com.course.springbankapp.repositories.AccountRepository;
import com.course.springbankapp.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        Optional<Account> object = accountRepository.findById(id);
        return object.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Account insert(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Long id) {
        try {
            accountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    public Account update(Long id, Account account) {
        try {
            Account entity = accountRepository.getReferenceById(id);
            updateData(entity, account);
            return accountRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Account entity, Account account) {
        entity.setClientName(account.getClientName());
        entity.setClientPhone(account.getClientPhone());

    }
}
