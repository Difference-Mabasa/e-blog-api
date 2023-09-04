package com.enelosoft.eblog.eblogapi.service;

import com.enelosoft.eblog.eblogapi.dto.LoginDto;
import com.enelosoft.eblog.eblogapi.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register (RegisterDto registerDto);
}
