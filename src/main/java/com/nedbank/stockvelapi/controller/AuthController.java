package com.nedbank.stockvelapi.controller;

import com.nedbank.stockvelapi.dto.JwtAuthResponse;
import com.nedbank.stockvelapi.dto.LoginDto;
import com.nedbank.stockvelapi.dto.RegisterDto;
import com.nedbank.stockvelapi.model.User;
import com.nedbank.stockvelapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login (@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register (@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("query") String query){
        return ResponseEntity.ok(authService.searchUsers(query));
    }
}
