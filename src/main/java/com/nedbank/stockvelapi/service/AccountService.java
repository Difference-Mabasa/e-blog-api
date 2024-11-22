package com.nedbank.stockvelapi.service;

import com.nedbank.stockvelapi.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    List<AccountDto> getAllAccounts();
    AccountDto updateAccount(Long id, AccountDto accountDto);
    String deleteAccount(Long id);
}
