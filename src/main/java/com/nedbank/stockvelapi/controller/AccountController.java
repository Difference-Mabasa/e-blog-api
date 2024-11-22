package com.nedbank.stockvelapi.controller;

import com.nedbank.stockvelapi.dto.AccountDto;
import com.nedbank.stockvelapi.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("create")

    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDto, @PathVariable Long id){
        return new ResponseEntity<>(accountService.updateAccount(id, accountDto), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }
}
