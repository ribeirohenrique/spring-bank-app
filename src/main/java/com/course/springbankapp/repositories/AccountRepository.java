package com.course.springbankapp.repositories;

import com.course.springbankapp.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
