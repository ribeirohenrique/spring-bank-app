package com.course.springbankapp.resources;


import com.course.springbankapp.entities.Account;
import com.course.springbankapp.entities.dtos.*;
import com.course.springbankapp.services.AccountService;
import com.course.springbankapp.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts", produces = "application/json", consumes = "application/json")
public class AccountResource {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    //Encontrar conta por Id
    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Long id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok().body(new AccountDTO(account));
    }


    //Consultar saldo por Id
    @GetMapping(value = "/{id}/balance")
    public ResponseEntity<BalanceDTO> balance(@PathVariable Long id) {
        Double balance = accountService.balance(id);
        return ResponseEntity.ok().body(new BalanceDTO(balance));
    }

    //Consultar histórico da conta por Id
    @GetMapping(value = "/{id}/history")
    public ResponseEntity<List<TransactionDTO>> history(@PathVariable Long id) {
        List<TransactionDTO> transactionDTOList = transactionService.history(id);
        return ResponseEntity.ok().body(transactionDTOList);
    }

    //Criar nova conta
    @PostMapping
    public ResponseEntity<AccountDTO> insert(@RequestBody Account account) {
        account = accountService.insert(account);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(uri).body(new AccountDTO(account));
    }

    //Efetuar depósito
    @PutMapping(value = "/{id}/deposit")
    public ResponseEntity<AccountDTO> deposit(@PathVariable Long id, @RequestBody AmountDTO amount) {
        Account account = accountService.deposit(id, amount.getAmount());
        return ResponseEntity.ok().body(new AccountDTO(account));
    }

    //Efetuar mudança de limite da conta
    @PutMapping(value = "/{id}/changeAccountLimit")
    public ResponseEntity<AccountDTO> changeAccountLimit(@PathVariable Long id, @RequestBody AmountDTO amount) {
        Account account = accountService.changeAccountLimit(id, amount.getAmount());
        return ResponseEntity.ok().body(new AccountDTO(account));
    }

    //Efetuar saque
    @PutMapping(value = "/{id}/withdraw")
    public ResponseEntity<AccountDTO> withdraw(@PathVariable Long id, @RequestBody AmountDTO amount) {
        Account account = accountService.withdraw(id, amount.getAmount());
        return ResponseEntity.ok().body(new AccountDTO(account));
    }

    //Atualizar conta existente
    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id, @RequestBody Account account) {
        account = accountService.update(id, account);
        return ResponseEntity.ok().body(new AccountDTO(account));
    }

    //Efetuar transferência entre contas
    @PutMapping(value = "/{id}/transfer")
    public ResponseEntity<AccountDTO> transferBetweenAccounts(@PathVariable Long id, @RequestBody TransferDTO transferDTO) {
        Account account = accountService.transferBetweenAccounts(id, transferDTO.getId(), transferDTO.getAmount());
        return ResponseEntity.ok().body(new AccountDTO(account));
    }
}
