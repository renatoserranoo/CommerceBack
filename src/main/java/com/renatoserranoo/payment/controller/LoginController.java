package com.renatoserranoo.payment.controller;

import com.renatoserranoo.payment.dto.AuthenticationRequest;
import com.renatoserranoo.payment.dto.AuthenticationResponse;
import com.renatoserranoo.payment.entity.User;
import com.renatoserranoo.payment.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest authenticationRequest,
                                HttpServletResponse response){

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(), authenticationRequest.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();
        var name = user.getName();
        var role = user.getRole();
        var token = tokenService.generateToken((User) auth.getPrincipal());

        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        System.out.println("Cookie JWT_TOKEN definido com valor: " + token);


        return ResponseEntity.ok(new AuthenticationResponse(name, token, role));

    }
}