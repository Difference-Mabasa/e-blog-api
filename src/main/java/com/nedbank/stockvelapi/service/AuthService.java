package com.nedbank.stockvelapi.service;

import com.nedbank.stockvelapi.dto.LoginDto;
import com.nedbank.stockvelapi.dto.RegisterDto;
import com.nedbank.stockvelapi.model.User;

import java.util.List;

public interface AuthService {
    String login(LoginDto loginDto);
    String register (RegisterDto registerDto);

    List<User> searchUsers(String user);
}
