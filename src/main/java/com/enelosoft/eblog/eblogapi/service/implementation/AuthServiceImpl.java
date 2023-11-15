package com.enelosoft.eblog.eblogapi.service.implementation;

import com.enelosoft.eblog.eblogapi.dto.LoginDto;
import com.enelosoft.eblog.eblogapi.dto.RegisterDto;
import com.enelosoft.eblog.eblogapi.exception.EBlogAPIException;
import com.enelosoft.eblog.eblogapi.model.Role;
import com.enelosoft.eblog.eblogapi.model.User;
import com.enelosoft.eblog.eblogapi.repository.RoleRepository;
import com.enelosoft.eblog.eblogapi.repository.UserRepository;
import com.enelosoft.eblog.eblogapi.security.JwtTokenProvider;
import com.enelosoft.eblog.eblogapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication((authentication));

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new EBlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        } else if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new EBlogAPIException(HttpStatus.BAD_REQUEST, "Email already already exists");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = (roleRepository.findByName("ROLE_USER").get());
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered!";
    }

    @Override
    public List<User> searchUsers(String query) {
        return userRepository.searchUsers(query);
    }


}
