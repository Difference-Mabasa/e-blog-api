package com.nedbank.stockvelapi.model;

import com.nedbank.stockvelapi.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name="accounts"
)
public class Account {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String accountNumber;
    private String description;
    private String goal;
    private double balance;
    private AccountStatus status = AccountStatus.INITIALIZING;
    private String joinLink;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> members;
}
