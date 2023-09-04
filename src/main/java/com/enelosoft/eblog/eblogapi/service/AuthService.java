package com.enelosoft.eblog.eblogapi.service;

import com.enelosoft.eblog.eblogapi.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
