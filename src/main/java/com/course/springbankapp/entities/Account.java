package com.course.springbankapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int accountNumber;
    private int agencyNumber;
    private String clientName;
    private String clientPhone;
    private double accountBalance;
    private double accountLimit;
    private String accountType;

    @JsonIgnore
    @OneToMany(mappedBy = "accountNumber")
    private List<Transaction> transactionHistory;

    public Account(int accountNumber, String clientName, double accountLimit, String accountType) {
        this.accountNumber = accountNumber;
        this.agencyNumber = 1;
        this.clientName = clientName;
        this.accountLimit = accountLimit;
        this.accountType = accountType;
        this.accountBalance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    @Override
    public String toString() {
        return
                "Dados da conta bancária: \n" +
                        "Account Name: " + getClientName() + "\n" +
                        "Account Number: " + getAccountNumber() + "\n" +
                        "Account Agency: " + getAgencyNumber() + "\n" +
                        "Account Balance: " + String.format("%.2f", getAccountBalance()) + "\n" +
                        "Account Limit: " + String.format("%.2f", getAccountLimit()) + "\n" +
                        "Account Type: " + getAccountType() + "\n" +
                        "============================================\n";
    }
}