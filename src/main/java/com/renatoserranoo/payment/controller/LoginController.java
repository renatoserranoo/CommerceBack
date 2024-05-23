package com.renatoserranoo.payment.controller;

import com.renatoserranoo.payment.dto.AuthenticationRequest;
import com.renatoserranoo.payment.dto.AuthenticationResponse;
import com.renatoserranoo.payment.entity.User;
import com.renatoserranoo.payment.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest authenticationRequest){

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(), authenticationRequest.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new AuthenticationResponse(token));

    }
}
