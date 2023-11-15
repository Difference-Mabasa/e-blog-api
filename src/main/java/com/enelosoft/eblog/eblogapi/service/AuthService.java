package com.enelosoft.eblog.eblogapi.service;

import com.enelosoft.eblog.eblogapi.dto.LoginDto;
import com.enelosoft.eblog.eblogapi.dto.RegisterDto;
import com.enelosoft.eblog.eblogapi.model.User;

import java.util.List;

public interface AuthService {
    String login(LoginDto loginDto);
    String register (RegisterDto registerDto);

    List<User> searchUsers(String user);
}
