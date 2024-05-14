package com.course.springbankapp.services;

import com.course.springbankapp.entities.Transaction;
import com.course.springbankapp.entities.dtos.TransactionDTO;
import com.course.springbankapp.repositories.TransactionRepository;
import com.course.springbankapp.resources.exceptions.BankingExceptions;
import com.course.springbankapp.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    private static void deleteFile(String fileName) {
        File transactionHistory = new File(fileName);
        try {
            if (transactionHistory.exists()) {
                if (transactionHistory.delete()) {
                    throw new BankingExceptions("Erro ao deletar o histórico de transações");
                }
            }
        } catch (BankingExceptions exceptions) {
            System.out.println(exceptions.getMessage());
        }

    }

    public List<TransactionDTO> history(Long accountId) {
        List<TransactionDTO> transactionDTO = new ArrayList<>();
        String fileName = "account_" + accountId + "_history.csv";
        deleteFile(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
            writer.write("AccountNumber,ClientName,Description,Date");
            writer.newLine();
            for (Transaction transaction : transactions) {
                transactionDTO.add(new TransactionDTO(transaction));
                writer.append(transaction.getAccount().getAccountNumber()
                        + "," + transaction.getAccount().getClientName()
                        + "," + transaction.getDescription()
                        + "," + transaction.getDate());
                writer.newLine();
            }
            transactions.clear();
        } catch (IOException e) {
            System.out.println("Erro ao salvar o histórico de transações: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(accountId);
        }
        return transactionDTO;
    }
}
