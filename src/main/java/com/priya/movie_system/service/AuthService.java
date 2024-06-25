package com.priya.movie_system.service;

import com.priya.movie_system.dto.AuthenticationRequest;
import com.priya.movie_system.dto.AuthenticationResponse;
import com.priya.movie_system.dto.RegisterRequest;
import com.priya.movie_system.model.Role;
import com.priya.movie_system.model.Users;
import com.priya.movie_system.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = request.getRole() == 1 ? Role.USER : Role.ADMIN;
        Users user = new Users();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setRole(role);
        if(userRepository.findByUsername(request.getUsername()).isEmpty()){
            userRepository.save(user);
        }
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role.name()));
        System.out.println("authorities = " + authorities);
        String jwtToken = jwtService.generateToken(new User(user.getUsername(), user.getPassword(),user.getAuthorities()));

        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        Users user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
