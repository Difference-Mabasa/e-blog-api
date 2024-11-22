package com.nedbank.stockvelapi.service.implementation;

import com.nedbank.stockvelapi.dto.AccountDto;
import com.nedbank.stockvelapi.exception.ResourceNotFoundException;
import com.nedbank.stockvelapi.model.Account;
import com.nedbank.stockvelapi.repository.AccountRepository;
import com.nedbank.stockvelapi.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private ModelMapper modelMapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = modelMapper.map(accountDto, Account.class);
        Account newAccount = accountRepository.save(account);
        return modelMapper.map(newAccount, AccountDto.class);
    }

    @Override
    public AccountDto getAccountById(Long categoryId) {
        Optional<Account> category = Optional.ofNullable(accountRepository.findById(categoryId).orElseThrow(() ->
            new ResourceNotFoundException("Account", "Id", categoryId)
        ));

        return modelMapper.map(category.get(), AccountDto.class);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> categories = accountRepository.findAll();

        return categories.stream().map((account -> modelMapper.map(account, AccountDto.class)))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto updateAccount(Long categoryId, AccountDto accountDto) {
        Account account = accountRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Account", "Id", categoryId)
        );

        account.setDescription(accountDto.getDescription());
        account.setGoal(accountDto.getGoal());

        return modelMapper.map(accountRepository.save(account), AccountDto.class);
    }

    @Override
    public String deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Account", "Id", id)
        );

        accountRepository.delete(account);

        return "Account successfully deleted";
    }

    private String generateJoinLink(String accountNumber) {
        String baseUrl = "https://stockvel.com/join";
        return String.format("%s?accountNumber=%s", baseUrl, accountNumber);
    }
}
