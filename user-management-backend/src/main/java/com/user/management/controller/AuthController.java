package com.user.management.controller;

import com.user.management.dto.UserDTO;
import com.user.management.model.CustomUserDetails;
import com.user.management.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping(value="api/v1")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login (@RequestBody UserDTO loginDto) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword());

        // 1) Authenticate and get the authenticated Authentication
        Authentication authentication = authenticationManager.authenticate(authInputToken);

        // 2) (Recommended) Store it in the SecurityContext so future calls can read it
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3) Now the principal is a UserDetails
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String role = userDetails.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        String token = jwtService.generateToken(loginDto.getName());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful!");
        response.put("token", token);
        response.put("user", Map.of(
                "name", userDetails.getUsername(),
//                "image", userDetails.getImage(),
                "roles", role
        ));

        return ResponseEntity.ok(response);
    }
}
