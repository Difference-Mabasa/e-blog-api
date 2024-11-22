package com.nedbank.stockvelapi.repository;

import com.nedbank.stockvelapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
