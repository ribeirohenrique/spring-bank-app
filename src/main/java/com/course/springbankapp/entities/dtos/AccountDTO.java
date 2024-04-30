package com.course.springbankapp.entities.dtos;

import com.course.springbankapp.entities.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private int accountNumber;
    private int agencyNumber;
    private String clientName;
    private String clientPhone;
    private Double balance;
    private double accountLimit;
    private String accountType;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.agencyNumber = account.getAgencyNumber();
        this.clientName = account.getClientName();
        this.clientPhone = account.getClientPhone();
        this.balance = account.getAccountBalance();
        this.accountLimit = account.getAccountLimit();
        this.accountType = account.getAccountType();
    }
}
