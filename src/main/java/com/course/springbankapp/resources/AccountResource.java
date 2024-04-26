package com.course.springbankapp.resources;


import com.course.springbankapp.entities.Account;
import com.course.springbankapp.entities.dtos.BalanceDTO;
import com.course.springbankapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/accounts")
public class AccountResource {

    @Autowired
    private AccountService accountService;

    //Encontrar conta por Id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> findbyId(@PathVariable Long id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok().body(account);
    }

    //Consultar saldo por Id
    @GetMapping(value = "/{id}/balance")
    public ResponseEntity<BalanceDTO> balance(@PathVariable Long id) {
        Double balance = accountService.balance(id);
        return ResponseEntity.ok().body(new BalanceDTO(balance));
    }

    //Criar nova conta
    @PostMapping
    public ResponseEntity<Account> insert(@RequestBody Account account) {
        account = accountService.insert(account);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(uri).body(account);
    }

    //Atualizar conta existente
    @PutMapping(value = "/{id}")
    public ResponseEntity<Account> update(@PathVariable Long id, @RequestBody Account account) {
        account = accountService.update(id, account);
        return ResponseEntity.ok().body(account);
    }
}
