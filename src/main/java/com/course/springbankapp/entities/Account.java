package com.course.springbankapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
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
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactionHistory;

    public Account(Long id, int accountNumber, int agencyNumber, String clientName, String clientPhone, String accountType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.agencyNumber = agencyNumber;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.accountBalance = 0.0;
        this.accountLimit = 0.0;
        this.accountType = accountType;
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