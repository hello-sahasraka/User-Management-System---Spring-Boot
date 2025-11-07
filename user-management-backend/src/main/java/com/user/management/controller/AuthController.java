package com.user.management.controller;

import com.user.management.dto.UserDTO;
import com.user.management.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="api/v1")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login (@RequestBody UserDTO loginDto) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword());
        authenticationManager.authenticate(authInputToken);

        String token = jwtService.generateToken(loginDto.getName());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful!");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
