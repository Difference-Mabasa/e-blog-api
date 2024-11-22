package com.nedbank.stockvelapi.dto;

import com.nedbank.stockvelapi.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String accountNumber;
    private String description;
    private String goal;
    private double balance;
    private List<User> members;
}
